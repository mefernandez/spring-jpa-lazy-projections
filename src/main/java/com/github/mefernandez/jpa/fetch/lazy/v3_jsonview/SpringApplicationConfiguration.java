package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@Configuration
public class SpringApplicationConfiguration {

	
	@Bean
	public Module jacksonHibernate4Module() {
		Hibernate4Module module = new Hibernate4Module();
		//module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
		return module;
	}

	/*
	@Bean
	public Module springDataPageSerializerModule(@Autowired ObjectMapper mapper) {
		JsonSerializer<Object> beanSerializer = mapper.getSerializerProviderInstance().getUnknownTypeSerializer(PageImpl.class);
		SimpleModule module = new SimpleModule();
		module.addSerializer(PageImpl.class, new PageSerializer(PageImpl.class, beanSerializer));
		return module;
	}
	*/

	/* Don't use DEFAULT_VIEW_INCLUSION for it will ignore all @JsonView */
	public void customizeObjectMapper(@Autowired ObjectMapper mapper) {
		//mapper.enable(MapperFeature.DEFAULT_VIEW_INCLUSION);
	}
	/**/
}
