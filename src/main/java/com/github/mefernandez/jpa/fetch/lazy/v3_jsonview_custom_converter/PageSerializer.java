package com.github.mefernandez.jpa.fetch.lazy.v3_jsonview_custom_converter;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class PageSerializer extends StdSerializer<PageImpl> {

	private JsonSerializer<Object> beanSerializer;

	protected PageSerializer(Class<PageImpl> class1, JsonSerializer<Object> beanSerializer) {
		super(class1);
		this.beanSerializer = beanSerializer;
	}

	@Override
	public void serialize(PageImpl value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		beanSerializer.serialize(value, gen, provider);
		/*
		gen.writeStartObject();
		gen.writeNumberField("number", value.getNumber());
		gen.writeEndObject();
		*/
	}

}
