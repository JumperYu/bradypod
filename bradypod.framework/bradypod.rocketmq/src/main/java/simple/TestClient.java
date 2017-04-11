package simple;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * Created by zxm on 2017/3/27.
 */
public class TestClient {

    public static void main(String[] args) {
    	// 指定全局唯一的消息Group
        DefaultMQPushConsumer consumer =
                new DefaultMQPushConsumer("TestConcumserGroup");
        consumer.setNamesrvAddr("172.18.144.35:9876");
        try {
            /**
             * topic		:需要订阅的主题
             * subExpression:指定tag或tag1 || tag2或*
             */
            consumer.subscribe("TestTopic", "*");
            //程序使用位点记录消费
            consumer.setConsumeFromWhere(
                    ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        public ConsumeConcurrentlyStatus consumeMessage(
                                List<MessageExt> list,
                                ConsumeConcurrentlyContext Context) {
                            Message msg = list.get(0);
                            System.out.println(new String(msg.getBody()));
                            
                            /**
                             * CONSUME_SUCCESS 消费成功
                             * RECONSUME_LATER 重试
                             */
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    }
            );
            consumer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
