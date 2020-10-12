package com.example.demo.models;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Date;

@Entity
public class Task {

	@Id
	@GeneratedValue
	private Integer id;
	
	private int project_id;
	
	private int assignee_id;

	private String description;

    private Date created_date;

	private Date deadline;

	private String task_type;

	public Task(){}

	public Task(int student_id, int project_id, String description, Date deadline, String task_type, Date created_date ) {
		this.assignee_id = student_id;
		this.project_id = project_id;
		this.description = description;
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

	public int getAssignee_id() {
		return assignee_id;
	}

	public void setAssignee_id(int assignee_id) {
		this.assignee_id = assignee_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
