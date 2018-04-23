import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by zxm on 2018/1/30.
 */
public class TestNIO {

    private final String storeMessage = "曾经有这么样个机会摆在我面前，我没有去珍惜。";

    private static Charset charset = Charset.forName("UTF-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    @Test
    public void testWriteFile() throws IOException {
        String path = "/Users/zxm/work/seewo-im-store/timeline";
        FileChannel fileChannel = new RandomAccessFile(path, "rw").getChannel();

        fileChannel.write(ByteBuffer.wrap(storeMessage.getBytes("UTF-8")), 0);

        fileChannel.close();
    }

    @Test
    public void testReadFile() throws IOException {
        String path = "/Users/zxm/work/seewo-im-store/timeline";
        FileChannel fileChannel = new RandomAccessFile(path, "rw").getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int len = fileChannel.read(buffer, 0);

        System.out.println(String.format("read: %d, buffer capacity: %d, position: %d,", len, buffer.capacity(), buffer.position()));

        buffer.flip();

        System.out.println(decoder.decode(buffer).toString());

    }

    @Test
    public void testParseLong() {
        System.out.println(Long.parseLong("00001234"));
    }
}
