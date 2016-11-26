package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

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

	@RequestMapping(method = RequestMethod.GET, value = "/lazy/employees")
	public Page<EmployeeProjection> search(Pageable pageable) {
		Page<EmployeeProjection> page = (Page<EmployeeProjection>) employeeRepository.findAllProjectedBy(pageable);
		PageWithJsonView<EmployeeProjection> myPage = new PageWithJsonView(page);
		return myPage;
	}
}
