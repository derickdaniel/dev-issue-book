package com.dev.issuebook.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.dev.issuebook.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.issuebook.db.repository.IssueRepository;
import com.dev.issuebook.entity.IssueEntity;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // ✅ Create or update an issue
    public IssueEntity saveIssue(IssueEntity issue) {
        if (issue.getCreatedAt() == null) {
            issue.setCreatedAt(LocalDateTime.now());
        }
        return issueRepository.save(issue);
    }

    // ✅ Get issue by ID
    public IssueEntity getIssueById(Long id) {
        //throw new ResourceNotFoundException("Forced test exception" +id);
        return issueRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Issue not found for this id: " + id));
    }

    // ✅ Get all issues
    public List<IssueEntity> getAllIssues() {
        return issueRepository.findAll();
    }

    // ✅ Get issues by user
    public List<IssueEntity> getIssuesByUser(Integer userId) {
        return issueRepository.findByUserId(userId);
    }

    // ✅ Get issues by type
    public List<IssueEntity> getIssuesByType(String issueType) {
        return issueRepository.findByIssueType(issueType);
    }

    // ✅ Get unresolved issues
    public List<IssueEntity> getUnresolvedIssues() {
        return issueRepository.findByResolved(false);
    }

    // ✅ Mark issue as resolved
    public IssueEntity resolveIssue(Long id, String resolution) {
        IssueEntity issue = getIssueById(id);
        if (issue.getResolved() == true) {
            throw new IllegalStateException("Issue is already resolved for this id: " + id);
        }
        issue.setResolved(true);
        issue.setResolution(resolution);
        return issueRepository.save(issue);
    }

    // ✅ Delete issue
    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Issue not found for this id: " + id);
        }
        issueRepository.deleteById(id);
    }
}