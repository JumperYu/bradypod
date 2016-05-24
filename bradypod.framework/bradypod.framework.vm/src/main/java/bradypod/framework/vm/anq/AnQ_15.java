package bradypod.framework.vm.anq;

/**
 * String构造
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_15 {

	public static void main(String[] args) {
		// 这里存在什么问题 ?
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++)
			bytes[i] = (byte) i;
		String str = new String(bytes);
		for (int i = 0, n = str.length(); i < n; i++)
			System.out.print((int) str.charAt(i) + " ");

		// String(byte[] bytes) 构造使用的是默认字符集编码, 如果在不同的平台上打印结果可能不一致
		System.out.println("\n" + java.nio.charset.Charset.defaultCharset());
	}

}
