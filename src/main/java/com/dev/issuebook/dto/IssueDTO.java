package com.dev.issuebook.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IssueDTO {

	private Long id;
	private Long userId;
	private String issueDesc;
	private String issueType; // e.g., "API", "UI", "Database"
	private Boolean resolved;
	private String resolution; // optional: explanation of fix
	private LocalDateTime createdAt;
	private List<String> tags; // list of tag names

	// Constructors
	public IssueDTO() {
	}

	public IssueDTO(Long id, Long userId, String issueDesc, String issueType, Boolean resolved, String resolution,
			LocalDateTime createdAt, LocalDateTime updatedAt, List<String> tags) {
		this.id = id;
		this.userId = userId;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.resolved = resolved;
		this.resolution = resolution;
		this.createdAt = createdAt;
		this.tags = tags;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIssueDesc() {
		return issueDesc;
	}

	public void setIssueDesc(String issueDesc) {
		this.issueDesc = issueDesc;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}