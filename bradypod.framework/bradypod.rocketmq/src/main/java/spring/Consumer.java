package spring;

import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class Consumer implements InitializingBean, ApplicationContextAware {

	private String group; // 由应用确保所属组名不重复
	private String address; // NameServer集群地址
	private String[] topics; // 订阅主题
	private String[] tags; // 订阅子标题
	private String[] beanNames; //
	private DefaultMQPushConsumer consumer; // 线程安全

	private ApplicationContext applicationContext;

	private static final Logger log = LoggerFactory.getLogger(Consumer.class);

	@Override
	public void afterPropertiesSet() throws Exception {
		consumer = new DefaultMQPushConsumer(group);
		consumer.setNamesrvAddr(address);
		// 程序使用位点记录消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// 订阅主题
		for (int i = 0; i < topics.length; i++) {
			consumer.subscribe(topics[i], tags[i]);
		}
		// 注册回调方法
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
				Message msg = list.get(0);

				ConsumerTask consumerTask = getTypeByName("com.xxx.Task");
				ConsumeConcurrentlyStatus status = consumerTask.execute(msg);

				/**
				 * CONSUME_SUCCESS 消费成功 RECONSUME_LATER 重试
				 */
				return status;
			}
		});
		consumer.start();
	}

	public ConsumerTask getTypeByName(String className) {
		ConsumerTask consumerTask = null;
		try {
			Class<?> clazz = Class.forName(className);
			consumerTask = (ConsumerTask) applicationContext.getBean(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return consumerTask;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
