package com.example.demo.services;


import com.example.demo.dto.DbFileDTO;
import com.example.demo.persistences.Comment;
import com.example.demo.persistences.DbFile;
import com.example.demo.persistences.Task;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.DbFileRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final DbFileRepository dbFileRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, CommentRepository commentRepository,
                       UserRepository userRepository, DbFileRepository dbFileRepository) {
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.dbFileRepository = dbFileRepository;
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

    private Task addAttachedFileToTask (Task task) {
        DbFile file = dbFileRepository.findByTaskId(task.getId()).orElse(null);

        if (file != null) {
            DbFileDTO fileDTO = new DbFileDTO();
            fileDTO.setId(file.getId());
            fileDTO.setFileName(file.getFileName());
            fileDTO.setFileType(file.getFileType());
            fileDTO.setTask_id(file.getTask_id());
            fileDTO.setUploadDate(file.getUploadDate());
            task.setAttachedFile(fileDTO);
        }

        return task;
    }

    public List<Task> findTaskByStudentID (int id) throws EntityNotFoundException {

        List<Task> taskList =  taskRepository.findbystudentID(id);

            return taskList.stream()
                    .map(task -> {
                        task.setProject_id(id);
                        task = this.addCommentToTask(task);
                        task = this.addAttachedFileToTask(task);
                        return task;
                    })
                    .collect(Collectors.toList());

    }

    public Task findTaskByID(int id) {
        Task task = taskRepository.findById(id).orElse(null);
        return addAttachedFileToTask(addCommentToTask(task));
    }

    public List<Task> findTaskByProjectID(int id) {
        List<Task> taskList =  taskRepository.findByProjectId(id);
        return taskList.stream()
                                    .map(task -> {
                                        task.setProject_id(id);
                                        task = this.addCommentToTask(task);
                                        task = this.addAttachedFileToTask(task);
                                        return task;
                                    })
                                    .collect(Collectors.toList());

    }

    public Task addTask(Task newTask) {
        return this.taskRepository.save(newTask);
    }
}