package com.dev.issuebook.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public interface TaskService {
	
	void saveTasksByUser(JSONObject issueJson, int userId);
	
	Map<String, Object> listTasksByUser(int userId);
	
	List<Object> listTasksByUserAndDate(int userId, LocalDate date);

}
