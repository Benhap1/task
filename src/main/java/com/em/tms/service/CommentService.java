package com.em.tms.service;

import com.em.tms.DTO.CommentCreateDTO;
import com.em.tms.DTO.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(CommentCreateDTO commentCreateDTO);

    Page<CommentDTO> getCommentsByTaskId(int taskId, Pageable pageable);

    void deleteComment(int commentId);
}
