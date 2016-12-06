package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
public class SpringApplicationConfiguration {

	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		return module;
	}

	// @see http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-customize-the-jackson-objectmapper
	@Bean
	public Module jacksonPageWithJsonViewModule() {
		SimpleModule module = new SimpleModule("jackson-page-with-jsonview", Version.unknownVersion());
		module.addSerializer(PageImpl.class, new PageSerializer());
		return module;
	}
	
}

