package bradypod.framework.vm.anq;

/**
 * 恶心的1和l
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_4 {
	
	public static void main(String[] args) {
		
		// 总结：应该避免使用l来定义长整型，使用大写L代替
		
		System.out.println(12345 + 5432l);
		
		System.out.println(12345 + 54321);
		
	}
	
}
