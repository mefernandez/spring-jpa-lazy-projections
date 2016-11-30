package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview_custom_converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class EmployeeRestController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@JsonView(SummaryView.class)
	@RequestMapping(method = RequestMethod.GET, value = "/lazy/employees")
	public Page<Employee> search(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}
}