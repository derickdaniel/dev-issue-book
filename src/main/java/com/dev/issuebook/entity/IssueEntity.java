package com.dev.issuebook.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonType;

@Entity
@Table(name = "Issues")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonType.class) })
public class IssueEntity extends IdBasedEntity implements Serializable {

	private static final long serialVersionUID = 1003150105693772666L;

	@Column(nullable = false)
	protected Integer userId;

	@Type(type = "json")
	@Column(columnDefinition = "json")
	private Map<String, Object> details = new HashMap<>();

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Map<String, Object> getDetails() {
		return details;
	}

	public void setDetails(Map<String, Object> details) {
		this.details = details;
	}

}
