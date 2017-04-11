package spring;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.common.message.Message;

public interface ConsumerTask {

	public ConsumeConcurrentlyStatus execute(Message msg);
}
