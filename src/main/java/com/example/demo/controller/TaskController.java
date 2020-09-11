package com.example.demo.controller;

import com.example.demo.models.Task;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;


@RestController
@RequestMapping("fyp/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/student-{student_id}/all")
    public List<Task> findTaskbyStudentID(@RequestParam int student_id){
        return taskService.findTaskByStudentID(student_id);
    }

    @PostMapping("/add")
    public void addTask (@RequestParam int student_id,
                         @RequestParam int project_id,
                         @RequestParam String description,
                         @RequestParam Date deadline ) {
        taskService.addTask(project_id, student_id, description, deadline);
    }

}
