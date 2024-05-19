package com.dev.issuebook.controller;

import java.sql.Timestamp;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.issuebook.service.DevIssueBookService;

@CrossOrigin
@RestController
public class DevIssueBookController {

	Logger log = LoggerFactory.getLogger(DevIssueBookController.class);

	@Autowired
	DevIssueBookService service;

	@PostMapping("/issue")
	public JSONObject createIssue(@RequestBody String issueData) {

		log.info("Request to create dev issue: " + issueData + " at: " + new Timestamp(System.currentTimeMillis()));

		JSONObject issueJson = new JSONObject(issueData);

		service.saveIssue(issueJson);

		log.info("Created dev issue at: " + new Timestamp(System.currentTimeMillis()));

		return issueJson;
	}
	
	@PatchMapping("/issue/{id}")
	public JSONObject updateIssue(@RequestBody String issueData, @PathVariable String id) {

		log.info("Request to update dev issue: " + issueData + " at: " + new Timestamp(System.currentTimeMillis()));

		JSONObject issueJson = new JSONObject(issueData);

		service.updateIssue(issueJson, id);

		log.info("Updated dev issue at: " + new Timestamp(System.currentTimeMillis()));

		return issueJson;
	}

	@GetMapping("/issue")
	public List<Object> getIssues() {

		List<Object> issues = service.listIssues();

		log.info("Returning issue list of size: " + issues.size() + " at: " + new Timestamp(System.currentTimeMillis()));

		return issues;
	}

	@GetMapping(value= "/issue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getIssueById(@PathVariable String id) {

		log.info("Request to get issue details by id: " + id);

		JSONObject issue = service.getIssueById(id);

		log.info("Returning issue list of size: " + issue + " at: " + new Timestamp(System.currentTimeMillis()));

		return new ResponseEntity<String>(issue.toString(), HttpStatus.OK);
	}
	
	@DeleteMapping("/issue/{id}")
	public void deleteIssue(@PathVariable String id) {

		service.deleteIssueById(id);

		log.info("Data removed for id: " + id + " at: " + new Timestamp(System.currentTimeMillis()));
	}
}
