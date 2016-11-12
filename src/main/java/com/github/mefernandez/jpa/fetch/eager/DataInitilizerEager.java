package com.github.mefernandez.jpa.fetch.eager;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizerEager {

	@Autowired
	public DataInitilizerEager(EmployeeEagerRepository employeeRepository, DepartmentEagerRepository departmentRepository) {
		// Department
		DepartmentEager department = new DepartmentEager();
		department.setName("Department");
		departmentRepository.save(department);
		
		// Employees
		int k = 100;
		
		for (int i=1; i<k; i++) {
			EmployeeEager employee = new EmployeeEager();
			employee.setName(String.valueOf(employee.hashCode()));
			employee.setDepartment(department);
			employeeRepository.save(employee);
		}
	}
}
