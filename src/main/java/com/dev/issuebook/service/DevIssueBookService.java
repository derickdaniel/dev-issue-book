package com.dev.issuebook.service;

import java.util.List;

import org.json.JSONObject;

public interface DevIssueBookService {

	List<Object> listIssues();

	void saveIssue(JSONObject issueJson);

	void updateIssue(JSONObject issueJson, Long id);
}
