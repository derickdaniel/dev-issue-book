package com.dev.issuebook.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONArray;

import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Table(name = "Issues")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) }) //add unique key userid
public class IssueEntity extends IdBasedEntity implements Serializable {

	private static final long serialVersionUID = 1003150105693772666L;

	@Column(unique=true, nullable = false)
	protected Integer userId;

	@Column(columnDefinition = "json")
	@Convert(converter = JSONArrayConverter.class)
	private JSONArray details;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public JSONArray getDetails() {
		return details;
	}

	public void setDetails(JSONArray details) {
		this.details = details;
	}
}
