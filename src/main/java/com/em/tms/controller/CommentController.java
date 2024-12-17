package com.em.tms.controller;

import com.em.tms.DTO.CommentCreateDTO;
import com.em.tms.DTO.CommentDTO;
import com.em.tms.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Validated
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "Добавление комментария к задаче")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Комментарий успешно добавлен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные комментария"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO addComment(@Valid @RequestBody CommentCreateDTO commentCreateDTO) {
        return commentService.addComment(commentCreateDTO);
    }



    @Operation(summary = "Получение всех комментариев к задаче с пагинацией")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии успешно получены"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/task/{taskId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentDTO> getCommentsByTaskId(
            @PathVariable int taskId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return commentService.getCommentsByTaskId(taskId, pageable);
    }



    @Operation(summary = "Удаление комментария по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно удалён"),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден")
    })
    @DeleteMapping("/{commentId}")
    @Secured("ROLE_ADMIN")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);

    }
}

