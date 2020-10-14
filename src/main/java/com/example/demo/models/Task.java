package com.example.demo.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="task")
public class Task {

	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;

	@Column(name="project_id")
	private int project_id;

	@Column(name="student_id")
	private int student_id;

	@Column(name="title")
	private String title;

	@Column(name="created_date")
    private Date created_date;

	@Column(name="deadline")
	private Date deadline;

	@Column(name="task_type")
	private String task_type;

	public Task(){}

	public Task(int student_id, int project_id, String title, Date deadline, String task_type, Date created_date ) {
		this.student_id = student_id;
		this.project_id = project_id;
		this.title = title;
		this.deadline = deadline;
		this.task_type = task_type;
		this.created_date = created_date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int assignee_id) {
		this.student_id = student_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getTask_type() {
        return task_type;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }
}
