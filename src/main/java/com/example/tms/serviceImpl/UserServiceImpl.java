package com.example.tms.serviceImpl;

import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.User;
import com.example.tms.repository.UserRepository;
import com.example.tms.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getByLogin(String login) throws NotFoundObjectException {
        Optional<User> optionalUser = userRepository.findById(login);
        if (optionalUser.isEmpty()) throw new NotFoundObjectException("Пользователь не найден");
        return optionalUser.get();
    }
}
