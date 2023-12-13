package com.example.tms.service;

import com.example.tms.model.User;
import org.springframework.validation.Errors;

public interface UserRegistrationService {
    void validateUsername(String username, Errors errors);

    void registration(User user);
}
