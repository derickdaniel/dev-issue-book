package com.dev.issuebook.service;

import java.util.List;

import org.json.JSONObject;

public interface DevIssueBookService {

	List<Object> listIssues();
	
	JSONObject getIssueById(String id);

	void saveIssue(JSONObject issueJson);

	void updateIssue(JSONObject issueJson, String id);
	
	void deleteIssueById(String id);
}
