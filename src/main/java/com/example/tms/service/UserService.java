package com.example.tms.service;

import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getByLogin(String login) throws NotFoundObjectException;
}
