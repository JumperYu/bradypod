package com.yu.retention;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomerAnntotationMethod {
	
	String author() default "zengxm";
	
	String date();
	
	String description();
	
}
