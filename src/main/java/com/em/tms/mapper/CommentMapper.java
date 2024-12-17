package com.em.tms.mapper;

import com.em.tms.web.DTO.CommentCreateDTO;
import com.em.tms.web.DTO.CommentDTO;
import com.em.tms.entity.Comment;
import com.em.tms.entity.Task;
import com.em.tms.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentCreateDTO commentCreateDTO, Task task, User author) {
        Comment comment = new Comment();
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setContent(commentCreateDTO.content());
        return comment;
    }

    public CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getTask().getId(),
                comment.getAuthor().getId(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }
}

