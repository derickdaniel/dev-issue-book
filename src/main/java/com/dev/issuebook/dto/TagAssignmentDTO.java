package com.dev.issuebook.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TagAssignmentDTO {

	private Long id;
	private Long tagId; // reference to Tag entity
	private String entityType; // e.g., "issue", "project"
	private Long entityId; // UUID of the entity
	private Integer createdBy; // user who created the assignment
	private LocalDateTime createdAt;
	
	private List<String> tagNameList; //new extra

	public TagAssignmentDTO() {
	}

	public TagAssignmentDTO(Long id, Long tagId, String entityType, Long entityId, Integer createdBy,
			LocalDateTime createdAt) {
		this.id = id;
		this.tagId = tagId;
		this.entityType = entityType;
		this.entityId = entityId;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}


	public List<String> getTagNameList() {
		return tagNameList;
	}

	public void setTagNameList(List<String> tagNameList) {
		this.tagNameList = tagNameList;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}