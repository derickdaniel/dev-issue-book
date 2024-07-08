package com.dev.issuebook.db.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.issuebook.entity.TaskEntity;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

	List<TaskEntity> findAll();

	Optional<TaskEntity> findById(Integer id);

	List<TaskEntity> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
	
	Optional<TaskEntity> findByUserIdAndCreatedDate(int userId, LocalDate date);
	
}
