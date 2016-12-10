package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

public interface EmployeeProjection {

	String getName();

	DepartmentProjection getDepartment();

	Long getId();

}