package bradypod.framework.agent;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * 
 * 这些包在tools.jar上, 所以使用反射来实现, 不然编译不过 import
 * 
 * com.sun.tools.attach.AgentInitializationException; import
 * com.sun.tools.attach.AgentLoadException; import
 * com.sun.tools.attach.AttachNotSupportedException; import
 * com.sun.tools.attach.VirtualMachine;
 * 
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class JvmAttach {

	public static void main(String[] args) throws Exception {

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
