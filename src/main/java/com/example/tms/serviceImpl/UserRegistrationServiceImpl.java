package com.example.tms.serviceImpl;

import com.example.tms.model.User;
import com.example.tms.repository.UserRepository;
import com.example.tms.service.UserRegistrationService;
import com.example.tms.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationServiceImpl(UserRepository userRepository,
                                       UserService userService,
                                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public void validateUsername(String username, Errors errors) {
        try {
            userService.getByLogin(username);
        } catch (Exception e) {
            return;
        }
        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
    }

    @Override
    @Transactional
    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
