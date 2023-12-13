package com.example.tms.repository;

import com.example.tms.model.Task;
import com.example.tms.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findByAuthor(User user, Pageable pageable);
    Page<Task> findByExecutor(User user, Pageable pageable);
}
