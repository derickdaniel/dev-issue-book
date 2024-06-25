package com.dev.issuebook.db.repository;

import com.dev.issuebook.entity.IssueEntity;
import com.microservice.authservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
	
	void createIssue(IssueEntity entity);

	Optional<IssueEntity> listIssues(String username);

	IssueEntity findIssueById(Integer id);

	IssueEntity findIssueByUserId(Integer userId);

}
