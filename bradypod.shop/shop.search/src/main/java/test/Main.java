package test;

import org.springframework.context.ApplicationContext;

import com.bradypod.util.spring.SpringContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext context = SpringContext.newInstance().buildContextByClassPathXml(
				"test.xml");
		 System.out.println(context.getBean("testCustom", TestBean.class).getName());  
	}
}