package com.yu.util.json;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * java日期对象经过Jackson库转换成JSON日期格式化自定义类
 * 
 */
public class CustomDateSerializer extends JsonSerializer<Object> {

	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formattedDate = formatter.format(value);
		jgen.writeString(formattedDate);
	}
}