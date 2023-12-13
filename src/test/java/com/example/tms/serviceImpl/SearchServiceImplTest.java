package com.example.tms.serviceImpl;

import com.example.tms.DTO.SortComment;
import com.example.tms.DTO.SortTask;
import com.example.tms.model.SearchType;
import com.example.tms.model.User;
import com.example.tms.repository.CommentRepository;
import com.example.tms.repository.TaskRepository;
import net.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Parameter;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class SearchServiceImplTest {
    @InjectMocks
    private SearchServiceImpl searchService;
    @Mock
    private  CommentRepository commentRepository;
    @Mock
    private  TaskRepository taskRepository;

    @Test
    void searchComment_ascFalse() {
        long id = 1L;
        int page = 0;
        int size = 1;
        boolean asc = false;
        SortComment sortComment  = SortComment.ID;
        Sort sortBy = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(page,size,sortBy);
        searchService.searchComment(id,size,page,asc,sortComment);
        Mockito.verify(commentRepository).findByTaskId(id,pageable);
    }
    @Test
    void searchComment_ascTrue() {
        long id = 1L;
        int page = 0;
        int size = 1;
        boolean asc = true;
        SortComment sortComment1  = SortComment.ID;
        SortComment sortComment2 = SortComment.AUTHOR;
        Sort sortBy = Sort.by("id","author").ascending();
        Pageable pageable = PageRequest.of(page,size,sortBy);
        searchService.searchComment(id,size,page,asc,sortComment1,sortComment2);
        Mockito.verify(commentRepository).findByTaskId(id,pageable);
    }

    @Test
    void searchTask_ascTrue_Author() {
        int page = 0;
        int size = 1;
        SearchType searchType = SearchType.AUTHOR;
        boolean asc = true;
        SortTask[] sortTasks  = new SortTask[]{SortTask.ID,SortTask.AUTHOR};
        Sort sortBy = Sort.by("id","author").ascending();
        Pageable pageable = PageRequest.of(page,size,sortBy);
        User user = new User();
        searchService.searchTask(user,size,page,asc,searchType,sortTasks);
        Mockito.verify(taskRepository).findByAuthor(user,pageable);
    }
    @Test
    void searchTask_ascTrue_Executor() {
        int page = 0;
        int size = 1;
        SearchType searchType = SearchType.EXECUTOR;
        boolean asc = false;
        SortTask[] sortTasks  = new SortTask[]{SortTask.ID,SortTask.AUTHOR};
        Sort sortBy = Sort.by("id","author").descending();
        Pageable pageable = PageRequest.of(page,size,sortBy);
        User user = new User();
        searchService.searchTask(user,size,page,asc,searchType,sortTasks);
        Mockito.verify(taskRepository).findByExecutor(user,pageable);
    }
}