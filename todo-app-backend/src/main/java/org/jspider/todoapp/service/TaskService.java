package org.jspider.todoapp.service;

import org.jspider.todoapp.entity.Task;
import org.jspider.todoapp.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public ResponseEntity<?> getAllTasks() {
        List<Task> all = taskRepository.findAll();
        if(all.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task list is empty");
        return ResponseEntity.status(HttpStatus.OK).body(all);
    }

    public ResponseEntity<?> addTask(Task task) {
        Optional<Task> byTitle = taskRepository.findByTitle(task.getTitle());
        System.out.println(byTitle);
        if(byTitle.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task is already present");
        task.setCompleted(false);
        System.out.println(task);
        Task saved = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    public ResponseEntity<?> deleteTask(int id) {
        Optional<Task> optional = taskRepository.findById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No task found");
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully");
    }

    public ResponseEntity<?> findTaskByTitle(String title) {
        Optional<Task> optional = taskRepository.findByTitle(title);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Task found");
        return ResponseEntity.status(HttpStatus.OK).body(optional.get());
    }

    public ResponseEntity<?> toggleTask(int id) {
        Optional<Task> optional = taskRepository.findById(id);
        if(optional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        Task task = optional.get();
        task.setCompleted(!task.isCompleted());
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body(savedTask);
    }
}
