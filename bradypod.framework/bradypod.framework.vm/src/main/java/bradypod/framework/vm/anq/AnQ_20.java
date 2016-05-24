package bradypod.framework.vm.anq;

/**
 * ¾­µäµÄStack Over Flow
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_20 {
	
	AnQ_20 anq = new AnQ_20();
	
	public AnQ_20() throws Exception {
		throw new Exception("init error");
	}
	
	public static void main(String[] args) {
		try {
			new AnQ_20();
			System.out.println("Surprise!");
		} catch (Exception e) {
			System.out.println("I told you error");
		}
	}
	
}
