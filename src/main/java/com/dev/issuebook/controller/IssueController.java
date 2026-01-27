package com.dev.issuebook.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.issuebook.dto.IssueDTO;
import com.dev.issuebook.dto.TagAssignmentDTO;
import com.dev.issuebook.entity.IssueEntity;
import com.dev.issuebook.mapper.IssueMapper;
import com.dev.issuebook.msclient.NotificationClient;
import com.dev.issuebook.msclient.TagsClient;
import com.dev.issuebook.service.IssueService;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
	
	Logger log = LoggerFactory.getLogger(IssueController.class);
	
	@Autowired
	private TagsClient tagsClient;
	
	@Autowired
	private NotificationClient notificationClient;
	
	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    // ✅ Create or update an issue
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<IssueEntity> createOrUpdateIssue(@RequestBody IssueDTO issue) {
        
    	IssueEntity savedIssue = issueService.saveIssue(IssueMapper.toEntity(issue));
        ResponseEntity<IssueEntity> reposnse  = ResponseEntity.ok(savedIssue);
        
        TagAssignmentDTO tagAssignDTO = new TagAssignmentDTO();
        tagAssignDTO.setEntityType("ISSUE");
        tagAssignDTO.setEntityId(savedIssue.getId());
        tagAssignDTO.setTagNameList(issue.getTags());
        tagAssignDTO.setCreatedBy(Integer.valueOf(httpServletRequest.getHeader("userid")));
        tagAssignDTO.setCreatedAt(LocalDateTime.now());

        notificationClient.sendEmail(Map.of("user", httpServletRequest.getHeader("username"), "action", "created"));
        List<TagAssignmentDTO> list = tagsClient.assignTag(tagAssignDTO);
        log.info("Assigned tag list: " + list);

        return reposnse;
    }

    // ✅ Get issue by ID
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<IssueEntity> getIssueById(@PathVariable Long id) {
        Optional<IssueEntity> issueOpt = issueService.getIssueById(id);
        return issueOpt.map(ResponseEntity::ok)
                       .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Get all issues
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<IssueEntity>> getAllIssues() {
        return ResponseEntity.ok(issueService.getAllIssues());
    }

    // ✅ Get issues by user
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IssueEntity>> getIssuesByUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(issueService.getIssuesByUser(userId));
    }

    // ✅ Get issues by type
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/type/{issueType}")
    public ResponseEntity<List<IssueEntity>> getIssuesByType(@PathVariable String issueType) {
        return ResponseEntity.ok(issueService.getIssuesByType(issueType));
    }

    // ✅ Get unresolved issues
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/unresolved")
    public ResponseEntity<List<IssueEntity>> getUnresolvedIssues() {
        return ResponseEntity.ok(issueService.getUnresolvedIssues());
    }

    // ✅ Resolve an issue
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/{id}/resolve")
    public ResponseEntity<IssueEntity> resolveIssue(@PathVariable Long id,
                                                    @RequestParam String resolution) {
        Optional<IssueEntity> resolvedIssue = issueService.resolveIssue(id, resolution);
        return resolvedIssue.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete an issue
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        boolean deleted = issueService.deleteIssue(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }
}
