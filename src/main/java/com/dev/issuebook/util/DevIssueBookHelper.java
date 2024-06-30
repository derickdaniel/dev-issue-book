package com.dev.issuebook.util;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.dev.issuebook.constant.Keys;

public class DevIssueBookHelper {

	/* Add key if not present */
	public static void validateAndRectifyData(JSONObject issueJson) {
		Arrays.stream(Keys.values()).filter(k -> !issueJson.has(k.name())).map(k -> issueJson.put(k.name(), ""));
	}

	public static JSONArray getFileDataIfPresent(final File issueFile) {
		if (issueFile.exists()) {
			try (FileReader fileR = new FileReader(issueFile)) {
				return (JSONArray) new JSONTokener(fileR).nextValue();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new JSONArray();
	}

	public static void writeJsonObjectToFile(final JSONArray fileData, final File issueFile) {
		try (FileWriter file = new FileWriter(issueFile, false)) {
			file.write(fileData.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeOrUpdateToFile(final JSONObject issueJson, final JSONArray fileData, final File issueFile,
			boolean isNew) {
		try (FileWriter file = new FileWriter(issueFile, false)) {
			setFieldsForNewRecord(issueJson, isNew);
			if (nonNull(fileData)) {
				fileData.put(issueJson);
				file.write(fileData.toString());
			} else {
				JSONArray jsonArray = new JSONArray();
				jsonArray.put(issueJson);
				file.write(jsonArray.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void cleanUpIssueBook() {
		final File issueFile = new File("");
		JSONArray fileData = getFileDataIfPresent(issueFile);
		List<UUID> idsToDelete = new ArrayList<UUID>();

		if (!fileData.isEmpty()) {
			fileData.forEach(data -> {
				JSONObject json = (JSONObject) data;
				if (json.isNull("issueDesc") || json.getString("issueDesc").isEmpty()) {
					idsToDelete.add(UUID.fromString(json.get("id").toString()));
				}
			});
		}
		System.out.println(idsToDelete);
	}
	
	private static void setFieldsForNewRecord(final JSONObject issueJson, boolean isNew) {
		if (isNew) {
			issueJson.put(Keys.ID.getVal(), generateId());
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM YYYY HH:mm");
			issueJson.put(Keys.CREATED_AT.getVal(), sdf.format(new Date()));
		}
	}
	
	public static String generateId() {
		SecureRandom rnd = new SecureRandom();
		String s = String.format("%06x", rnd.nextInt(0x1000000));
		return s.toUpperCase();
	}
	
	//todo: review
	private static JSONArray validateKeys(JSONArray data) {
		JSONArray rectifiedData = new JSONArray();
		Iterator<Object> itr = data.iterator();
		while (itr.hasNext()) {
			JSONObject json = (JSONObject) itr.next();

			Arrays.stream(Keys.values()).forEach(key -> {

				if (!json.has(key.name())) {
					json.put(key.name(), "");
				}
			});
			rectifiedData.put(json);
		}
		return rectifiedData;
	}
}
