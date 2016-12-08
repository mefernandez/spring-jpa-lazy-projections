package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeRestControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();


	@Test
	public void lazyFetchTypeSerializesEmployeeName() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[1].name").isString());
	}

	@Test
	public void lazyFetchTypeDoesNotSerializesBoss() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[1].boss").doesNotExist());
	}

	@Test
	public void lazyFetchTypeSerializesDepartmentName() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[1].department.name").isString());
	}

	@Test
	public void lazyFetchTypeSerializesSalaries() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[1].salaries").doesNotExist());
	}

	@Test
	public void lazyFetchTypeQueriesEmployeeTableTwice() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(2, count("select .* from employee", systemOutRule.getLog()));
	}

	@Test
	public void lazyFetchTypeQueriesDepartmentTableOnce() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(1, count("select .* from department", systemOutRule.getLog()));
	}

	@Test
	public void lazyFetchTypeQueriesSalariesZeroTimes() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(0, count("select .* from salary", systemOutRule.getLog()));
	}

	@Test
	public void lazyFetchTypeTotalQueries() throws Exception {
		this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(3, count("select .* from", systemOutRule.getLog()));
	}

	@Test
	public void lazyFetchJSONTotalCharacters() throws Exception {
		MvcResult response = this.mvc.perform(get("/lazy/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		assertEquals(1862, response.getResponse().getContentAsString().length());
	}
	private int count(String regex, String log) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(log);
		int count = 0;
		while (matcher.find()) {
			count ++;
		}
		return count;
	}

}
