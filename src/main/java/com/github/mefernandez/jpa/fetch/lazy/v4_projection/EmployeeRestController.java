package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeRestController {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/lazy/employees")
	public Page<EmployeeProjection> search(Pageable pageable, @RequestParam(required=false) String salaryFrom) {
		if (salaryFrom != null) {
			return (Page<EmployeeProjection>) employeeRepository.findBySalariesSalaryGreaterThanEqual(new BigDecimal(salaryFrom), pageable);
		}
		Page<EmployeeProjection> page = (Page<EmployeeProjection>) employeeRepository.findAllProjectedBy(pageable);
		return page;
	}
}
