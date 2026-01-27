package com.dev.issuebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "issues")
public class IssueEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "issue_desc", nullable = false, columnDefinition = "TEXT")
	private String issueDesc;

	@Column(name = "issue_type", nullable = false, length = 50)
	private String issueType;

	@Column(name = "root_cause", columnDefinition = "TEXT")
	private String rootCause;

	@Column(name = "resolution", columnDefinition = "TEXT")
	private String resolution;

	@Column(name = "resolved", nullable = false)
	private Boolean resolved = false;

	@Column(name = "refs", columnDefinition = "TEXT")
	private String refs; // reference URLs (comma-separated or JSON string)

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	public IssueEntity() {
	}

	public IssueEntity(Long id, Long userId, String issueDesc, String issueType, String rootCause,
			String resolution, Boolean resolved, String refs, LocalDateTime createdAt) {
		this.id = id;
		this.userId = userId;
		this.issueDesc = issueDesc;
		this.issueType = issueType;
		this.rootCause = rootCause;
		this.resolution = resolution;
		this.resolved = resolved;
		this.refs = refs;
		this.createdAt = createdAt;
	}

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

	public String getRootCause() {
		return rootCause;
	}

	public void setRootCause(String rootCause) {
		this.rootCause = rootCause;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public Boolean getResolved() {
		return resolved;
	}

	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}

	public String getRefs() {
		return refs;
	}

	public void setRefs(String refs) {
		this.refs = refs;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}