package com.example.tms;

import com.example.tms.model.Comment;
import com.example.tms.model.Role;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.CommentRepository;
import com.example.tms.repository.TaskRepository;
import com.example.tms.repository.UserRepository;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication

public class TmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmsApplication.class, args);
    }
}
