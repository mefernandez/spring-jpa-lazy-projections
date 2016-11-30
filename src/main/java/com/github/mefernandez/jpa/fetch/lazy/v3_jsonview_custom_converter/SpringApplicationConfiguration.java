package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview_custom_converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

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

	/*
	@Bean
	public AbstractJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(
			@Autowired MappingJackson2HttpMessageConverter converter) {
		AbstractJackson2HttpMessageConverter jsonConverter = new PageWithJsonJsonViewHttpMessageConverter(converter);
		return jsonConverter;
	}
	*/
	
	@Bean
    public HttpMessageConverters customConverters(@Autowired MappingJackson2HttpMessageConverter converter) {
        HttpMessageConverter<?> pageWithJsonViewHttpMessageConverter = new PageWithJsonJsonViewHttpMessageConverter(converter);
        return new HttpMessageConverters(pageWithJsonViewHttpMessageConverter);
    }
}
