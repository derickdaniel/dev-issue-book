package com.dev.issuebook.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.issuebook.service.WeatherService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dib")
public class WeatherController {

	Logger log = LoggerFactory.getLogger(WeatherController.class);

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	WeatherService weatherService;

	@PreAuthorize("hasAuthority('ROLE_USER')")
	@GetMapping("/weather/{location}")
	public Mono<String> getWeather(@RequestHeader("Authorization") String authorization, @PathVariable String location) {

		String userid = httpServletRequest.getHeader("userid");
		log.info("Found user id: " + userid + " from request context");

		return weatherService.getWeather(location);
	}
}
