package com.github.mefernandez.jpa.fetch.lazy.v4_projection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
public class SpringApplicationConfiguration {

	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		return module;
	}
	
}

