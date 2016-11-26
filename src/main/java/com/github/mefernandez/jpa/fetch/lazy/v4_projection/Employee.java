package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@JsonView(SummaryView.class)
	private String name;

	@JsonView(SummaryView.class)
	@ManyToOne(fetch=FetchType.LAZY)
	private Department department;

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee boss;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getBoss() {
		return boss;
	}

	public void setBoss(Employee boss) {
		this.boss = boss;
	}
}
