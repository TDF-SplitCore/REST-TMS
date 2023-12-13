package com.example.tms.serviceImpl;

import com.example.tms.DTO.task.recent.TaskCreateDTO;
import com.example.tms.DTO.task.recent.TaskStatusDTO;
import com.example.tms.DTO.task.recent.TaskUpdateDTO;
import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.TaskRepository;
import com.example.tms.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Task getTaskById(Long id) throws NotFoundObjectException {
        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty()) throw new NotFoundObjectException("Задача не найдена");
        return optionalTask.get();
    }


    @Transactional
    public Task createTask(TaskCreateDTO taskCreateDTO, User author, User executor) {
        Task task = new Task();
        if (taskCreateDTO.description() != null) task.setDescription(taskCreateDTO.description());
        if (taskCreateDTO.header() != null) task.setHeader(taskCreateDTO.header());
        if (taskCreateDTO.priority() != null) task.setPriority(taskCreateDTO.priority());
        if (taskCreateDTO.status() != null) task.setStatus(taskCreateDTO.status());
        if (executor != null) task.setExecutor(executor);
        task.setAuthor(author);
        return taskRepository.save(task);
    }

    @Transactional
    public void updateTask(Task task, TaskUpdateDTO taskUpdateDTO, User executor) {
        if (taskUpdateDTO.description() != null) task.setDescription(taskUpdateDTO.description());
        if (taskUpdateDTO.header() != null) task.setHeader(taskUpdateDTO.header());
        if (executor != null) task.setExecutor(executor);
        if (taskUpdateDTO.priority() != null) task.setPriority(taskUpdateDTO.priority());
        if (taskUpdateDTO.status() != null) task.setStatus(taskUpdateDTO.status());
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Transactional
    public void updateExecutorTask(Task task, TaskStatusDTO taskStatusDTO) {
        task.setStatus(taskStatusDTO.status());
        taskRepository.save(task);
    }
}
