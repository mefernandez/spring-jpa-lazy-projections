package com.github.mefernandez.jpa.fetch.eager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/eager/employees")
	public Page<Employee> search(Pageable pageable) {
		return (Page<Employee>) employeeRepository.findAll(pageable);
	}
}
