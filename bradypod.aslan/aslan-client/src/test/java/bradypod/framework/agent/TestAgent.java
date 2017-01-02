package bradypod.framework.agent;

import java.lang.reflect.InvocationTargetException;

import com.bradypod.reflect.jdk.Programmer;

/**
 * 
 * Transformer.java改变编译后打包到agent里面, premain的时候将他替换掉
 * 
 * -javaagent:E:\work\new-life\bradypod\bradypod.framework\bradypod.framework.
 * agent\target\bradypod.framework.agent.jar
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class TestAgent {

	public static void main(String[] args) throws ClassNotFoundException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		// 获取运行的pid
		ClassLoader classLoader = TestAgent.class.getClassLoader();
		Class<?> managementFactoryClz = classLoader
				.loadClass("java.lang.management.ManagementFactory");
		Object runtime = managementFactoryClz.getMethod("getRuntimeMXBean",
				(Class<?>[]) null).invoke((Object) null, (Object[]) null);
		final String runtimeName = (String) classLoader
				.loadClass("java.lang.management.RuntimeMXBean")
				.getMethod("getName", (Class<?>[]) null)
				.invoke(runtime, (Object[]) null);
		final int pid = Integer.parseInt(runtimeName.substring(0,
				runtimeName.indexOf("@")));
		// not stop
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Programmer programmer = new Programmer();
				System.out.println("current process：" + runtimeName);
				System.out.println("current pid：" + pid);
				while (true) {
					try {
						programmer.doCoding("hello moto." + Math.random() * 100);
						Thread.sleep(1000 * 30);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		});
		thread.start();
	}
}
