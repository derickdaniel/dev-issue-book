package com.dev.issuebook.service.impl;

import static com.dev.issuebook.constant.IssueBookConstants.FILE_PATH;
import static com.dev.issuebook.util.DevIssueBookHelper.getFileDataIfPresent;
import static com.dev.issuebook.util.DevIssueBookHelper.validateAndRectifyData;
import static com.dev.issuebook.util.DevIssueBookHelper.writeJsonObjectToFile;

import java.io.File;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dev.issuebook.service.DevIssueBookService;

@Service
public class DevIssueBookServiceImpl implements DevIssueBookService {

	Logger log = LoggerFactory.getLogger(DevIssueBookServiceImpl.class);

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

	@Override
	public void updateIssue(JSONObject issueJson, Long id) {
	}

}
