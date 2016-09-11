package bradypod.framework.agent;

/**
 * 
 *  Transformer.java改变编译后打包到agent里面, premain的时候将他替换掉
 * 
 * -javaagent:E:\work\new-life\bradypod\bradypod.framework\bradypod.framework.agent\target\bradypod.framework.agent.jar
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年9月11日
 */
public class TestAgent {

	public static void main(String[] args) {
//		System.out.println(new TransformClass().getNumber());
		Thread testThread = new Thread() {
			public void run() {
				while (true) {
					try {
						int number = new TransformClass().getNumber();
						System.out.println(number);
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		};
		testThread.start();
	}
	
}
