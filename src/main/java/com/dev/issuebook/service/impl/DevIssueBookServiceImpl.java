package com.dev.issuebook.service.impl;

import static java.util.Objects.nonNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dev.issuebook.constant.IssueBookJsonKeys;

@Service
public class DevIssueBookServiceImpl implements com.dev.issuebook.service.DevIssueBookService {

	Logger log = LoggerFactory.getLogger(DevIssueBookServiceImpl.class);

	final static String FILE_PATH = "H:\\Project\\cloud\\dev-ssue-book.json";

	@Override
	public List<Object> listIssues() {

		final File issueFile = new File(FILE_PATH);

		JSONArray fileData = getFileDataIfPresent(issueFile);

		return fileData.toList();
	}

	@Override
	public void saveIssue(JSONObject issueJson) {

		final File issueFile = new File(FILE_PATH);

		JSONArray fileData = getFileDataIfPresent(issueFile);

		validateAndRectifyData(issueJson);

		log.info(issueJson.toString());

		writeJsonObjectToFile(issueJson, fileData, issueFile);

	}

	private void validateAndRectifyData(JSONObject issueJson) {
		Arrays.stream(IssueBookJsonKeys.values()).filter(k -> !issueJson.has(k.name()))
				.map(k -> issueJson.put(k.name(), ""));
	}

	private JSONArray getFileDataIfPresent(final File issueFile) {

		if (issueFile.exists()) {

			try (FileReader fileR = new FileReader(issueFile)) {

				return (JSONArray) new JSONTokener(fileR).nextValue();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new JSONArray();
	}

	private void writeJsonObjectToFile(final JSONObject issueJson, final JSONArray fileData, final File issueFile) {

		try (FileWriter file = new FileWriter(issueFile, false)) {

			issueJson.put("id", UUID.randomUUID());

			if (nonNull(fileData)) {

				fileData.put(issueJson);
				file.write(fileData.toString());
			} else {

				JSONArray jsonArray = new JSONArray();
				jsonArray.put(issueJson);
				file.write(jsonArray.toString());
			}
			log.info("New issue added in a file: " + issueFile);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void cleanUpIssueBook() {

		final File issueFile = new File(FILE_PATH);

		JSONArray fileData = getFileDataIfPresent(issueFile);

		List<UUID> idsToDelete = new ArrayList<UUID>();

		if (!fileData.isEmpty()) {

			fileData.forEach(data -> {
				JSONObject json = (JSONObject) data;

				if (json.isNull("issueDesc")
						|| json.getString("issueDesc").isEmpty()) {
					idsToDelete.add(UUID.fromString(json.get("id").toString()));
				}
			});
		}

		System.out.println(idsToDelete);
	}

	@Override
	public void updateIssue(JSONObject issueJson, Long id) {
	}

}
