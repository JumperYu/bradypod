package bradypod.framework.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * socket实现redis的文本协议
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午11:26:44
 */
public class RedisSocketTest {

	public static void main(String[] args) {

		try (Socket socket = new Socket("localhost", 6379);) {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			byte[] cmd = getCmd("foo");
			System.out.println("cmd: \n" + new String(cmd));
			
			out.write(cmd);
			out.flush();

			// 读数据
			byte[] buf = new byte[8046];
			in.read(buf);

			System.out.println("read: " + new String(buf, "UTF-8"));

			in.close();
			out.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get命令
	 * 
	 * @param key
	 *            键
	 * @return 文本协议
	 */
	private static byte[] getCmd(String key) {
		int count = 0;
		byte[] buf = new byte[1024];
		buf[count++] = ASTERISK_BYTE; // *
		buf[count++] = '2'; // 消息个数
		buf[count++] = '\r';
		buf[count++] = '\n';
		buf[count++] = DOLLAR_BYTE; // $
		buf[count++] = '3'; // GET命令字节数
		buf[count++] = '\r';
		buf[count++] = '\n';
		for (int i = 0, len = CMD_GET.length; i < len; i++) {
			buf[count++] = CMD_GET[i]; // 命令内容
		}
		buf[count++] = '\r';
		buf[count++] = '\n';
		buf[count++] = DOLLAR_BYTE; // *
		buf[count++] = '3'; // 命令字节数
		buf[count++] = '\r';
		buf[count++] = '\n';
		byte[] keyBytes = key.getBytes();
		for (int i = 0, len = keyBytes.length; i < len; i++) {
			buf[count++] = keyBytes[i]; // 命令内容
		}
		buf[count++] = '\r';
		buf[count++] = '\n';
		return Arrays.copyOf(buf, count);
	}

	public static final byte DOLLAR_BYTE = '$';
	public static final byte ASTERISK_BYTE = '*';
	public static final byte[] CMD_GET = "GET".getBytes();

}