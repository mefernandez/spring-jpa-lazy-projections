package com.github.mefernandez.jpa.fetch.lazy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
public class EmployeeLazyRestController {
	
	@Autowired
	private EmployeeLazyRepository employeeRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/lazy/employees")
	public Page<EmployeeLazy> search(Pageable pageable) {
		return (Page<EmployeeLazy>) employeeRepository.findAll(pageable);
	}
}
