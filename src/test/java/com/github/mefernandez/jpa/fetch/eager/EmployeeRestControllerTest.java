package com.github.mefernandez.jpa.fetch.eager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeRestControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();


	@Test
	public void eagerFetchTypeSerializesAllProperties() throws Exception {
		this.mvc.perform(get("/eager/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[1].name").isString())
				.andExpect(jsonPath("$.content[1].boss.name").isString())
				.andExpect(jsonPath("$.content[1].department.name").isString());
	}

	@Test
	public void eagerFetchTypeQueriesEmployeeTableTwice() throws Exception {
		this.mvc.perform(get("/eager/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(2, count("select .* from employee", systemOutRule.getLog()));
	}

	@Test
	public void eagerFetchTypeQueriesDepartmentTableOnce() throws Exception {
		this.mvc.perform(get("/eager/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(1, count("select .* from department", systemOutRule.getLog()));
	}

	@Test
	public void eagerFetchTypeQueriesSalaries20Times() throws Exception {
		this.mvc.perform(get("/eager/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(20, count("select .* from salary", systemOutRule.getLog()));
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
