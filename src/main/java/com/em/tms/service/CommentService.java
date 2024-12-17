package com.em.tms.service;

import com.em.tms.web.DTO.CommentCreateDTO;
import com.em.tms.web.DTO.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDTO addComment(CommentCreateDTO commentCreateDTO);

    Page<CommentDTO> getCommentsByTaskId(int taskId, Pageable pageable);

    void deleteComment(int commentId);
}
