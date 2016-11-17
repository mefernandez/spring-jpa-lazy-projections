package com.github.mefernandez.jpa.fetch.lazy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class EmployeeLazy {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	private DepartmentLazy department;

	@ManyToOne(fetch=FetchType.LAZY)
	private EmployeeLazy boss;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartmentLazy getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentLazy department) {
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeLazy getBoss() {
		return boss;
	}

	public void setBoss(EmployeeLazy boss) {
		this.boss = boss;
	}
}
