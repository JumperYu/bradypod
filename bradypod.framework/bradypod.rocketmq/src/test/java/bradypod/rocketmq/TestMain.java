package bradypod.rocketmq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.Producer;

public class TestMain {
	
	protected static ApplicationContext applicationContext;
	
	public static void main(String[] args) {

		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		Producer producer = applicationContext.getBean(Producer.class);
		producer.sendMsg("TestTopic", "msg", "Just for test.");
	}

}
