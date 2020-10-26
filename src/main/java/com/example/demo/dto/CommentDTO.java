package com.example.demo.dto;

import java.util.Date;

public class CommentDTO {

    private  int id;

    private int task_id;

    private int user_id;

    private String user_email;

    private String content;

    private Date created_date;

    public CommentDTO() {}

    public CommentDTO(int task_id, int user_id, String user_email, String content, Date created_date) {
        this.task_id = task_id;
        this.user_email = user_email;
        this.user_id = user_id;
        this.content = content;
        this.created_date = created_date;
    }

    public CommentDTO(int task_id, int user_id, String content, Date created_date) {
        this.task_id = task_id;
        this.user_id = user_id;
        this.content = content;
        this.created_date = created_date;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
