package com.dev.issuebook.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dev.issuebook.dto.IssueDTO;
import com.dev.issuebook.dto.TagDTO;
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
		dto.setRootCause(entity.getRootCause());
		dto.setResolution(entity.getResolution());
		dto.setRefs(entity.getRefs());
		dto.setCreatedAt(entity.getCreatedAt());

		// Tags are not stored in IssueEntity directly; they come from Tag-Service
		dto.setTags(Collections.emptyList());

		return dto;
	}
	
	public static IssueDTO toDTOWithTags(IssueEntity entity, Map<Long, List<TagDTO>> issueTagsMap) {
		IssueDTO dto = IssueMapper.toDTO(entity);
		
		List<TagDTO> tagDtoList = issueTagsMap.get(dto.getId());
		dto.setTags(tagDtoList);
		buildAndSetTagStr(dto, tagDtoList);
		
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
		entity.setRootCause(dto.getRootCause());
		entity.setResolution(dto.getResolution());
		entity.setRefs(dto.getRefs());
		entity.setCreatedAt(dto.getCreatedAt());

		// Tags are ignored here since they belong to Tag-Service
		return entity;
	}

	// Entity List → DTO List
	public static List<IssueDTO> toDTOList(List<IssueEntity> entities) {
		if (entities == null || entities.isEmpty())
			return Collections.emptyList();

		return entities.stream().map(IssueMapper::toDTO).collect(Collectors.toList());
	}

	// DTO List → Entity List
	public static List<IssueEntity> toEntityList(List<IssueDTO> dtos) {
		if (dtos == null || dtos.isEmpty())
			return Collections.emptyList();

		return dtos.stream().map(IssueMapper::toEntity).collect(Collectors.toList());
	}

	public static List<IssueDTO> toDTOListIncludeTags(List<IssueEntity> entities, Map<Long, List<TagDTO>> issueTagsMap) {
		if (entities == null || entities.isEmpty())
			return Collections.emptyList();

		return entities.stream().map(IssueMapper::toDTO).map(dto -> IssueMapper.setTags(dto, issueTagsMap))
				.collect(Collectors.toList());
	}

	private static IssueDTO setTags(IssueDTO dto, Map<Long, List<TagDTO>> issueTagsMap) {
		List<TagDTO> tagDtoList = issueTagsMap.get(dto.getId());
		dto.setTags(tagDtoList);
		buildAndSetTagStr(dto, tagDtoList);
		return dto;
	}

	private static void buildAndSetTagStr(IssueDTO dto, List<TagDTO> tagDtoList) {
		if (tagDtoList != null) {
			dto.setTagsStr(tagDtoList.stream().map(TagDTO::getName).collect(Collectors.joining(", ")));
		} else {
			dto.setTagsStr("");
			dto.setTags(List.of());
		}
	}

}