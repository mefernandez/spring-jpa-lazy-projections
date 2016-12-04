package com.github.mefernandez.jpa.fetch.eager;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizer {

	@Autowired
	public DataInitilizer(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) throws ParseException {
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
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
		
		for (int i=1; i<k; i++) {
			Employee employee = new Employee();
			employee.setName(String.valueOf(employee.hashCode()));
			employee.setDepartment(department);
			employee.setBoss(boss);
			List<Salary> salaries = new ArrayList<Salary>();
			Salary salary = new Salary();
			salary.setFromDate(dateFormat.parse("01/01/2016"));
			salary.setToDate(dateFormat.parse("31/01/2016"));
			salary.setSalary(new BigDecimal("1925.78"));
			salaries.add(salary);
			employee.setSalaries(salaries);
			employeeRepository.save(employee);
		}
	}
}
