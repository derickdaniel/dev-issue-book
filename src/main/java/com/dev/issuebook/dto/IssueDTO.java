package com.dev.issuebook.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IssueDTO {

	private Long id;
	private Long userId;
	private String issueDesc;
	private String issueType; // e.g., "API", "UI", "Database"
	private Boolean resolved;
	private String rootCause;
	private String resolution; // optional: explanation of fix
	private String refs;
	private LocalDateTime createdAt;
	private String tagsStr;
	private List<TagDTO> tags; // list of tag names

	// Constructors
	public IssueDTO() {
	}

	public IssueDTO(Long id, Long userId, String issueDesc, String issueType, Boolean resolved, String rootCause, String resolution, String refs,
			LocalDateTime createdAt, LocalDateTime updatedAt, String tagsStr, List<TagDTO> tags) {
		this.id = id;
		this.userId = userId;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.resolved = resolved;
		this.rootCause = rootCause;
		this.resolution = resolution;
		this.refs = refs;
		this.createdAt = createdAt;
		this.tagsStr = tagsStr;
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

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getResolution() {
		return resolution;
	}

	public String getRefs() {
		return refs;
	}

	public void setRefs(String refs) {
		this.refs = refs;
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

	public String getTagsStr() {
		return tagsStr;
	}

	public void setTagsStr(String tagsStr) {
		this.tagsStr = tagsStr;
	}

	public List<TagDTO> getTags() {
		return tags;
	}

	public void setTags(List<TagDTO> tags) {
		this.tags = tags;
	}
}