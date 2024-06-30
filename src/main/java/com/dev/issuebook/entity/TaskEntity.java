package com.dev.issuebook.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONArray;

import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Table(name = "Tasks")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public class TaskEntity extends IdBasedEntity implements Serializable {

	private static final long serialVersionUID = -2016825234039508483L;

	@Column(nullable = false)
	protected Integer userId;
	
	@Column(columnDefinition = "json")
	@Convert(converter = JSONArrayConverter.class)
	private JSONArray tasks;
	
	@Column(nullable = false)
	private LocalDate createdDate;
	
	protected Integer issueId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public JSONArray getTasks() {
		return tasks;
	}

	public void setTasks(JSONArray tasks) {
		this.tasks = tasks;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getIssueId() {
		return issueId;
	}

	public void setIssueId(Integer issueId) {
		this.issueId = issueId;
	}
}
