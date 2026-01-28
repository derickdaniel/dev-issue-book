package com.dev.issuebook.msclient;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dev.issuebook.dto.TagAssignmentDTO;
import com.dev.issuebook.dto.TagDTO;

@FeignClient("TAG-SERVICE")
public interface TagsClient {

	@PostMapping("/api/tag-assignments")
	List<TagAssignmentDTO> assignTag(@RequestBody TagAssignmentDTO assignmentDTO);

	@GetMapping("api/tags/user/{createdBy}")
	Map<Long, List<TagDTO>> getTagsByCreatedBy(@PathVariable Long createdBy);

	@DeleteMapping("api/tag-assignments/{entityType}/{entityId}")
	void removeAllAssignmentsForEntity(@PathVariable String entityType, @PathVariable Long entityId);

}
