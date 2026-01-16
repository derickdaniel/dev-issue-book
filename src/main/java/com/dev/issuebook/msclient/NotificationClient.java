package com.dev.issuebook.msclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("notification-service")
public interface NotificationClient {

	@PostMapping("/notification/send-email")
	String sendEmail(@RequestBody String body);

}
