package com.example.tms.service;

import com.example.tms.exception.NoAccessException;
import com.example.tms.model.Task;

public interface AccessService {
    void accessAuthorToTask(String user, Task task) throws NoAccessException;

    void accessExecutorToTask(String user, Task task) throws NoAccessException;
}
