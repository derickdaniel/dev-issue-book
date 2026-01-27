package com.dev.issuebook.mapper;

import java.util.Collections;

import com.dev.issuebook.dto.IssueDTO;
import com.dev.issuebook.entity.IssueEntity;

public class IssueMapper {

	// Entity → DTO
	public static IssueDTO toDTO(IssueEntity entity) {
		if (entity == null)
			return null;

		IssueDTO dto = new IssueDTO();
		dto.setId(entity.getId());
		dto.setUserId(entity.getUserId());
		dto.setIssueDesc(entity.getIssueDesc());
		dto.setIssueType(entity.getIssueType());
		dto.setResolved(entity.getResolved());
		dto.setResolution(entity.getResolution());
		dto.setCreatedAt(entity.getCreatedAt());

		// Tags are not stored in IssueEntity directly; they come from Tag-Service
		dto.setTags(Collections.emptyList());

		return dto;
	}

	// DTO → Entity
	public static IssueEntity toEntity(IssueDTO dto) {
		if (dto == null)
			return null;

		IssueEntity entity = new IssueEntity();
		entity.setId(dto.getId());
		entity.setUserId(dto.getUserId());
		entity.setIssueDesc(dto.getIssueDesc());
		entity.setIssueType(dto.getIssueType());
		entity.setResolved(dto.getResolved());
		entity.setResolution(dto.getResolution());
		entity.setCreatedAt(dto.getCreatedAt());

		// Tags are ignored here since they belong to Tag-Service
		return entity;
	}
}