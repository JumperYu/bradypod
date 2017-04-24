package simple;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionExecuter;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 根据sendResult找到Prepared消息，sendResult包含事务消息的ID 根据localTransaction更新消息的最终状态
 * 如果endTransaction方法执行失败，数据没有发送到broker，导致事务消息的状态更新失败，
 * broker会有回查线程定时（默认1分钟）扫描每个存储事务状态的表格文件， 如果是已经提交或者回滚的消息直接跳过，
 * 如果是prepared状态则会向Producer发起CheckTransaction请求，
 * Producer会调用DefaultMQProducerImpl.checkTransactionState()方法来处理broker的定时回调请求，
 * 而checkTransactionState会调用我们的事务设置的决断方法来决定是回滚事务还是继续执行，
 * 最后调用endTransactionOneway让broker来更新消息的最终状态。
 * 
 * 
 * @author zengxiangmin<zengxiangmin@cvte.com>
 *
 * @date 2017年4月13日
 */
public class TestProducerTransaction {

	public static void main(String[] args) throws MQClientException, InterruptedException {
		/**
		 * 未决事务，MQ服务器回查客户端 当RocketMQ发现`Prepared消息`时，会根据这个Listener实现的策略来决断事务
		 */
		TransactionCheckListener transactionCheckListener = new TransactionCheckListener() {

			@Override
			public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
				System.out.println("prepared msg");
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		};
		String groupId = "uniqueConsumerGroup1";
		TransactionMQProducer producer = new TransactionMQProducer(groupId);
		producer.setNamesrvAddr("172.18.144.35:9876");
		producer.setCheckThreadPoolMinSize(2);
		producer.setCheckThreadPoolMaxSize(2);
		producer.setCheckRequestHoldMax(2000);
		producer.setTransactionCheckListener(transactionCheckListener);

		final AtomicInteger atomicInteger = new AtomicInteger(0);

		// 事务决断
		LocalTransactionExecuter localTransactionExecuter = new LocalTransactionExecuter() {

			@Override
			public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
				System.out.println("send msg branch " + (new String(msg.getBody())) + " " + arg);
				int count = atomicInteger.getAndIncrement();
				if (count % 2 == 0) {
					return LocalTransactionState.COMMIT_MESSAGE;
				} else if (count == 5) {
					return LocalTransactionState.UNKNOW;
				} else {
					return LocalTransactionState.ROLLBACK_MESSAGE;
				}
			}
		};

		producer.start();

		for (int i = 0; i < 10; i++) {
			// 构造MSG，省略构造参数
			Message msg = new Message("TestTopic", "tag", ("hi_" + i).getBytes());
			// 发送消息
			SendResult sendResult = producer.sendMessageInTransaction(msg, localTransactionExecuter, null);
			// 发送完成
			System.out.println(sendResult.getMsgId());
		}

		// wait
		// TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
		// shutdown
		producer.shutdown();
	}

}
