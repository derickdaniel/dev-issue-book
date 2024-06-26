package com.dev.issuebook.service;

import java.util.List;

import org.json.JSONObject;

public interface DevIssueBookService {

	List<Object> listIssuesByUser(int userId);
	
	JSONObject getIssueById(String id, int userId);

	void saveIssueByUser(JSONObject issueJson, int userId);

	void updateIssue(JSONObject issueJson, String id, int userId);
	
	void deleteIssueById(String id, int userId);
}
