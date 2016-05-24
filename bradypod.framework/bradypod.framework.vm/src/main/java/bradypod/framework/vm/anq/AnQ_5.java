package bradypod.framework.vm.anq;

/**
 * 十六进制的谜题
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_5 {
	
	public static void main(String[] args) {
		
		// 总结：没看懂
		
		System.out.println(Long.toHexString(0x100000000L + 0xcafebabe)); // 错误结果
		
		System.out.println(Long.toHexString(0x100000000L + 0xcafebabeL));
		
	}
	
}
