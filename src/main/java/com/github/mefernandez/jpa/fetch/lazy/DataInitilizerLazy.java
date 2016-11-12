package com.github.mefernandez.jpa.fetch.lazy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizerLazy {

	@Autowired
	public DataInitilizerLazy(EmployeeLazyRepository employeeRepository, DepartmentLazyRepository departmentRepository) {
		// Department
		DepartmentLazy department = new DepartmentLazy();
		department.setName("Department");
		departmentRepository.save(department);
		
		// Employees
		int k = 100;
		
		for (int i=1; i<k; i++) {
			EmployeeLazy employee = new EmployeeLazy();
			employee.setName(String.valueOf(employee.hashCode()));
			employee.setDepartment(department);
			employeeRepository.save(employee);
		}
	}
}
