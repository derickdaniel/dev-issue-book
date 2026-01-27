package com.dev.issuebook.dto;

import java.time.LocalDateTime;

public class TagDTO {

	private String uuid;
	private String name;
	private String slug;
	private String description;
	private String color;
	private LocalDateTime createdAt;

	public TagDTO() {
	}

	public TagDTO(String uuid, String name, String slug, String description, String color, LocalDateTime createdAt) {
		this.uuid = uuid;
		this.name = name;
		this.slug = slug;
		this.description = description;
		this.color = color;
		this.createdAt = createdAt;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
