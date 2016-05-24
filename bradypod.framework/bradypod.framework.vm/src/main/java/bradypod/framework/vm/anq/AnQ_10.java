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
		// System.out.println(letters + " easy as " + numbers); // 类名 + @ + 对象的十六进制的值
		System.out.println(letters + " easy as " + String.valueOf(numbers));
		System.out.println(numbers);
		
		// 下面是错误
		Object chars = new char[]{'1', '2', '3'};
		System.out.println(chars);
	}

}
