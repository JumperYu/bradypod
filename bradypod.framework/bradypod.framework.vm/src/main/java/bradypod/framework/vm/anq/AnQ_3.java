package bradypod.framework.vm.anq;

/**
 * �����ͼ������
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_3 {
	
	public static void main(String[] args) {
		
		// ΢�� / ���� = 1000
		final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000; 
		final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
		
		System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
		
		System.out.println("΢��(����): " + MICROS_PER_DAY);
		System.out.println("����" + MILLIS_PER_DAY);
		
		// ����ԭ������ΪintԽ����
		
		final long MICROS_PER_DAY_LONG = 24L * 60 * 60 * 1000 * 1000; 
		final long MILLIS_PER_DAY_LONG = 24L * 60 * 60 * 1000;
		
		System.out.println(MICROS_PER_DAY_LONG / MILLIS_PER_DAY_LONG);
	}
	
}
