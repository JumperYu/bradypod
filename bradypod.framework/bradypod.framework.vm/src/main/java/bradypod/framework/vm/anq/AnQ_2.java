package bradypod.framework.vm.anq;

import java.math.BigDecimal;

/**
 * 解惑——2，找小数点
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_2 {
	
	public static void main(String[] args) {
		
		// 1, 做金额扣减的时候，错误示例
		System.out.println("错误示例: " + (2.0 - 1.1));
		
		// 2， printf
		System.out.printf("%.2f%n", 2.00 - 1.10);
		
		// 3，转换为分为单位,int类型
		System.out.println(200 - 110 + "分");
		
		// 4，BigDecimal的字符串构造器，不用浮点构造否则会以浮点的精度录入
		System.out.println(new BigDecimal("2.00").subtract(new BigDecimal("1.10")));
		System.out.println("错误示例: " + new BigDecimal(2.00).subtract(new BigDecimal(1.10)));
	}
	
}
