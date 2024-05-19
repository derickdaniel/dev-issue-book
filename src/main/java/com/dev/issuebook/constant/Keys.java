package com.dev.issuebook.constant;

public enum Keys {
	
    ISSUE_DESC("issueDesc"),
	ISSUE_TYPE("issueType"),
	CAUSE("cause"),
	ID("id"),
	RESOLUTION("resolution"),
	RESOLVED("resolved"),
	TAGS("tags"),
	REFERENCES("references");
	
	private String key;
	
	Keys(String key) {
		this.key = key;
	}
	
	public String getVal() {
		return key;
	}

}
