package com.dev.issuebook.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.issuebook.msclient.NotificationClient;
import com.dev.issuebook.service.DevIssueBookFileService;
import com.dev.issuebook.service.DevIssueBookService;

@RestController
@RequestMapping("/dib")
public class DevIssueBookController {

	Logger log = LoggerFactory.getLogger(DevIssueBookController.class);

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	DevIssueBookService dbService;

	@Autowired
	DevIssueBookFileService fileService;
	
	@Autowired
	NotificationClient notificationClient;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PostMapping("/issue")
	public JSONObject createIssue(@RequestBody String issueData) {

		log.info("Request to create dev issue: " + issueData + " at: " + new Timestamp(System.currentTimeMillis()));
		JSONObject issueJson = new JSONObject(issueData);
		fileService.saveIssue(issueJson);
		log.info("Created dev issue at: " + new Timestamp(System.currentTimeMillis()));

		// db call start
		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");
		if (userid != null && !userid.isBlank()) {
			dbService.saveIssueByUser(issueJson, Integer.parseInt(userid));
			log.info("Create issue db call completed for user: " + userid);
		}
		
		// call notification service, notify user
		String username = httpServletRequest.getHeader("username");
		notificationClient.sendEmail(issueData + " has been saved by User " +  " " + username);
		
		return issueJson;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@PatchMapping("/issue/{id}")
	public JSONObject updateIssue(@RequestBody String issueData, @PathVariable String id) {

		log.info("Request to update dev issue: " + issueData + " at: " + new Timestamp(System.currentTimeMillis()));
		JSONObject issueJson = new JSONObject(issueData);
		String userid = httpServletRequest.getHeader("userid");
		dbService.updateIssue(issueJson, id, Integer.parseInt(userid));
		log.info("Updated dev issue at: " + new Timestamp(System.currentTimeMillis()) + " for user: " + userid);
		
		// call notification service, notify user
		String username = httpServletRequest.getHeader("username");
		notificationClient.sendEmail(issueData + " has been updated by User " +  " " + username);
		
		return issueJson;
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/issue")
	public List<Object> getIssues(@RequestHeader("Authorization") String authorization) {

		// List<Object> issuesFromFile = fileService.listIssues();
		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");

		return dbService.listIssuesByUser(Integer.parseInt(userid));
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping(value = "/issue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getIssueById(@PathVariable String id) {

		log.info("Request to get issue details by id: " + id);
		String userid = httpServletRequest.getHeader("userid");
		JSONObject issue = dbService.getIssueById(id, Integer.parseInt(userid));
		// fileService.getIssueById(id);
		return new ResponseEntity<String>(issue.toString(), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@DeleteMapping("/issue/{id}")
	public void deleteIssue(@PathVariable String id) {

		String userid = httpServletRequest.getHeader("userid");
		dbService.deleteIssueById(id, Integer.parseInt(userid));
		log.info("Data removed for id: " + id + " at: " + new Timestamp(System.currentTimeMillis()) + " for user: "
				+ userid);
		
		// call notification service, notify user
		String username = httpServletRequest.getHeader("username");
		notificationClient.sendEmail(id +  " id has been deleted by User " + " " + username);
	}
}
