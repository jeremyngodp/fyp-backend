package com.example.demo.dto;

public class ProjectDTO {
    private int id;
    private int student_id;
    private int supervisor_id;
    private String name;
    private String description;

    public ProjectDTO() {

    }

    public ProjectDTO(int student_id, int supervisor_id, String name, String description) {
        this.student_id = student_id;
        this.supervisor_id = supervisor_id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
