package com.example.tms.controller;

import com.example.tms.DTO.comment.recent.CommentCreateDTO;
import com.example.tms.DTO.comment.recent.CommentSearchDTO;
import com.example.tms.DTO.comment.send.CommentPageSendDTO;
import com.example.tms.DTO.comment.send.CommentSendDTO;
import com.example.tms.DTO.task.send.TaskPageSendDTO;
import com.example.tms.converter.Converter;
import com.example.tms.exception.BindingResultException;
import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.service.CommentService;
import com.example.tms.service.SearchService;
import com.example.tms.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@Tag(name = "Comments")
public class CommentController {

    private final CommentService commentService;
    private final TaskService taskService;
    private final SearchService searchService;

    public CommentController(CommentService commentService,
                             TaskService taskService,
                             SearchService searchService) {
        this.commentService = commentService;
        this.taskService = taskService;
        this.searchService = searchService;
    }

    @GetMapping("/{idComment}")
    @Operation(method = "GET",
            summary = "Получение комментария",
            description = "Получение комментария по id")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskPageSendDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentSendDTO getComment(@Parameter(description = "Идентификатор комментария")
                                     @PathVariable("idComment") Long id) throws NotFoundObjectException {
        Comment comment = commentService.getCommentById(id);
        return Converter.convertToCommentToSendDto(comment);
    }

    @PostMapping("/task/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(method = "POST",
            summary = "Создание комментария",
            description = "Создание комментария")
    @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    schema = @Schema(implementation = CommentCreateDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentSendDTO createComment(@Parameter(description = "Идентификатор Задачи")
                                        @PathVariable("id")
                                        Long id,
                                        @RequestBody CommentCreateDTO commentCreateDTO,
                                        @AuthenticationPrincipal User author) throws NotFoundObjectException {
        Task task = taskService.getTaskById(id);
        Comment comment = commentService.createComment(commentCreateDTO.text(), task, author);
        return Converter.convertToCommentToSendDto(comment);
    }

    @GetMapping("/task/{id}")
    @Operation(method = "GET",
            summary = "Поиск комментариев",
            description = "Осуществление поиска по комментариям")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskPageSendDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public CommentPageSendDTO searchComment(@Parameter(description = "Идентификатор Задачи")
                                            @PathVariable("id")
                                            Long id,
                                            @Parameter(schema = @Schema(implementation = CommentSearchDTO.class))
                                            @Valid
                                            @ModelAttribute
                                            CommentSearchDTO commentSearchDTO,
                                            BindingResult bindingResult) throws BindingResultException {
        if (bindingResult.hasErrors()) throw new BindingResultException(bindingResult);
        Page<Comment> page = searchService.searchComment(
                id,
                commentSearchDTO.size(),
                commentSearchDTO.page(),
                commentSearchDTO.asc(),
                commentSearchDTO.sort()
        );
        return Converter.convertToPageComment(page);
    }
}
