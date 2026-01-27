package com.dev.issuebook.msclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dev.issuebook.dto.TagAssignmentDTO;

@FeignClient("TAG-SERVICE")
public interface TagsClient {

	@PostMapping("/api/tag-assignments")
	List<TagAssignmentDTO> assignTag(@RequestBody TagAssignmentDTO assignmentDTO);

}
