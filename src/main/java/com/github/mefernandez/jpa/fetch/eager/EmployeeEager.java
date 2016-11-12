package com.github.mefernandez.jpa.fetch.eager;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class EmployeeEager {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne(fetch=FetchType.EAGER)
	private DepartmentEager department;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DepartmentEager getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEager department) {
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
