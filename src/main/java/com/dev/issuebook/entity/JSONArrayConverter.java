package com.dev.issuebook.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.json.JSONArray;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

@Converter(autoApply = true)
public class JSONArrayConverter implements AttributeConverter<JSONArray, String> {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(JSONArrayConverter.class);

	@Override
	public String convertToDatabaseColumn(JSONArray array) {
		String data = null;
		try {
			data = array.toString();
		} catch (final Exception e) {
			logger.error("JSON writing error", e);
		}

		return data;
	}

	@Override
	public JSONArray convertToEntityAttribute(String data) {
		JSONArray array = null;

		try {
			array = new JSONArray(data);
		} catch (final Exception e) {
			logger.error("JSON reading error", e);
		}

		return array;
	}
}