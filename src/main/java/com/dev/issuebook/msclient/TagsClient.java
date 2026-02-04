package com.dev.issuebook.msclient;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dev.issuebook.dto.TagAssignmentDTO;
import com.dev.issuebook.dto.TagDTO;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient("TAG-SERVICE")
public interface TagsClient {

	Logger log = LoggerFactory.getLogger(TagsClient.class);

	@CircuitBreaker(name = "assignTagCB", fallbackMethod = "assignTagFallback")
	@PostMapping("/api/tag-assignments")
	List<TagAssignmentDTO> assignTag(@RequestBody TagAssignmentDTO assignmentDTO);

	@CircuitBreaker(name = "getTagsByCreatedByCB", fallbackMethod = "getTagsByCreatedByFallback")
	@GetMapping("api/tags/user/{createdBy}")
	Map<Long, List<TagDTO>> getTagsByCreatedBy(@PathVariable Long createdBy);

	@CircuitBreaker(name = "removeAllAssignmentsForEntityCB", fallbackMethod = "removeAllAssignmentsForEntityFallback")
	@DeleteMapping("api/tag-assignments/{entityType}/{entityId}")
	void removeAllAssignmentsForEntity(@PathVariable String entityType, @PathVariable Long entityId);

	default List<TagAssignmentDTO> assignTagFallback(Exception ex) {
		log.error("Tag Service - Assign tags failed.");
		log.error(ex.getMessage());
		return List.of();
	}

	default Map<Long, List<TagDTO>> getTagsByCreatedByFallback(Exception ex) {
		log.error("Tag Service - Get tags by user failed.");
		log.error(ex.getMessage());
		return Map.of();
	}

	default void removeAllAssignmentsForEntityFallback(Exception ex) {
		log.error("Tag Service - Remove all assignment for entity failed.");
		log.error(ex.getMessage());
	}

}
