package com.example.tms.controller;

import com.example.tms.DTO.task.recent.TaskCreateDTO;
import com.example.tms.DTO.task.recent.TaskSearchDTO;
import com.example.tms.DTO.task.recent.TaskStatusDTO;
import com.example.tms.DTO.task.recent.TaskUpdateDTO;
import com.example.tms.DTO.task.send.TaskPageSendDTO;
import com.example.tms.DTO.task.send.TaskSendDTO;
import com.example.tms.converter.Converter;
import com.example.tms.exception.BindingResultException;
import com.example.tms.exception.NoAccessException;
import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.service.AccessService;
import com.example.tms.service.SearchService;
import com.example.tms.service.TaskService;
import com.example.tms.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final AccessService accessService;
    private final HttpServletRequest request;
    private final SearchService searchService;

    public TaskController(TaskService taskService,
                          UserService userService,
                          AccessService accessService,
                          HttpServletRequest request,
                          SearchService searchService) {
        this.taskService = taskService;
        this.userService = userService;
        this.accessService = accessService;
        this.request = request;
        this.searchService = searchService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение Задачи",
            description = "Получение задачи по id в пути")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskSendDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskSendDTO getTask(@Parameter(description = "Идентификатор задачи")
                               @PathVariable Long id) throws NotFoundObjectException {
        Task task = taskService.getTaskById(id);
        return Converter.convertToTaskSendDTO(task);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Изменение задачи",
            description = "Изменение любого параметра автором")
    @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskUpdateDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskSendDTO updateTask(@Parameter(description = "Идентификатор задачи")
                                  @PathVariable Long id,
                                  @RequestBody TaskUpdateDTO taskUpdateDTO,
                                  @AuthenticationPrincipal(expression = "username") String email) throws NotFoundObjectException, NoAccessException {
        Task task = taskService.getTaskById(id);
        accessService.accessAuthorToTask(email, task);
        User executor = null;
        if (taskUpdateDTO.executor() != null) {
            executor = userService.getByLogin(taskUpdateDTO.executor());
        }
        taskService.updateTask(task, taskUpdateDTO, executor);
        return Converter.convertToTaskSendDTO(task);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создание заказа",
            description = "Создать заказ по всем пунктам")
    @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = TaskCreateDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskSendDTO createTask(@RequestBody TaskCreateDTO taskCreateDTO,
                                  @AuthenticationPrincipal User user) throws NotFoundObjectException {
        User executor = userService.getByLogin(taskCreateDTO.executor());
        Task task = taskService.createTask(taskCreateDTO, user, executor);
        return Converter.convertToTaskSendDTO(task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление Задачи",
            description = "Удаление задачи по id в пути")
    @SecurityRequirement(name = "Bearer Authentication")
    public void deleteTask(@Parameter(description = "Идентификатор задачи")
                           @PathVariable("id") Long id,
                           @AuthenticationPrincipal(expression = "username")
                           String email) throws NoAccessException, NotFoundObjectException {
        Task task = taskService.getTaskById(id);
        accessService.accessAuthorToTask(email, task);
        taskService.delete(task);
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Изменение статуса Задачи",
            description = "Изменение статуса Задачи исполнителем")
    @ApiResponse(responseCode = "201",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskSendDTO.class)))
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = TaskStatusDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskSendDTO statusTask(@Parameter(description = "Идентификатор задачи")
                                  @PathVariable Long id,
                                  @RequestBody TaskStatusDTO taskStatusDTO,
                                  @AuthenticationPrincipal(expression = "username")
                                  String email) throws NoAccessException, NotFoundObjectException {
        Task task = taskService.getTaskById(id);
        accessService.accessExecutorToTask(email, task);
        taskService.updateExecutorTask(task, taskStatusDTO);
        return Converter.convertToTaskSendDTO(task);
    }

    @GetMapping("/search")
    @Operation(summary = "Поиск задач",
            description = "Поиск задач по автору или по исполнителю а так же выдает страницами в странице от 1 до 50")
    @ApiResponse(responseCode = "200",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TaskPageSendDTO.class)))
    @SecurityRequirement(name = "Bearer Authentication")
    public TaskPageSendDTO searchTask(@Parameter(description = "Тип запроса", schema = @Schema(implementation = TaskSearchDTO.class))
                                      @ModelAttribute @Valid
                                      TaskSearchDTO taskSearchDTO,
                                      BindingResult bindingResult) throws NotFoundObjectException, BindingResultException {
        if (bindingResult.hasErrors()) throw new BindingResultException(bindingResult);
        User searchBy = userService.getByLogin(taskSearchDTO.searchBy());
        return Converter.convertPageTask(
                searchService.searchTask(
                        searchBy,
                        taskSearchDTO.size(),
                        taskSearchDTO.page(),
                        taskSearchDTO.asc(),
                        taskSearchDTO.searchType(),
                        taskSearchDTO.sort()),
                request.getRequestURI()
        );
    }
}
