package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview_custom_converter;

import java.io.IOException;
import java.lang.reflect.Type;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;

public class PageWithJsonJsonViewHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
	
	public PageWithJsonJsonViewHttpMessageConverter(MappingJackson2HttpMessageConverter converter) {
		super(converter.getObjectMapper(), converter.getSupportedMediaTypes().toArray(new MediaType[0]));
	}
	
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		return super.canWrite(PageWithJsonView.class, mediaType);
	}

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if (object instanceof MappingJacksonValue) {
			MappingJacksonValue container = (MappingJacksonValue) object;
			Object value = container.getValue();
			if (value instanceof Page) {
				Page page = (Page) value;
				PageWithJsonView<Employee> myPage = new PageWithJsonView(page);
				super.writeInternal(myPage, type, outputMessage);
				return;
			}
		}
		super.writeInternal(object, type, outputMessage);
	}

}
