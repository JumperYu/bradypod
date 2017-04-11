package simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * Created by zxm on 2017/3/27.
 */
public class TestProducer {

	public static void main(String[] args) {

		// 定义生产者Group
		DefaultMQProducer producer = new DefaultMQProducer("TestProducerGroup");
		// 指定NameServer地址
		producer.setNamesrvAddr("172.18.144.35:9876");
		try {
			producer.start();

			/**
			 * topic:消息主题
			 * tags :子标题
			 * body :消息主体 	
			 */
			Message msg = new Message("TestTopic", "msg", "Just for test.".getBytes());

			SendResult result = producer.send(msg);
			
			// 正常状态为SEND_OK
			System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}
}
