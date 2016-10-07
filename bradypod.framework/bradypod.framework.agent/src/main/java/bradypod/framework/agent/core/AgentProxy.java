package bradypod.framework.agent.core;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.jar.JarFile;

public class AgentProxy {

	/**
	 * 在启动之前打入
	 * 
	 * @param agentOps
	 * @param inst
	 * @throws ClassNotFoundException
	 * @throws UnmodifiableClassException
	 */
	public static void premain(String agentOps, Instrumentation inst)
			throws Exception {
		main(agentOps, inst);

		LogUtil.log("Agent Pre Done");
	}

	/**
	 * 在启动之后打入
	 * 
	 * @param agentArgs
	 * @param inst
	 */
	public static void agentmain(String agentArgs, Instrumentation inst)
			throws Exception {

		main(agentArgs, inst);

		LogUtil.log("Agent Main Done");
	}

	public static void main(String agentArgs, Instrumentation inst)
			throws Exception {
		// jarfile要加入已启动的classloadder里面
		inst.appendToBootstrapClassLoaderSearch(new JarFile(AgentProxy.class
				.getProtectionDomain().getCodeSource().getLocation().getFile()));

		ClassLoader agentLoader = AgentProxy.class.getClassLoader();

		// Configure类定义
		final Class<?> classOfConfigure = agentLoader
				.loadClass("bradypod.framework.agent.core.Configure");

		// GaServer类定义
		final Class<?> classOfGaServer = agentLoader
				.loadClass("bradypod.framework.agent.core.GaServer");

		// 反序列化成Configure类实例
		final Object objectOfConfigure = classOfConfigure.getMethod(
				"toConfigure", String.class).invoke(null, agentArgs);

		// JavaPid
		final int javaPid = (Integer) classOfConfigure.getMethod("getJavaPid")
				.invoke(objectOfConfigure);

		// 获取GaServer单例
		final Object objectOfGaServer = classOfGaServer.getMethod(
				"getInstance", int.class, Instrumentation.class).invoke(null,
				javaPid, inst);

		// gaServer.isBind()
		final boolean isBind = (Boolean) classOfGaServer.getMethod("isBind")
				.invoke(objectOfGaServer);

		if (!isBind) {
			try {
				classOfGaServer.getMethod("bind", classOfConfigure).invoke(
						objectOfGaServer, objectOfConfigure);
			} catch (Throwable t) {
				classOfGaServer.getMethod("destroy").invoke(objectOfGaServer);
				throw t;
			}

		}

	}

}
