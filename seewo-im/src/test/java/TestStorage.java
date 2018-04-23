import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by zxm on 2018/1/30.
 */
public class TestStorage {

    private final String storeMessage = "曾经有这么样个机会摆在我面前，我没有去珍惜。";

    @Test
    public void testStorage() throws IOException {

        String path = "/Users/zxm/work/seewo-im-store/storage";
        FileChannel fileChannel = new RandomAccessFile(path, "rw").getChannel();

        int storeSize = storeMessage.getBytes("UTF-8").length;
        byte[] body = storeMessage.getBytes("UTF-8");

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // body size
        buffer.putInt(storeSize);
        // body
        buffer.put(body);

        buffer.flip();
        fileChannel.write(buffer, 0);

        ByteBuffer newBuf = ByteBuffer.allocate(1024);

        int count = fileChannel.read(newBuf);

        newBuf.flip();

        System.out.println(newBuf.getInt());

        byte[] bytes = new byte[newBuf.limit() - 4];

        newBuf.get(bytes);

        System.out.println(new String(bytes, "UTF-8"));

        fileChannel.close();

    }

    @Test
    public void test() {
        System.out.println(1024 / 1024);
    }

}
