package bradypod.framework.vm.anq;

/**
 * String����
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_15 {

	public static void main(String[] args) {
		// �������ʲô���� ?
		byte bytes[] = new byte[256];
		for (int i = 0; i < 256; i++)
			bytes[i] = (byte) i;
		String str = new String(bytes);
		for (int i = 0, n = str.length(); i < n; i++)
			System.out.print((int) str.charAt(i) + " ");

		// String(byte[] bytes) ����ʹ�õ���Ĭ���ַ�������, ����ڲ�ͬ��ƽ̨�ϴ�ӡ������ܲ�һ��
		System.out.println("\n" + java.nio.charset.Charset.defaultCharset());
	}

}
