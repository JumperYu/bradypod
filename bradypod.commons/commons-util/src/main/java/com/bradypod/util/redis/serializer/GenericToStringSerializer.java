package com.bradypod.util.redis.serializer;

import java.nio.charset.Charset;

import org.springframework.beans.BeansException;
import org.springframework.beans.TypeConverter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

public class GenericToStringSerializer<T> implements RedisSerializer<T>, BeanFactoryAware {

	private final Charset charset;
	private Converter converter = new Converter(new DefaultConversionService());
	private Class<T> type;

	public GenericToStringSerializer(Class<T> type) {
		this(type, Charset.forName("UTF8"));
	}

	public GenericToStringSerializer(Class<T> type, Charset charset) {
		Assert.notNull(type);
		this.type = type;
		this.charset = charset;
	}

	public void setConversionService(ConversionService conversionService) {
		Assert.notNull(conversionService, "non null conversion service required");
		converter = new Converter(conversionService);
	}

	public void setTypeConverter(TypeConverter typeConverter) {
		Assert.notNull(typeConverter, "non null type converter required");
		converter = new Converter(typeConverter);
	}

	public T deserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}

		String string = new String(bytes, charset);
		return converter.convert(string, type);
	}

	public byte[] serialize(T object) {
		if (object == null) {
			return null;
		}
		String string = converter.convert(object, String.class);
		return string.getBytes(charset);
	}

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		if (converter == null && beanFactory instanceof ConfigurableBeanFactory) {
			ConfigurableBeanFactory cFB = (ConfigurableBeanFactory) beanFactory;
			ConversionService conversionService = cFB.getConversionService();

			converter = (conversionService != null ? new Converter(conversionService) : new Converter(cFB.getTypeConverter()));
		}
	}

	private class Converter {
		private final ConversionService conversionService;
		private final TypeConverter typeConverter;

		public Converter(ConversionService conversionService) {
			this.conversionService = conversionService;
			this.typeConverter = null;
		}

		public Converter(TypeConverter typeConverter) {
			this.conversionService = null;
			this.typeConverter = typeConverter;
		}

		<E> E convert(Object value, Class<E> targetType) {
			if (conversionService != null) {
				return conversionService.convert(value, targetType);
			}
			return typeConverter.convertIfNecessary(value, targetType);
		}
	}
}

