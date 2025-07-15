package com.todo.controller;

import com.todo.dto.TaskRequestDto;
import com.todo.entity.Task;
import com.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequestDto taskRequestDto){
        return ResponseEntity.ok(taskService.createTask(taskRequestDto));
    }

    @PutMapping
    public ResponseEntity<Task> updateTask(Long id, @RequestBody TaskRequestDto taskRequestDto){
        return ResponseEntity.ok(taskService.updateTask(id,taskRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted succesfully.");
    }
}
