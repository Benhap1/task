package com.em.tms.repository;

import com.em.tms.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskId(int taskId);
    List<Comment> findByAuthorId(int authorId);
}
