package com.github.mefernandez.jpa;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
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

	//@MockBean
	//private EmployeeRepository userVehicleService;

	@Test
	public void defaultFetchTypeDoesNotSerializeEmployeeDepartment() throws Exception {
		//given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
		this.mvc.perform(get("/employees")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.numberOfElements").value("20"))
				.andExpect(jsonPath("$.content[0].name").isString())
				.andExpect(jsonPath("$.content[0].department.name").value("Department"));
	}

}
