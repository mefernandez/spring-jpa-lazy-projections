package com.github.mefernandez.jpa.fetch.lazy.v1_default;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{

	Page<Employee> findBySalariesSalaryGreaterThanEqual(BigDecimal salary, Pageable page);

}
