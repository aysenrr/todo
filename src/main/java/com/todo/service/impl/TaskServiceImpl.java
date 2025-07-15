package com.todo.service.impl;

import com.todo.dto.TaskRequestDto;
import com.todo.entity.Task;
import com.todo.entity.User;
import com.todo.repository.TaskRepository;
import com.todo.repository.UserRepository;
import com.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;


    private User getCurrentUser(){ //servis içi yardımcı metot, interface'te tanıtılmaz
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public List<Task> getAllTasks(){
        return taskRepository.findAllByUser(getCurrentUser());
    }

    @Override
    public Task createTask(TaskRequestDto taskRequestDto){
        Task task = new Task();
        task.setTitle(taskRequestDto.getTitle());
        task.setCompleted(taskRequestDto.isCompleted());
        task.setUser(getCurrentUser());
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, TaskRequestDto taskRequestDto){
        Task task = taskRepository.findById(id).orElseThrow();
        Hibernate.initialize(task.getUser());
        if(!task.getUser().getUsername().equals(getCurrentUser().getUsername()))
            throw new RuntimeException("You are not allowed to update this task.");

        task.setTitle(taskRequestDto.getTitle());
        task.setCompleted(taskRequestDto.isCompleted());
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id){
        Task task = taskRepository.findById(id).orElseThrow();
        if (!task.getUser().getUsername().equals(getCurrentUser().getUsername()))
            throw new RuntimeException("You are not allowed to delete this task.");
        taskRepository.delete(task);
    }
}
