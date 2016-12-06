package com.github.mefernandez.jpa.fetch.lazy.v1_default;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class DataInitilizer {
	
	@Autowired
	public DataInitilizer(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, @Value("#{environment.TOTAL_EMPLOYEES}") String totalEmployees) throws ParseException {
		// Department
		Department department = new Department();
		department.setName("Department");
		departmentRepository.save(department);
		
		// Boss
		Employee boss = new Employee();
		boss.setName("The Boss");
		employeeRepository.save(boss);
		
		// Employees
		int k = totalEmployees != null ? Integer.parseInt(totalEmployees) : 100;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es"));
		List<Employee> employees = new ArrayList<Employee>();
		int checkpoint = 100000;
		for (int i=1; i<k; i++) {
			Employee employee = new Employee();
			employee.setName(String.valueOf(employee.hashCode()).substring(0, 3));
			employee.setDepartment(department);
			employee.setBoss(boss);
			List<Salary> salaries = new ArrayList<Salary>();
			Salary salary = new Salary();
			salary.setFromDate(dateFormat.parse("01/01/2016"));
			salary.setToDate(dateFormat.parse("31/01/2016"));
			salary.setSalary(new BigDecimal(i));
			salary.setEmployee(employee);
			salaries.add(salary);
			employee.setSalaries(salaries);
			employees.add(employee);
			if (i>0 && ((i%checkpoint) == 0)) {
				System.out.println("DataInitilizer: Checkpoint " + i);
				employeeRepository.save(employees);
				employees.clear();
			}
		}
		employeeRepository.save(employees);
	}
}
