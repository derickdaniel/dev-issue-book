package com.dev.issuebook.service.impl;

import static com.dev.issuebook.constant.IssueBookConstants.FILE_PATH;
import static com.dev.issuebook.util.DevIssueBookHelper.getFileDataIfPresent;
import static com.dev.issuebook.util.DevIssueBookHelper.validateAndRectifyData;
import static com.dev.issuebook.util.DevIssueBookHelper.writeJsonObjectToFile;
import static com.dev.issuebook.util.DevIssueBookHelper.writeOrUpdateToFile;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.issuebook.constant.Keys;
import com.dev.issuebook.db.repository.IssueRepository;
import com.dev.issuebook.service.DevIssueBookFileService;

@Service
public class DevIssueBookFileServiceImpl implements DevIssueBookFileService {

	Logger log = LoggerFactory.getLogger(DevIssueBookFileServiceImpl.class);

	@Autowired
	IssueRepository issueRepo;

	@Override
	public List<Object> listIssues() {

		JSONArray fileData = getFileDataIfPresent(new File(FILE_PATH));
		log.info("Retreived issue list from a file: " + FILE_PATH);
		return fileData.toList();
	}

	@Override
	public void saveIssue(JSONObject issueJson) {

		final File issueFile = new File(FILE_PATH);
		JSONArray fileData = getFileDataIfPresent(issueFile);
		validateAndRectifyData(issueJson);
		writeOrUpdateToFile(issueJson, fileData, issueFile, true);
		log.info("Issue saved in a file: " + FILE_PATH);
	}

	@Override
	public void updateIssue(JSONObject issueJson, String id) {

		final File issueFile = new File(FILE_PATH);
		JSONArray issueArray = removeJsonObjectById(id, issueFile);
		writeOrUpdateToFile(issueJson, issueArray, issueFile, false);
		log.info("Issue updated in a file: " + FILE_PATH);
	}

	@Override
	public JSONObject getIssueById(String id) {

		JSONArray issueArray = getFileDataIfPresent(new File(FILE_PATH));
		JSONObject issueDetails = new JSONObject();
		Iterator<Object> itr = issueArray.iterator();
		while (itr.hasNext()) {
			JSONObject json = (JSONObject) itr.next();

			if (id.equals(json.getString(Keys.ID.getVal()))) {
				issueDetails = json;
				break;
			}
		}
		log.info("Retreived an issue with id: " + id + " from a file: " + FILE_PATH);
		return issueDetails;
	}

	@Override
	public void deleteIssueById(String id) {

		final File issueFile = new File(FILE_PATH);
		JSONArray issueArray = removeJsonObjectById(id, issueFile);
		writeJsonObjectToFile(issueArray, issueFile);
		log.info("Deleted issue with id: " + id + " from a file: " + FILE_PATH);
	}

	private JSONArray removeJsonObjectById(String id, final File issueFile) {

		JSONArray issueArray = getFileDataIfPresent(issueFile);
		int counter = 0;
		Iterator<Object> itr = issueArray.iterator();

		while (itr.hasNext()) {
			JSONObject json = (JSONObject) itr.next();
			if (id.equals(json.getString(Keys.ID.getVal()))) {
				issueArray.remove(counter);
				break;
			}
			counter++;
		}
		return issueArray;
	}
}
