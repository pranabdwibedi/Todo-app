package org.jspider.todoapp.controller;

import org.jspider.todoapp.entity.Task;
import org.jspider.todoapp.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping("/{title}")
    public ResponseEntity<?> findTaskByTitle(@PathVariable String title){
        return taskService.findTaskByTitle(title);
    }
    @GetMapping
    public ResponseEntity<?> getAllTasks(){
        return taskService.getAllTasks();
    }
    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task){
        return taskService.addTask(task);
    }

    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleTask(@PathVariable int id){
        return taskService.toggleTask(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable int id){
        return taskService.deleteTask(id);
    }
}
