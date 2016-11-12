package com.github.mefernandez.jpa.fetch.eager;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeEagerRepository extends PagingAndSortingRepository<EmployeeEager, Long>{

}
