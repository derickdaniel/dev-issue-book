package com.dev.issuebook.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.issuebook.entity.IssueEntity;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

	// 🔍 Find all issues by user
	List<IssueEntity> findByUserId(Integer userId);

	// 🔍 Find issues by type (e.g., "API call", "UI")
	List<IssueEntity> findByIssueType(String issueType);

	// 🔍 Find issues by resolved status
	List<IssueEntity> findByResolved(Boolean resolved);

	// 🔍 Find issues created after a specific date
	List<IssueEntity> findByCreatedAtAfter(java.time.LocalDateTime createdAt);

	// 🔍 Find issues by keyword in description
	List<IssueEntity> findByIssueDescContainingIgnoreCase(String keyword);
	
	//
	List<IssueEntity> findByIdIn(List<Long> ids);
}