package com.dev.issuebook.service;

import org.json.JSONObject;

import reactor.core.publisher.Mono;

public interface WeatherService {
	
	Mono<String> getWeather(String location);
	
}
