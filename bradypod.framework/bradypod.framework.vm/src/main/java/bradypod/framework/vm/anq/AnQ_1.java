package bradypod.framework.vm.anq;

/**
 * ��󡪡�1�������ж�
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_1 {

	/**
	 * �ж�����,�����ָ�������0��ʱ��, �����Ǵ��
	 * 
	 * @param i
	 * @return
	 */
	public static boolean isOddInCorrect(int i) {
		return i % 2 == 1;
	}
	
	public static boolean isOddCorrect(int i){
		return (i & 1) != 0;
	}
	
	public static void main(String[] args) {
		System.out.println("�������ж�:" + isOddInCorrect(11));
		System.out.println("�������ж�:" + isOddInCorrect(-11));
		System.out.println("���ж�:" + isOddInCorrect(-11));
		
		System.out.println("�������ж�:" + isOddCorrect(11));
		System.out.println("�������ж�:" + isOddCorrect(-11));
		System.out.println("���ж�:" + isOddCorrect(-11));
	}
}
