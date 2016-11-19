package com.github.mefernandez.jpa.fetch.lazy.v2_force;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizer {

	@Autowired
	public DataInitilizer(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
		// Department
		Department department = new Department();
		department.setName("Department");
		departmentRepository.save(department);
		
		// Boss
		Employee boss = new Employee();
		boss.setName("The Boss");
		employeeRepository.save(boss);
		
		// Employees
		int k = 100;
		
		for (int i=1; i<k; i++) {
			Employee employee = new Employee();
			employee.setName(String.valueOf(employee.hashCode()));
			employee.setDepartment(department);
			employee.setBoss(boss);
			employeeRepository.save(employee);
		}
	}
}
