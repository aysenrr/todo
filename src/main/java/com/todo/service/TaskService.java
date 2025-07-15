package com.todo.service;

import com.todo.dto.TaskRequestDto;
import com.todo.entity.Task;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks();

    Task createTask(TaskRequestDto taskRequestDto);

    Task updateTask(Long id, TaskRequestDto taskRequestDto);

    void deleteTask(Long id);
}
