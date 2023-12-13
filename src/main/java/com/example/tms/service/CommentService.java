package com.example.tms.service;

import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.model.User;

public interface CommentService {
    Comment getCommentById(Long id) throws NotFoundObjectException;

    Comment createComment(String text, Task task, User author);

}
