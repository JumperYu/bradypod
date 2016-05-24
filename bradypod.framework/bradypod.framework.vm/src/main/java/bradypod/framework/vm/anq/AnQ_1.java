package bradypod.framework.vm.anq;

/**
 * 解惑——1，奇数判断
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_1 {

	/**
	 * 判断奇数,当出现负整数和0的时候, 程序是错的
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
		System.out.println("正整数判断:" + isOddInCorrect(11));
		System.out.println("负整数判断:" + isOddInCorrect(-11));
		System.out.println("零判断:" + isOddInCorrect(-11));
		
		System.out.println("正整数判断:" + isOddCorrect(11));
		System.out.println("负整数判断:" + isOddCorrect(-11));
		System.out.println("零判断:" + isOddCorrect(-11));
	}
}
