package bradypod.framework.vm.anq;

import java.math.BigDecimal;

/**
 * ��󡪡�2����С����
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_2 {
	
	public static void main(String[] args) {
		
		// 1, �����ۼ���ʱ�򣬴���ʾ��
		System.out.println("����ʾ��: " + (2.0 - 1.1));
		
		// 2�� printf
		System.out.printf("%.2f%n", 2.00 - 1.10);
		
		// 3��ת��Ϊ��Ϊ��λ,int����
		System.out.println(200 - 110 + "��");
		
		// 4��BigDecimal���ַ��������������ø��㹹�������Ը���ľ���¼��
		System.out.println(new BigDecimal("2.00").subtract(new BigDecimal("1.10")));
		System.out.println("����ʾ��: " + new BigDecimal(2.00).subtract(new BigDecimal(1.10)));
	}
	
}
