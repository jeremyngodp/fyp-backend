package com.example.demo.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    int id;

    @Column(name = "task_id")
    int task_id;

    @Column(name = "user_id")
    int user_id;

    @Column(name ="content")
    String content;

    @Column(name= "created_date")
    Date created_date;

    public Comment() {}

    public Comment (int task_id, int user_id, String content, Date created_date) {
        this.task_id = task_id;
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
