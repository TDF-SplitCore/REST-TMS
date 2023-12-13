package com.example.tms.serviceImpl;

import com.example.tms.exception.NoAccessException;
import com.example.tms.model.Task;
import com.example.tms.service.AccessService;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {
    @Override
    public void accessAuthorToTask(String user, Task task) throws NoAccessException {
        if (!task.getAuthor().getUsername().equals(user))
            throw new NoAccessException("Пользователь не является автором");
    }

    @Override
    public void accessExecutorToTask(String user, Task task) throws NoAccessException {
        if (!task.getExecutor().getUsername().equals(user))
            throw new NoAccessException("Пользователь не является исполнителем");
    }
}
