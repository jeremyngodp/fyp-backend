package com.example.demo.services;

import com.example.demo.dto.DbFileDTO;
import com.example.demo.persistences.DbFile;
import com.example.demo.persistences.Project;
import com.example.demo.persistences.Task;
import com.example.demo.persistences.User;
import com.example.demo.repository.DbFileRepository;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final DbFileRepository dbFileRepository;

    @Autowired
    public ProjectService (ProjectRepository projectRepository, TaskRepository taskRepository,
                           UserRepository userRepository, DbFileRepository dbFileRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.dbFileRepository = dbFileRepository;
    }

    public List<Project> findAll() {
        List<Project> result =  this.projectRepository.findAll()
                .stream()
                .map(project -> {
                    project = this.addTaskList(project);
                    project = this.addStudentEntity(project);
                    return project;
                })
                .collect(Collectors.toList());
        return result;
    }

    public List<Project> findBySupervisorID (int id) {
        List<Project> result =  this.projectRepository.findBySupervisorIDEqual(id)
                                                    .stream()
                                                    .map(project -> {
                                                        project = this.addTaskList(project);
                                                        project = this.addStudentEntity(project);
                                                        return project;
                                                    })
                                                    .collect(Collectors.toList());
        return result;
    }

    public List<Project> findbyStudentID (int id) {
        List<Project> result = this.projectRepository.findByStudentIDEqual(id)
                                                     .stream()
                                                     .map(project -> {
                                                         project = this.addTaskList(project);
                                                         project = this.addStudentEntity(project);
                                                         return project;
                                                     })
                                                     .collect(Collectors.toList());

        return result;
    }

    public Project findById (int id) {
        Project result = this.projectRepository.findById(id).orElse(null);

        result = this.addTaskList(result);
        result = this.addStudentEntity(result);

        return result;
    }

    private Project addTaskList (Project project) {
        int project_id = project.getId();

        // For each task in the taskList, there is a commentList for that task. The inner loop (inner .stream().map() )
        // will add the user_email and task_id to each of the comment since these fields are marked with @Transient.
        List<Task> taskList= taskRepository.findByProjectId(project_id)
                                           .stream()
                                           .map(task -> {
                                                    task.getComments()
                                                        .stream()
                                                        .map(comment -> {
                                                                            comment.setTask_id(task.getId());
                                                                            comment.setUser_email(userRepository.findById(comment.getUser_id())
                                                                                   .getEmail());
                                                                            return comment;
                                                                        })
                                                        .collect(Collectors.toList());

                                                    DbFile file = dbFileRepository.findByTaskId(task.getId()).orElse(null);

                                                    if (file != null) {
                                                        DbFileDTO fileDTO = new DbFileDTO();
                                                        fileDTO.setTask_id(file.getTask_id());
                                                        fileDTO.setFileType(file.getFileType());
                                                        fileDTO.setFileName(file.getFileName());
                                                        fileDTO.setId(file.getId());
                                                        fileDTO.setUploadDate(file.getUploadDate());
                                                        task.setAttachedFile(fileDTO);
                                                    }

                                                    task.setProject_id(project_id);

                                                    return task;
                                            })
                                           .collect(Collectors.toList());

        project.setTaskList(taskList);

        return project;
    }

    private Project addStudentEntity(Project project) {
        int student_id = project.getStudent_id();
        if (student_id> 0) {
            User student = userRepository.findById(student_id);
            project.setStudent(student);
        }

        return project;
    }

    public Project addProject (Project project) {
        return projectRepository.save(project);
    }
}
