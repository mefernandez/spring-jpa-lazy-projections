package com.github.mefernandez.jpa;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizer {

	@Autowired
	public DataInitilizer(EmployeeRepository employeeRepository) {
		
		for (int i=1; i<100; i++) {
			Employee employee = new Employee();
			employee.setName(String.valueOf(employee.hashCode()));
			employeeRepository.save(employee);
		}
	}
}
