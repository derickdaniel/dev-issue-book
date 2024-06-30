package com.dev.issuebook.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.issuebook.db.repository.TaskRepository;
import com.dev.issuebook.entity.TaskEntity;
import com.dev.issuebook.service.TaskService;
import static com.dev.issuebook.util.DevIssueBookHelper.generateId;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepo;

	@Override
	public void saveTasksByUser(JSONObject taskJson, int userId) {
		
		Optional<TaskEntity> entity = taskRepo.findByUserIdAndCreatedDate(userId, LocalDate.now());
		if (entity.isPresent()) {
			entity.get().setTasks(taskJson.getJSONArray("tasks"));
			taskRepo.save(entity.get());
		} else {
			JSONArray taskArray = addTaskId(taskJson.getJSONArray("tasks"));
			TaskEntity newEntity = new TaskEntity();
			newEntity.setTasks(taskArray);
			newEntity.setUserId(userId);
			newEntity.setCreatedDate(LocalDate.now());
			taskRepo.save(newEntity);
		}
	}

	private JSONArray addTaskId(JSONArray arr) {
		arr.forEach(a -> {
			JSONObject j = (JSONObject) a;
			j.put("taskId", generateId());
		});

		return arr;
	}

	@Override
	public Map<String, Object> listTasksByUser(int userId) {

		List<TaskEntity> taskList = taskRepo.findByUserId(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		taskList.forEach(task -> {
			map.put(task.getCreatedDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), task.getTasks().toList());
			// map.put("allCompleted", false);
		});
		return map;
	}

	@Override
	public List<Object> listTasksByUserAndDate(int userId, LocalDate date) {

		Optional<TaskEntity> entity = taskRepo.findByUserIdAndCreatedDate(userId, LocalDate.now());
		if (entity.isPresent()) {
			return entity.get().getTasks().toList();
		}
		return List.of();
	}
}
