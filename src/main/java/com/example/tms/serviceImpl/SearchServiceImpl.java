package com.example.tms.serviceImpl;

import com.example.tms.DTO.SortComment;
import com.example.tms.DTO.SortTask;
import com.example.tms.model.Comment;
import com.example.tms.model.SearchType;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import com.example.tms.repository.CommentRepository;
import com.example.tms.repository.TaskRepository;
import com.example.tms.service.SearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchServiceImpl implements SearchService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public SearchServiceImpl(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Page<Comment> searchComment(Long id,
                                       int size,
                                       int page,
                                       boolean asc,
                                       SortComment... sort) {
        String[] sortStr = new String[sort.length];
        for (int i = 0; i < sort.length; i++) {
            sortStr[i] = sort[i].toString().toLowerCase();
        }
        Sort sortBy = Sort.by(sortStr);
        if (asc) sortBy = sortBy.ascending();
        else sortBy = sortBy.descending();
        Pageable pageable = PageRequest.of(page, size, sortBy);
        return commentRepository.findByTaskId(id, pageable);
    }

    @Override
    @Transactional
    public Page<Task> searchTask(User author,
                                 int size,
                                 int page,
                                 boolean asc,
                                 SearchType searchType,
                                 SortTask[] sort) {
        String[] sortStr = new String[sort.length];
        for (int i = 0; i < sort.length; i++) {
            sortStr[i] = sort[i].toString().toLowerCase();
        }
        Sort sortBy = Sort.by(sortStr);
        if (asc) sortBy = sortBy.ascending();
        else sortBy = sortBy.descending();
        Pageable pageable = PageRequest.of(page, size, sortBy);
        Page<Task> task = null;
        if (searchType == SearchType.AUTHOR) {
            task = taskRepository.findByAuthor(author, pageable);
        } else if (searchType == SearchType.EXECUTOR) {
            task = taskRepository.findByExecutor(author, pageable);
        }
        return task;
    }
}
