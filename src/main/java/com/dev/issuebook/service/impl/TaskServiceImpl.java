package com.dev.issuebook.service.impl;

import java.lang.StackWalker.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

		JSONArray tasks = taskJson.getJSONArray("tasks");
		addTaskId(tasks);

		Optional<TaskEntity> entity = taskRepo.findByUserIdAndCreatedDate(userId, LocalDate.now());
		if (entity.isPresent()) {
			entity.get().setTasks(tasks);
			taskRepo.save(entity.get());
		} else {
			TaskEntity newEntity = new TaskEntity();
			newEntity.setTasks(tasks);
			newEntity.setUserId(userId);
			newEntity.setCreatedDate(LocalDate.now());
			taskRepo.save(newEntity);
		}
	}

	private void addTaskId(JSONArray arr) {
		arr.forEach(a -> {
			JSONObject j = (JSONObject) a;
			if (!j.has("taskId") || j.getString("taskId") != null) {
				j.put("taskId", generateId());
			}
		});
	}

	@Override
	public Map<String, Object> listTasksByUser(int userId) {

		List<TaskEntity> taskList = taskRepo.findAllByUserIdOrderByCreatedDateDesc(userId);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		taskList.forEach(task -> {
			final String dateStr = task.getCreatedDate().format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
			if (task.getCreatedDate().isEqual(LocalDate.now())) {
				map.put("Today's Task (" + dateStr + ")", task.getTasks().toList());
			} else {
				map.put(dateStr, task.getTasks().toList());			
			}
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

	@Override
	public void deleteTaskByTaskId(String taskId, int userId) {
		TaskEntity entity = findTaskByTaskId(taskId, userId);

		if (entity.getId() != null) {

			int len = entity.getTasks().length();

			if (len == 1) {
				if (entity.getTasks().getJSONObject(0).getString("taskId").equals(taskId)) {
					taskRepo.delete(entity);
				}
			} else {
				Iterator<Object> itr = entity.getTasks().iterator();
				while (itr.hasNext()) {
					JSONObject json = (JSONObject) itr.next();
					if (json.getString("taskId").equals(taskId)) {
						itr.remove();
						taskRepo.save(entity);
						break;
					}
				}
			}

		} else {
			System.out.println("No entity found for deletion.");
		}
	}

	private TaskEntity findTaskByTaskId(String taskId, int userId) {
		List<TaskEntity> taskList = taskRepo.findAllByUserIdOrderByCreatedDateDesc(userId);

		TaskEntity entity = new TaskEntity();

		taskList.forEach(task -> {

			Iterator<Object> itr = task.getTasks().iterator();
			while (itr.hasNext()) {
				JSONObject json = (JSONObject) itr.next();
				if (json.getString("taskId").equals(taskId)) {
					entity.setCreatedDate(task.getCreatedDate());
					entity.setId(task.getId());
					entity.setTasks(task.getTasks());
					entity.setUserId(task.getUserId());
					entity.setIssueId(task.getIssueId());
					break;
				}
			}
		});
		return entity;
	}
}
