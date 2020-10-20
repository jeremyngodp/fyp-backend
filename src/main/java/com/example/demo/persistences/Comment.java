package com.example.demo.persistences;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="task_id", nullable = false)
    private Task task;

    @Column(name = "user_id")
    private int user_id;

    @Column(name ="content")
    private String content;

    @Column(name= "created_date")
    private Date created_date;

    public Comment() {}

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
