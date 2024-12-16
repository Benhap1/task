package com.em.tms.service;

import com.em.tms.entity.Task;
import com.em.tms.entity.TaskPriority;
import com.em.tms.entity.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {

    public static Specification<Task> hasStatus(TaskStatus status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<Task> hasPriority(TaskPriority priority) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("priority"), priority);
    }

    public static Specification<Task> hasAuthorEmail(String authorEmail) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("author").get("email"), authorEmail);
    }

    public static Specification<Task> hasAssigneeEmail(String assigneeEmail) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("assignee").get("email"), assigneeEmail);
    }
}

