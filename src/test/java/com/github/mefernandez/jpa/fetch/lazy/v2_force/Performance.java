package com.github.mefernandez.jpa.fetch.lazy.v2_force;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.carrotsearch.junitbenchmarks.AbstractBenchmark;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Performance extends AbstractBenchmark {

	@Autowired
	private MockMvc mvc;
	
	private int totalEmployees;
	
	@Before
	public void setup() {
		String totalEmployees = System.getenv("TOTAL_EMPLOYEES");
		this.totalEmployees = totalEmployees != null ? Integer.parseInt(totalEmployees) : 100;
	}
	
	@Test
	public void testPerformanceGetFirstPage() throws Exception {
		String expectedTotalCount = String.valueOf(totalEmployees);
		this.mvc.perform(get("/lazy/employees")
				.param("page", "1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(expectedTotalCount));
	}

	@Test
	public void testPerformanceGetLastPage() throws Exception {
		String lastPage = getLastPage(20);
		String expectedTotalCount = String.valueOf(totalEmployees);
		this.mvc.perform(get("/lazy/employees")
				.param("page", lastPage)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalPages").value(lastPage))
				.andExpect(jsonPath("$.totalElements").value(expectedTotalCount));
	}

	@Test
	public void testPerformanceSearchBySalaryExpectingToMatchHalfOfTotalEmployees() throws Exception {
		String salaryFrom = String.valueOf((int)totalEmployees / 2);
		String expectedTotalCount = salaryFrom;
		this.mvc.perform(get("/lazy/employees")
				.param("salaryFrom", salaryFrom)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(expectedTotalCount));
	}

	@Test
	public void testPerformanceGetAllEmployeesInOnePage() throws Exception {
		String expectedTotalCount = String.valueOf(totalEmployees);
		String pageSize = String.valueOf(totalEmployees);
		this.mvc.perform(get("/lazy/employees")
				.param("page", "1")
				.param("size", pageSize)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalElements").value(expectedTotalCount));
	}

	private String getLastPage(int pageSize) {
		int division = (int)totalEmployees / pageSize;
		if (division == 0) {
			division = 1;
		}
		return String.valueOf(division);
	}
}
