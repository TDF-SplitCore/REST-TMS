package com.example.tms.service;

import com.example.tms.DTO.SortComment;
import com.example.tms.DTO.SortTask;
import com.example.tms.model.Comment;
import com.example.tms.model.SearchType;
import com.example.tms.model.Task;
import com.example.tms.model.User;
import org.springframework.data.domain.Page;

public interface SearchService {
    Page<Comment> searchComment(Long id, int size, int page, boolean asc, SortComment... sort);

    Page<Task> searchTask(User author, int size, int page, boolean asc, SearchType searchType, SortTask[] sort);

}
