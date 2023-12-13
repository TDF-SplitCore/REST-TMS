package com.example.tms.converter;

import com.example.tms.DTO.auth.recent.RegistrationDTO;
import com.example.tms.DTO.comment.send.CommentPageSendDTO;
import com.example.tms.DTO.comment.send.CommentSendDTO;
import com.example.tms.DTO.task.send.TaskPageElementSendDTO;
import com.example.tms.DTO.task.send.TaskPageSendDTO;
import com.example.tms.DTO.task.send.TaskSendDTO;
import com.example.tms.model.Comment;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import org.springframework.data.domain.Page;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static User convertToUser(RegistrationDTO registrationDTO) {
        return new User(
                registrationDTO.username(),
                registrationDTO.password(),
                registrationDTO.role()
        );
    }

    public static TaskSendDTO convertToTaskSendDTO(Task task) {
        return new TaskSendDTO(
                task.getId(),
                task.getHeader(),
                task.getDescription(),
                task.getPriority(),
                task.getStatus(),
                task.getAuthor().getUsername(),
                task.getExecutor().getUsername()
        );
    }

    public static TaskPageSendDTO convertPageTask(Page<Task> page, String urlApiCommentSearch) {
        List<TaskPageElementSendDTO> list = new ArrayList<>();
        for (Task t : page) {
            list.add(
                    new TaskPageElementSendDTO(
                            t.getId(),
                            t.getHeader(),
                            t.getDescription(),
                            t.getPriority(),
                            t.getStatus(),
                            t.getAuthor().getUsername(),
                            t.getExecutor().getUsername(),
                            urlApiCommentSearch + t.getId()
                    )
            );
        }
        return new TaskPageSendDTO(
                list,
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast(),
                page.isFirst(),
                page.getSize(),
                page.getNumber(),
                page.getSort(),
                page.getNumberOfElements(),
                page.isEmpty()
        );
    }

    public static CommentSendDTO convertToCommentToSendDto(Comment comment) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        return new CommentSendDTO(
                comment.getId(),
                comment.getText(),
                comment.getAuthor().getUsername(),
                comment.getTask().getHeader(),
                comment.getCreateDataTime().format(dateTimeFormatter)
        );
    }

    public static CommentPageSendDTO convertToPageComment(Page<Comment> page) {
        List<CommentSendDTO> list = new ArrayList<>();
        for (Comment c : page) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
            list.add(new CommentSendDTO(
                            c.getId(),
                            c.getText(),
                            c.getAuthor().getUsername(),
                            c.getTask().getHeader(),
                            c.getCreateDataTime().format(dateTimeFormatter)
                    )
            );
        }
        return new CommentPageSendDTO(
                list,
                page.getTotalPages(),
                page.getTotalElements(),
                page.isLast(),
                page.isFirst(),
                page.getSize(),
                page.getNumber(),
                page.getSort(),
                page.getNumberOfElements(),
                page.isEmpty()
        );
    }
}
