package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{

	// @see https://github.com/spring-projects/spring-data-examples/blob/master/jpa/example/src/main/java/example/springdata/jpa/projections/CustomerRepository.java
	Page<EmployeeProjection> findAllProjectedBy(Pageable page);

	Page<EmployeeProjection> findBySalariesSalaryGreaterThanEqual(BigDecimal salary, Pageable page);

}
