package com.example.demo.services;


import com.example.demo.persistences.Comment;
import com.example.demo.persistences.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private  final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // This method add comment list to the task before sending it back to the caller. For each of the comment,
    // task_id is added, user_email is added, since these fields are annotated with @Transient int the Comment Persistence.
    private Task addCommentToTask (Task task) {
        List<Comment> commentSet = commentRepository.findByTask(task)
                                                    .stream()
                                                    .map(comment -> {
                                                        comment.setTask_id(task.getId());
                                                        comment.setUser_email(userRepository.findById(comment.getUser_id())
                                                                                            .getEmail());
                                                        return comment;
                                                    })
                                                    .collect(Collectors.toList());

        task.setComments(commentSet);
        return task;
    }

    public List<Task> findTaskByStudentID (int id){

        List<Task> taskList =  taskRepository.findbystudentID(id);
        return taskList.stream()
                            .map(task -> {
                                task.setProject_id(id);
                                task = this.addCommentToTask(task);
                                return task;
                            })
                            .collect(Collectors.toList());


    }

    public Task findTaskByID(int id) {
        return addCommentToTask(taskRepository.findById(id)
                                                     .orElse(null));
    }

    public List<Task> findTaskByProjectID(int id) {
        List<Task> taskList =  taskRepository.findByProjectId(id);
        return taskList.stream()
                                    .map(task -> {
                                        task.setProject_id(id);
                                        task = this.addCommentToTask(task);
                                        return task;
                                    })
                                    .collect(Collectors.toList());

    }

    public Task addTask(Task newTask) {
        return this.taskRepository.save(newTask);
    }
}