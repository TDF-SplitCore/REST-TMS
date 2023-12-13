package com.example.tms.service;

import com.example.tms.DTO.task.recent.TaskCreateDTO;
import com.example.tms.DTO.task.recent.TaskStatusDTO;
import com.example.tms.DTO.task.recent.TaskUpdateDTO;
import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Task;
import com.example.tms.model.User;

public interface TaskService {
    Task getTaskById(Long id) throws NotFoundObjectException;

    void delete(Task task);

    Task createTask(TaskCreateDTO taskCreateDTO, User author, User executor);

    void updateTask(Task task, TaskUpdateDTO taskUpdateDTO, User executor);

    void updateExecutorTask(Task task, TaskStatusDTO taskStatusDTO);
}
