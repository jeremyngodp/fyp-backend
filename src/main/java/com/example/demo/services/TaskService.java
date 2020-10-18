package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTaskByStudentID (int id){
        return taskRepository.findbystudentID(id);
    }

    public Task findTaskByID(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task addTask(Task newTask) {
        return this.taskRepository.save(newTask);
    }
}