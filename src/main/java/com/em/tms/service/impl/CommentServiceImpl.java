package com.em.tms.service.impl;

import com.em.tms.web.DTO.CommentCreateDTO;
import com.em.tms.web.DTO.CommentDTO;
import com.em.tms.entity.Comment;
import com.em.tms.entity.Task;
import com.em.tms.entity.User;
import com.em.tms.exception.ResourceNotFoundException;
import com.em.tms.mapper.CommentMapper;
import com.em.tms.repository.CommentRepository;
import com.em.tms.repository.TaskRepository;
import com.em.tms.repository.UserRepository;
import com.em.tms.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + commentCreateDTO.taskId()));

        User author = userRepository.findById(commentCreateDTO.authorId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + commentCreateDTO.authorId()));

        Comment comment = commentMapper.toEntity(commentCreateDTO, task, author);
        comment = commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public Page<CommentDTO> getCommentsByTaskId(int taskId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findByTaskId(taskId, pageable);

        if (commentsPage.isEmpty()) {
            throw new ResourceNotFoundException("No comments found for the task with ID: " + taskId);
        }

        return commentsPage.map(commentMapper::toDTO);
    }

    @Override
    public void deleteComment(int commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }
}
