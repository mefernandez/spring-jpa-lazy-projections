package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import com.fasterxml.jackson.annotation.JsonView;

public interface EmployeeProjection {

	String getName();

	Department getDepartment();

	Long getId();

}