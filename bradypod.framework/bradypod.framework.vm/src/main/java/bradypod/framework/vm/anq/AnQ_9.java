package bradypod.framework.vm.anq;

/**
 * ��ֵ
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_9 {

	public static void main(String[] args) {

		short x = 0;
		int i = 123456;
		
		System.out.println(x += i); // ������һ�����ص�����ת��
		
		x = 0;
		i = 123456;
		
		x = (short) (x + i); // ��Ҫ��ʾ������
		
		System.out.println();
		
		Object a = "Buy ";
		String b = "Something";
		
		a = a + b; // ���ǺϷ��ļ���
		
		System.out.println(a);
		
		System.out.println("A" + "B");
		System.out.println('A' + 'B');
		System.out.println("" + 'A' + 'B');
		System.out.println(new StringBuilder().append('A').append('B').toString());
	}

}
