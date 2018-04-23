import com.seewo.store.CommitLog;
import com.seewo.store.MessageInner;
import com.seewo.store.MessageStoreConfig;
import com.seewo.store.PutMessageResult;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zxm on 2018/2/10.
 */
public class CommitLogTest {

    static final int FILE_SIZE = 1024;
    static final String STORE_PATH = "/Users/zxm/work/seewo/bradypod/seewo-im/target/store";

    CommitLog commitLog;

    @Before
    public void init() {

        MessageStoreConfig messageStoreConfig = new MessageStoreConfig();
        messageStoreConfig.setMapedFileSizeCommitLog(FILE_SIZE);
        messageStoreConfig.setStorePathCommitLog(STORE_PATH);

        commitLog = new CommitLog(messageStoreConfig);
    }

    @Test
    public void testPutMessage() {
        String msg = "0123456789abcdef"; // 16

        MessageInner messageInner = new MessageInner();
        messageInner.setBody(msg.getBytes());

        int wroteNum = 1024;

        for (int i = 0; i < wroteNum; i++) {
            PutMessageResult putMessageResult = commitLog.putMessage(messageInner);

            assertNotNull(putMessageResult);

            assertEquals(new String(commitLog.getMessage(putMessageResult.getOffset(), putMessageResult.getSize()).getBody()), msg);
        }
    }
}

