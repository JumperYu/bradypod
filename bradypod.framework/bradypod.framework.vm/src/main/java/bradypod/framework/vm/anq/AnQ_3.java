package bradypod.framework.vm.anq;

/**
 * 长整型计算出错
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_3 {
	
	public static void main(String[] args) {
		
		// 微秒 / 毫秒 = 1000
		final long MICROS_PER_DAY = 24 * 60 * 60 * 1000 * 1000; 
		final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000;
		
		System.out.println(MICROS_PER_DAY / MILLIS_PER_DAY);
		
		System.out.println("微秒(出错): " + MICROS_PER_DAY);
		System.out.println("毫秒" + MILLIS_PER_DAY);
		
		// 出错原因是因为int越界了
		
		final long MICROS_PER_DAY_LONG = 24L * 60 * 60 * 1000 * 1000; 
		final long MILLIS_PER_DAY_LONG = 24L * 60 * 60 * 1000;
		
		System.out.println(MICROS_PER_DAY_LONG / MILLIS_PER_DAY_LONG);
	}
	
}
