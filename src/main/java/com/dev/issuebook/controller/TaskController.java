package com.dev.issuebook.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.issuebook.service.TaskService;

@RestController
@RequestMapping("/dib")
public class TaskController {

	Logger log = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	TaskService taskService;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/task")
	public ResponseEntity createTask(@RequestBody String tasks) {

		log.info("Request to create dev task: " + tasks + " at: " + new Timestamp(System.currentTimeMillis()));
		JSONObject taskJson = new JSONObject(tasks);
		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");
		
		if (userid != null && !userid.isBlank()) {
			taskService.saveTasksByUser(taskJson, Integer.parseInt(userid));
			log.info("Create task db call completed for user: " + userid);
		}
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/tasks")
	public Map<String, Object> getTasks(@RequestHeader("Authorization") String authorization) {

		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");

		return taskService.listTasksByUser(Integer.parseInt(userid));
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/tasks/today")
	public List<Object> getTodaysTasks(@RequestHeader("Authorization") String authorization) {

		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");

		return taskService.listTasksByUserAndDate(Integer.parseInt(userid), LocalDate.now());
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/tasks/{taskId}")
	public ResponseEntity deleteTaskByTaskId(@RequestHeader("Authorization") String authorization, @PathVariable String taskId) {

		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");

		log.info("Deleting task of user: " + userid + " and task: " + taskId);
		taskService.deleteTaskByTaskId(taskId, Integer.parseInt(userid));

		return ResponseEntity.ok().build();
	}

}
