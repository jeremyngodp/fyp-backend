package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTaskByStudentID (int id){
        return taskRepository.findbystudentID(id);
    }

    public void addTask(int project_id, int assignee_id, String description, Date deadline) {
        Task newTask = new Task();
        newTask.setProject_id(project_id);
        newTask.setAssignee_id(assignee_id);
        newTask.setDescription(description);
        newTask.setDeadline(deadline);
    }


}
