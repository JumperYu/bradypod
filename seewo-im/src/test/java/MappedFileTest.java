import com.seewo.store.MappedFile;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by zxm on 2018/2/6.
 */
public class MappedFileTest {

    private static final int FILE_SIZE = 1024;

    private static final String FILE_NAME = "target/store/0000";

    @Test
    public void testInitOK() throws IOException {
        MappedFile mappedFile = new MappedFile(FILE_NAME, FILE_SIZE);
        assertNotNull(mappedFile.getFileChannel());

        mappedFile.destroy();
    }

    @Test
    public void testAppendMessage() throws IOException {
        String msg = "sorry, it's my fault"; // 20 bytes

        MappedFile mappedFile = new MappedFile(FILE_NAME, FILE_SIZE);

        // append
        assertTrue(mappedFile.appendMessage(msg.getBytes()));

        byte[] buffer = new byte[msg.getBytes().length];

        mappedFile.selectMappedBuffer(0, msg.getBytes().length).getByteBuffer().get(buffer);
        // assert equals
        assertEquals(msg, new String(buffer));

        mappedFile.destroy();
    }

}
