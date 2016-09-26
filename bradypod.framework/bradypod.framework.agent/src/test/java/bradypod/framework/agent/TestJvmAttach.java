package bradypod.framework.agent;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @see JvmAttach main
 * @see MyAgent
 * 
 *      premain-agent 和 main-agent 和 jvm attach 的用法
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class TestJvmAttach {

	public static void main(String[] args) throws InterruptedException {
		final String jar = "E://work//new-life//bradypod//bradypod.framework//bradypod.framework.agent//target//bradypod.framework.agent.jar";
		Thread jvmAttachThread = new Thread() {
			@Override
			public void run() {
				try {
					VirtualMachine vm = null;
					List<VirtualMachineDescriptor> listBefore = VirtualMachine
							.list();
					List<VirtualMachineDescriptor> listAfter = null;
					while (true) {
						listAfter = VirtualMachine.list();
						// System.out.println("vm:" + listAfter);
						for (VirtualMachineDescriptor vmd : listAfter) {
							if (!listBefore.contains(vmd)) {
								// 如果 VM 有增加，我们就认为是被监控的 VM 启动了
								// 这时，我们开始监控这个 VM
								vm = VirtualMachine.attach(vmd);
								System.out.println("attach vm " + vmd.id());
								vm.loadAgent(jar);
								vm.detach();
								listBefore.add(vmd);
								System.out.println("load agent");
								break;
							}
						}
						Thread.sleep(3000);
					}
				} catch (Exception e) {

				}
			};
		};
		jvmAttachThread.start();
	}

	public void test() throws Exception, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		String pid = "532888";

		// AttachJVM进程上
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		Class<?> vmClass = classLoader
				.loadClass("com.sun.tools.attach.VirtualMachine");

		// VirtualMachine virtualmachine = VirtualMachine.attach("44096");

		Object virtualmachine = vmClass.getMethod("attach", String.class)
				.invoke(null, pid);

		// 让JVM加载jmx Agent
		// String javaHome =
		// virtualmachine.getSystemProperties().getProperty("java.home");

		Properties systemProperties = (Properties) vmClass.getMethod(
				"getSystemProperties", (Class<?>) null).invoke(virtualmachine,
				(Class<?>) null);
		String javaHome = systemProperties.getProperty("java.home");

		String jmxAgent = javaHome + File.separator + "lib" + File.separator
				+ "management-agent.jar";

		// virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");
		vmClass.getMethod("loadAgent", String.class).invoke(virtualmachine,
				jmxAgent);

		// 获得连接地址
		// Properties properties = virtualmachine.getAgentProperties();
		Properties properties = (Properties) vmClass.getMethod(
				"getAgentProperties", (Class<?>) null).invoke(virtualmachine,
				(Class<?>) null);
		String address = (String) properties
				.get("com.sun.management.jmxremote.localConnectorAddress");

		// Detach
		// virtualmachine.detach();
		vmClass.getMethod("detach", (Class<?>) null).invoke(virtualmachine,
				(Class<?>) null);

		JMXServiceURL url = new JMXServiceURL(address);
		JMXConnector connector = JMXConnectorFactory.connect(url);
		RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(
				connector.getMBeanServerConnection(), "java.lang:type=Runtime",
				RuntimeMXBean.class);
		System.out.println(rmxb.getLibraryPath());
	}
}
