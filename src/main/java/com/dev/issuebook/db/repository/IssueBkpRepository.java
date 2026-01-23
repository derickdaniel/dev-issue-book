package com.dev.issuebook.db.repository;

import com.dev.issuebook.entity.IssueBkpEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueBkpRepository extends JpaRepository<IssueBkpEntity, Integer> {
	
	List<IssueBkpEntity> findAll();

	Optional<IssueBkpEntity> findById(Integer id);

	Optional<IssueBkpEntity> findByUserId(Integer userId);

}
