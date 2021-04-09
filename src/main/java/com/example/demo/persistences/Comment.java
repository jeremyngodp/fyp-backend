package com.example.demo.persistences;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="task_id", nullable = false)
    private Task task;

    @Column(name = "user_id")
    private int user_id;

    @Column(name ="content")
    private String content;

    @Column(name= "created_date")
    private Date created_date;

    // These two value are no for storing data, it is for transferring data over the network
    @Transient
    private int task_id;

    @Transient
    private String user_email;

    public Comment() {}

    public Comment(int task_id, int user_id, String content, Date created_date) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.content = content;
        this.created_date = created_date;
    }

    public Comment(Task task, int user_id, String content, Date created_date) {
        this.task = task;
        this.user_id = user_id;
        this.content = content;
        this.created_date = created_date;
    }

    public Comment (int user_id, String content, Date created_date) {
        this. user_id = user_id;
        this.content = content;
        this.created_date = created_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
