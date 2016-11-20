package com.github.mefernandez.jpa.fetch.lazy.v1_default;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
public class SpringApplicationConfiguration {

	/**
	 * You need this to get Page class serialized by Jackson.
	 * @return
	 */
	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		return module;
	}
}
