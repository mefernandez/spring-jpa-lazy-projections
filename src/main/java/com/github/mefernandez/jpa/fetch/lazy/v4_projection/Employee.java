package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	private Department department;

	@ManyToOne(fetch=FetchType.LAZY)
	private Employee boss;

	// @see Good summary on CascadeType(s) https://vladmihalcea.com/2015/03/05/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
	@OneToMany(fetch=FetchType.LAZY, mappedBy="employee", cascade = CascadeType.ALL)
	private List<Salary> salaries;

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

	public List<Salary> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<Salary> salaries) {
		this.salaries = salaries;
	}
}
