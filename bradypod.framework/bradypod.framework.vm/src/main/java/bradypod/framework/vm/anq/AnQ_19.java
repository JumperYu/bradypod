package bradypod.framework.vm.anq;

/**
 * 数值越界不报错
 */
public class AnQ_19 {

	public static void main(String[] args) {

		int max = Integer.MAX_VALUE;
		
		int min = Integer.MIN_VALUE;
		
		System.out.println(max + 1);
		
		System.out.println(min);

	}

}
