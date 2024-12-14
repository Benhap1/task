package com.em.tms.repository;

import com.em.tms.entity.Task;
import com.em.tms.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByAuthorId(int authorId);
    List<Task> findByAssigneeId(int assigneeId);
}