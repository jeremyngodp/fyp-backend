package com.example.demo.persistences;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name = "description")
	private String description;

	@Column(name ="name")
	private String name;

	@Column(name = "student_id")
	private Integer student_id;

	@Column(name = "supervisor_id")
	private Integer supervisor_id;

//	@JsonIgnore
	@OneToMany(mappedBy = "project")
	private List<Task> taskList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public int getSupervisor_id() {
		return supervisor_id;
	}

	public void setSupervisor_id(int supervisor_id) {
		this.supervisor_id = supervisor_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
}
