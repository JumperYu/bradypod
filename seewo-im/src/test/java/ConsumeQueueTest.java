import com.seewo.store.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zxm on 2018/2/19.
 */
public class ConsumeQueueTest {

    private static final String TOPIC = "TimeLine-A";

    private static final int QUEUE_ID = 0;

    private static final int COMMIT_LOG__SIZE = 1024 * 1024 * 10;

    private static final int CONSUME_LOG__SIZE = 1024 * 1024 * 10;

    static final String STORE_PATH = "/Users/zxm/work/seewo/bradypod/seewo-im/target/store";

    private CommitLog commitLog;

    private ConsumeQueue consumeQueue;

    @Before
    public void init() {

        MessageStoreConfig messageStoreConfig = new MessageStoreConfig();
        messageStoreConfig.setMapedFileSizeCommitLog(COMMIT_LOG__SIZE);
        messageStoreConfig.setStorePathCommitLog(STORE_PATH);

        commitLog = new CommitLog(messageStoreConfig);

        consumeQueue = new ConsumeQueue(TOPIC, QUEUE_ID, STORE_PATH, CONSUME_LOG__SIZE);
    }

    @Test
    public void testputMessagePositionInfo() {

        MessageInner messageInner = new MessageInner();
        messageInner.setBody("123".getBytes());

        PutMessageResult putMessageResult = commitLog.putMessage(messageInner);

        assertTrue(consumeQueue.putMessagePositionInfo(putMessageResult.getOffset(), putMessageResult.getSize(), "*".hashCode()));
    }

}
