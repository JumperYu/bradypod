package bradypod.framework.vm.anq;

/**
 * 
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_10 {

	public static void main(String[] args) {
		
		String letters = "ABC";
		char[] numbers = {'1', '2', '3'};
		// System.out.println(letters + " easy as " + numbers); // ���� + @ + �����ʮ�����Ƶ�ֵ
		System.out.println(letters + " easy as " + String.valueOf(numbers));
		System.out.println(numbers);
		
		// �����Ǵ���
		Object chars = new char[]{'1', '2', '3'};
		System.out.println(chars);
	}

}
