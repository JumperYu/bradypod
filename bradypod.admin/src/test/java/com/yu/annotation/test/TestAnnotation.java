package com.yu.annotation.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.yu.retention.CustomerAnnotationType;
import com.yu.retention.CustomerAnntotationMethod;

@CustomerAnnotationType(author = "zengxm", date = "2015-05-18", description = "这是一个注解测试")
public class TestAnnotation {
	
	@CustomerAnntotationMethod(author = "zengxm", date = "2015-05-18", description = "这是一个注解测试")
	public void test(){
		
	}
	
	public static void main(String[] args) {
		Class<TestAnnotation> object = TestAnnotation.class;
		// Retrieve all annotations from the class
		Annotation[] annotations = object.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println("----" + annotation);
		}

		// Checks if an annotation is present
		if (object.isAnnotationPresent(CustomerAnnotationType.class)) {

			// Gets the desired annotation
			CustomerAnnotationType annotation = (CustomerAnnotationType)object
					.getAnnotation(CustomerAnnotationType.class);
			System.out.println(annotation.author());

		}
		// the same for all methods of the class
		for (Method method : object.getDeclaredMethods()) {

			if (method.isAnnotationPresent(CustomerAnntotationMethod.class)) {

				Annotation annotation = method
						.getAnnotation(CustomerAnntotationMethod.class);

				System.out.println(annotation);
			}
		}

	}

}
