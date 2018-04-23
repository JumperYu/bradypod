import com.seewo.store.MappedFile;
import com.seewo.store.MappedFileQueue;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by zxm on 2018/2/7.
 */
public class MappedFileQueueTest {

    private static final int FILE_SIZE = 1024;

    private static final String STORE_PATH = "target/store/";

    @Test
    public void testGetLastMappedFile() {
        final String fixedMsg = "0123456789abcdef"; // 16 bytes

        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        // 64次生成一个新文件
        for (int i = 0; i < 1024; i++) {
            MappedFile mappedFile = mappedFileQueue.getLastMappedFile(0);
            assertNotNull(mappedFile);
            assertTrue(mappedFile.appendMessage(fixedMsg.getBytes()));
        }

        MappedFile mappedFile = mappedFileQueue.findMappedFileByOffset(0);
        assertNotNull(mappedFile);
        assertEquals(mappedFile.getFileFromOffset(), 0);

        mappedFile = mappedFileQueue.findMappedFileByOffset(FILE_SIZE - 100);
        assertNotNull(mappedFile);
        assertEquals(mappedFile.getFileFromOffset(), 0);

        mappedFile = mappedFileQueue.findMappedFileByOffset(FILE_SIZE + 1);
        assertNotNull(mappedFile);
        assertEquals(mappedFile.getFileFromOffset(), FILE_SIZE);

        mappedFile = mappedFileQueue.findMappedFileByOffset(FILE_SIZE * 63);
        assertNull(mappedFile);

        // 清空所有存储
        mappedFileQueue.destroy();
    }

    @Test
    public void testFindMappedFileByOffset_StartOffsetIsNonZero() {
        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        //Start from a non-zero offset
        MappedFile mappedFile = mappedFileQueue.getLastMappedFile(FILE_SIZE);
        assertNotNull(mappedFile);

        assertEquals(mappedFileQueue.findMappedFileByOffset(FILE_SIZE + 1), mappedFile);

        assertNull(mappedFileQueue.findMappedFileByOffset(0));
        assertNull(mappedFileQueue.findMappedFileByOffset(FILE_SIZE - 1, false));
        assertEquals(mappedFileQueue.findMappedFileByOffset(FILE_SIZE - 1, true), mappedFile);

        assertNull(mappedFileQueue.findMappedFileByOffset(0, false));
        assertEquals(mappedFileQueue.findMappedFileByOffset(0, true), mappedFile);

        mappedFileQueue.destroy();
    }

    @Test
    public void testAppendMessage() {
        final String fixedMsg = "0123456789abcdef";

        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        for (int i = 0; i < 1024; i++) {
            MappedFile mappedFile = mappedFileQueue.getLastMappedFile(0);
            assertNotNull(mappedFile);
            assertTrue(mappedFile.appendMessage(fixedMsg.getBytes()));
        }

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024);

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024 * 2);

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024 * 3);

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024 * 4);

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024 * 5);

        assertFalse(mappedFileQueue.flush(0));
        assertEquals(mappedFileQueue.getFlushedWhere(), 1024 * 6);

        mappedFileQueue.destroy();
    }

    @Test
    public void testGetMappedMemorySize() {
        final String fixedMsg = "0123456789abcdef";

        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        for (int i = 0; i < 1024; i++) {
            MappedFile mappedFile = mappedFileQueue.getLastMappedFile(0);
            assertNotNull(mappedFile);
            assertTrue(mappedFile.appendMessage(fixedMsg.getBytes()));
        }

        assertEquals(mappedFileQueue.getMappedMemorySize(), fixedMsg.length() * 1024);

        mappedFileQueue.destroy();
    }

    @Test
    public void testDeleteExpiredFileByOffset() {
        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        for (int i = 0; i < 2048; i++) {
            MappedFile mappedFile = mappedFileQueue.getLastMappedFile(0);
            assertNotNull(mappedFile);
            ByteBuffer byteBuffer = ByteBuffer.allocate(20);
            byteBuffer.putLong(i);
            byte[] padding = new byte[12];
            Arrays.fill(padding, (byte) '0');
            byteBuffer.put(padding);
            byteBuffer.flip();

            assertTrue(mappedFile.appendMessage(byteBuffer.array()));
        }

        MappedFile first = mappedFileQueue.getFirstMappedFile();
        first.hold();

        assertEquals(mappedFileQueue.deleteExpiredFileByOffset(20480, 20), 0);
//        first.release();

        assertTrue(mappedFileQueue.deleteExpiredFileByOffset(20480, 20) > 0);
        first = mappedFileQueue.getFirstMappedFile();
        assertTrue(first.getFileFromOffset() > 0);

        mappedFileQueue.destroy();
    }

    @Test
    public void testDeleteExpiredFileByTime() throws Exception {
        MappedFileQueue mappedFileQueue =
                new MappedFileQueue(STORE_PATH, FILE_SIZE);

        for (int i = 0; i < 100; i++) {
            MappedFile mappedFile = mappedFileQueue.getLastMappedFile(0);
            assertNotNull(mappedFile);
            byte[] bytes = new byte[512];
            assertTrue(mappedFile.appendMessage(bytes));
        }

        assertEquals(mappedFileQueue.getMappedFiles().size(), 50);
        long expiredTime = 100 * 1000;
        for (int i = 0; i < mappedFileQueue.getMappedFiles().size(); i++) {
            MappedFile mappedFile = mappedFileQueue.getMappedFiles().get(i);
            if (i < 5) {
                mappedFile.getFile().setLastModified(System.currentTimeMillis() - expiredTime * 2);
            }
            if (i > 20) {
                mappedFile.getFile().setLastModified(System.currentTimeMillis() - expiredTime * 2);
            }
        }
        mappedFileQueue.deleteExpiredFileByTime(expiredTime, 0, 0, false);
        assertEquals(mappedFileQueue.getMappedFiles().size(), 45);
    }
}
