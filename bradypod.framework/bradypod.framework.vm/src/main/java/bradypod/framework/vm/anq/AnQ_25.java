package bradypod.framework.vm.anq;

/**
 * �߼���
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_25 {

	public static void main(String[] args) throws InterruptedException {
		// int i = -(2147483648); ��õ�һ���������
		
		long x = Long.MAX_VALUE;
		double y = (double) Long.MAX_VALUE;
		long z = Long.MAX_VALUE - 1;
		System.out.println(x + " " + y + " " + z);
		System.out.print((x == y) + " "); // Imprecise!
		System.out.print((y == z) + " "); // Imprecise!
		System.out.println(x == z); // Precise!
		
	}

}
