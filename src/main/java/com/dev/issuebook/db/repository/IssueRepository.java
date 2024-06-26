package com.dev.issuebook.db.repository;

import com.dev.issuebook.entity.IssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<IssueEntity, Integer> {
	
	List<IssueEntity> findAll();

	Optional<IssueEntity> findById(Integer id);

	Optional<IssueEntity> findByUserId(Integer userId);

}
