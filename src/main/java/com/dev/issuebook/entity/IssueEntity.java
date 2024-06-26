package com.dev.issuebook.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.json.JSONArray;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Table(name = "Issues")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public class IssueEntity extends IdBasedEntity implements Serializable {

	private static final long serialVersionUID = 1003150105693772666L;

	@Column(nullable = false)
	protected Integer userId;

	//@Type(type = "json")
	//@Lob
	@Column(columnDefinition = "json")
	//@JsonIgnoreProperties(ignoreUnknown = true)
	@Convert(converter = JSONArrayConverter.class)
	//private Map<String, Object> details = new HashMap<>();
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
