package bradypod.framework.vm.anq;

/**
 * 赋值
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_9 {

	public static void main(String[] args) {

		short x = 0;
		int i = 123456;
		
		System.out.println(x += i); // 包含了一个隐藏的类型转换
		
		x = 0;
		i = 123456;
		
		x = (short) (x + i); // 需要显示的声明
		
		System.out.println();
		
		Object a = "Buy ";
		String b = "Something";
		
		a = a + b; // 这是合法的计算
		
		System.out.println(a);
		
		System.out.println("A" + "B");
		System.out.println('A' + 'B');
		System.out.println("" + 'A' + 'B');
		System.out.println(new StringBuilder().append('A').append('B').toString());
	}

}
