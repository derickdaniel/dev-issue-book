package com.dev.issuebook.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
	public Optional<IssueEntity> getIssueById(Long id) {
		return issueRepository.findById(id);
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
	public Optional<IssueEntity> resolveIssue(Long id, String resolution) {
		Optional<IssueEntity> issueOpt = issueRepository.findById(id);
		if (issueOpt.isPresent()) {
			IssueEntity issue = issueOpt.get();
			issue.setResolved(true);
			issue.setResolution(resolution);
			issueRepository.save(issue);
			return Optional.of(issue);
		}
		return Optional.empty();
	}

	// ✅ Delete issue
	public boolean deleteIssue(Long id) {
		if (issueRepository.existsById(id)) {
			issueRepository.deleteById(id);
			return true;
		}
		return false;
	}
}