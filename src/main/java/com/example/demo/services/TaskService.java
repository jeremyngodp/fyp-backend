package com.example.demo.services;

import com.example.demo.dto.CommentDTO;
import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentService commentService;

    @Autowired
    public TaskService(TaskRepository taskRepository, CommentService commentService) {
        this.taskRepository = taskRepository;
        this.commentService = commentService;
    }

    private Task addCommentToTask (Task task) {
        List<Comment> commentSet = commentService.findCommentByTask(task);
        List<CommentDTO> commentDTOList = commentService.toDTOList(commentSet);
        task.setCommentsDTO(commentDTOList);
        return task;
    }

    public List<Task> findTaskByStudentID (int id){

        List<Task> taskList =  taskRepository.findbystudentID(id);
        taskList.stream()
                .map(this::addCommentToTask)
                .collect(Collectors.toList());

        return taskList;
    }

    public Task findTaskByID(int id) {
        Task result = addCommentToTask(taskRepository.findById(id)
                                                     .orElse(null));
        return result;
    }

    public List<Task> findTaskByProjectID(int id) {
        List<Task> taskList =  taskRepository.findByProjectId(id);
        taskList.stream()
                .map(this::addCommentToTask)
                .collect(Collectors.toList());

        return taskList;
    }

    public Task addTask(Task newTask) {
        return this.taskRepository.save(newTask);
    }
}