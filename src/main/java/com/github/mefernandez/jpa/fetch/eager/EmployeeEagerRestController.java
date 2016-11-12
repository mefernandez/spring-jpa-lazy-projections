package com.github.mefernandez.jpa.fetch.eager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeEagerRestController {
	
	@Autowired
	private EmployeeEagerRepository employeeRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/eager/employees")
	public Page<EmployeeEager> search(Pageable pageable) {
		return (Page<EmployeeEager>) employeeRepository.findAll(pageable);
	}
}
