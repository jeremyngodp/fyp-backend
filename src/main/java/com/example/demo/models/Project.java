package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;

	private String description;

	private String name;
	
	private Integer supervisor_id;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}
