package spring;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public class Producer implements InitializingBean {

	private String group; // 由应用确保所属组名不重复
	private String address; // NameServer集群地址
	private DefaultMQProducer producer; // 线程安全

	private static final Logger log = LoggerFactory.getLogger(Producer.class);

	/**
	 * 发送消息
	 * 
	 * @param topic
	 *            -- 主题
	 * @param message
	 *            -- 消息主体
	 */
	public void sendMsg(String topic, String message) {
		sendMsg(topic, "", message);
	}

	/**
	 * 发送消息
	 * 
	 * @param topic
	 *            -- 主题
	 * @param tags
	 *            -- 次标题
	 * @param message
	 *            -- 消息主体
	 */
	public void sendMsg(String topic, String tags, String message) {
		log.info("sending msg topic:{}, tags:{}, message:{}", topic, tags, message);

		Message msg = new Message(topic, tags, message.getBytes());

		try {
			SendResult sendResult = producer.send(msg);

			log.info("sending msg id:{}, status:{}", sendResult.getMsgId(), sendResult.getSendStatus().toString());
		} catch (Exception e) {
			log.error("send msg error", e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		producer = new DefaultMQProducer(group);
		producer.setNamesrvAddr(address);
		producer.start();

		log.info("Produer [{}] start success", group);
	}

	public void close() {
		if (producer != null) {
			producer.shutdown();

			log.info("Produer [{}] stop success", group);
		}
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
