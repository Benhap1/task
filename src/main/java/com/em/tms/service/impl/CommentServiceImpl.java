package com.em.tms.service.impl;

import com.em.tms.DTO.CommentCreateDTO;
import com.em.tms.DTO.CommentDTO;
import com.em.tms.entity.Comment;
import com.em.tms.entity.Task;
import com.em.tms.entity.User;
import com.em.tms.mapper.CommentMapper;
import com.em.tms.repository.CommentRepository;
import com.em.tms.repository.TaskRepository;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public CommentDTO addComment(CommentCreateDTO commentCreateDTO) {

        Task task = taskRepository.findById(commentCreateDTO.taskId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        User author = userRepository.findById(commentCreateDTO.authorId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = commentMapper.toEntity(commentCreateDTO, task, author);
        comment = commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }


    @Override
    public Page<CommentDTO> getCommentsByTaskId(int taskId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findByTaskId(taskId, pageable);
        if (commentsPage.isEmpty()) {
            throw new EntityNotFoundException("No comments found for the task with ID: " + taskId);
        }
        return commentsPage.map(commentMapper::toDTO);
    }


    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }
}
