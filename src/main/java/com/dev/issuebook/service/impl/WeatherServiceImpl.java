package com.dev.issuebook.service.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.dev.issuebook.service.WeatherService;

import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl implements WeatherService {

	WebClient webClient = WebClient.create("https://api.weatherstack.com");

	private final String API_KEY = "3646039d001a12774b747f7fe66f114d";

	@Override
	public Mono<String> getWeather(String location) {
		return webClient.get().uri("current?access_key=" + API_KEY + "&query=" + location).retrieve()
				.bodyToMono(String.class);
	}

}
