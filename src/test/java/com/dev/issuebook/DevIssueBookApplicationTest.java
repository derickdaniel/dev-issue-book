package com.dev.issuebook;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.issuebook.controller.DevIssueBookController;

@SpringBootTest
class DevIssueBookApplicationTest {

	@Autowired
	private DevIssueBookController devIssueBookController;

	@Test
	void contextLoads() {
		// to ensure that controller is getting created inside the application context
		assertNotNull(devIssueBookController);
	}

}
