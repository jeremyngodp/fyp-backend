package com.example.demo.persistences;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.DbFileDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="project_id", nullable = false)
	private Project project;

	@Transient
	private int project_id;

	@Transient
	private DbFileDTO attachedFile;

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

	@OneToMany(mappedBy = "task")
	private List<Comment> comments;

	@Column(name="hour")
	private int hourSpent;

	@Column(name="status")
	private String status;

	public Task(){}

	public Task(int student_id, Project project, String title, Date deadline, String task_type, Date created_date ) {
		this.student_id = student_id;
		this.project = project;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public void setStudent_id(int student_id) {
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getHourSpent() {
		return hourSpent;
	}

	public void setHourSpent(int hourSpent) {
		this.hourSpent = hourSpent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public DbFileDTO getAttachedFile() {
		return attachedFile;
	}

	public void setAttachedFile(DbFileDTO attachedFile) {
		this.attachedFile = attachedFile;
	}
}
