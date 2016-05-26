package bradypod.framework.vm.anq;

/**
 * 线程中断的理解
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_24 {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().interrupt();
		// 错误使用Thread.interrupted();
		if (Thread.currentThread().isInterrupted()) {
			System.out.println("interrupted");
		}
	}

}
