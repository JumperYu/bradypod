package bradypod.framework.vm.anq;

/**
 * �߳��жϵ����
 * 
 * @author xiangmin.zxm
 *
 */
public class AnQ_24 {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().interrupt();
		// ����ʹ��Thread.interrupted();
		if (Thread.currentThread().isInterrupted()) {
			System.out.println("interrupted");
		}
	}

}
