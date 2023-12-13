package com.example.tms.serviceImpl;

import com.example.tms.exception.NotFoundObjectException;
import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.CommentRepository;
import com.example.tms.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById(Long id) throws NotFoundObjectException {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isEmpty()) throw new NotFoundObjectException("Комментарий не найден");
        return optionalComment.get();
    }

    @Override
    @Transactional
    public Comment createComment(String text, Task task, User author) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setTask(task);
        comment.setAuthor(author);
        comment.setCreateDataTime(LocalDateTime.now());
        return commentRepository.save(comment);
    }


}
