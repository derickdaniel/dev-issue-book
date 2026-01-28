package com.dev.issuebook.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dev.issuebook.dto.NotificationRequest;
import com.dev.issuebook.dto.UserInfoResponse;
import com.dev.issuebook.model.UserAction;
import com.dev.issuebook.msclient.AuthClient;
import com.dev.issuebook.msclient.NotificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.dev.issuebook.dto.TagDTO;
import com.dev.issuebook.entity.IssueEntity;
import com.dev.issuebook.mapper.IssueMapper;
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
    AuthClient authClient;
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
        ResponseEntity<IssueEntity> reposnse = ResponseEntity.ok(savedIssue);


        if (issue.getTagsStr() != null && !issue.getTagsStr().isBlank()) {
            TagAssignmentDTO tagAssignDTO = new TagAssignmentDTO();
            tagAssignDTO.setEntityType("ISSUE");
            tagAssignDTO.setEntityId(savedIssue.getId());
            tagAssignDTO.setTagNameList(Arrays.stream(issue.getTagsStr().split(",")).map(String::trim).collect(Collectors.toList()));
            tagAssignDTO.setCreatedBy(Integer.valueOf(httpServletRequest.getHeader("userid")));
            tagAssignDTO.setCreatedAt(LocalDateTime.now());
            List<TagAssignmentDTO> list = tagsClient.assignTag(tagAssignDTO);
            log.info("Assigned tag list: " + list);
        }

        //notificationClient.sendEmail(Map.of("user", httpServletRequest.getHeader("username"), "action", "created"));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserInfoResponse user = authClient.getUserByUsername(username);
        notificationClient.notifyAction(
                new NotificationRequest(
                        user.getEmail(),
                        UserAction.CREATED.toString(),
                        user.getUsername(), LocalDate.now()
                )
        );
        return reposnse;
    }


    // ✅ Get issue by ID
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssueById(@PathVariable Long id) {
        Optional<IssueEntity> issueOpt = issueService.getIssueById(id);

        // todo: to create cache and store this data
        Map<Long, List<TagDTO>> issueTagsMap = tagsClient.getTagsByCreatedBy(Long.valueOf(httpServletRequest.getHeader("userid")));

        IssueDTO responseDTO = IssueMapper.toDTOWithTags(issueOpt.get(), issueTagsMap);
        return ResponseEntity.ok(responseDTO);
    }

    // ✅ Get all issues
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<IssueDTO>> getAllIssues() {

        List<IssueEntity> issues = issueService.getAllIssues();
        Map<Long, List<TagDTO>> issueTagsMap = tagsClient.getTagsByCreatedBy(Long.valueOf(httpServletRequest.getHeader("userid")));

        return ResponseEntity.ok(IssueMapper.toDTOListIncludeTags(issues, issueTagsMap));
    }

    // ✅ Get issues by user
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<IssueDTO>> getIssuesByUser(@PathVariable Integer userId) {

        List<IssueEntity> issues = issueService.getIssuesByUser(userId);
        Map<Long, List<TagDTO>> issueTagsMap = tagsClient.getTagsByCreatedBy(userId.longValue());

        return ResponseEntity.ok(IssueMapper.toDTOListIncludeTags(issues, issueTagsMap));
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
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserInfoResponse user = authClient.getUserByUsername(username);
        notificationClient.notifyAction(
                new NotificationRequest(
                        user.getEmail(),
                        UserAction.UPDATED.toString(),
                        user.getUsername(), LocalDate.now()
                )
        );
        return resolvedIssue.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete an issue
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        boolean deleted = issueService.deleteIssue(id);
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        UserInfoResponse user = authClient.getUserByUsername(username);
        notificationClient.notifyAction(
                new NotificationRequest(
                        user.getEmail(),
                        UserAction.DELETED.toString(),
                        user.getUsername(), LocalDate.now()
                )
        );
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
