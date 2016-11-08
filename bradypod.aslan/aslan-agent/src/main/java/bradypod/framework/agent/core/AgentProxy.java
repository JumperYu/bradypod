package bradypod.framework.agent.core;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.jar.JarFile;

/**
 * Aslan-Agent, 启动接收命令的服务
 * 
 * @author xiangmin.zxm
 * @date 2016-11-10
 *
 */
public class AgentProxy {

	/**
	 * 在启动之前打入
	 * 
	 * @param agentArgs
	 * @param inst
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		main(agentArgs, inst);
	}

	/**
	 * 在启动之后打入
	 * 
	 * @param agentArgs
	 * @param inst
	 */
	public static void agentmain(String agentArgs, Instrumentation inst) {
		main(agentArgs, inst);
	}

	public static void main(String agentArgs, Instrumentation inst) {
		try {
			
			String[] args = agentArgs.split(";");
			String jarLibPath = args[0];
			
			// aslan-agent加入到目标进程的classloader里面
			inst.appendToBootstrapClassLoaderSearch(
					new JarFile(AgentProxy.class.getProtectionDomain().getCodeSource().getLocation().getFile()));

			ClassLoader agentLoader = loadOrDefineClassLoader(jarLibPath);

			// Configure类定义
			final Class<?> classOfConfigure = agentLoader.loadClass("bradypod.framework.agent.core.Configure");

			// GaServer类定义
			final Class<?> classOfGaServer = agentLoader.loadClass("bradypod.framework.agent.core.GaServer");

			// 反序列化成Configure类实例
			final Object objectOfConfigure = classOfConfigure.getMethod("toConfigure", String.class).invoke(null,
					agentArgs);

			// JavaPid
			final int javaPid = (Integer) classOfConfigure.getMethod("getJavaPid").invoke(objectOfConfigure);

			// 获取GaServer单例
			final Object objectOfGaServer = classOfGaServer.getMethod("getInstance", int.class, Instrumentation.class)
					.invoke(null, javaPid, inst);

			// gaServer.isBind()
			final boolean isBind = (Boolean) classOfGaServer.getMethod("isBind").invoke(objectOfGaServer);

			if (!isBind) {
				try {
					classOfGaServer.getMethod("bind", classOfConfigure).invoke(objectOfGaServer, objectOfConfigure);
				} catch (Throwable t) {
					classOfGaServer.getMethod("destroy").invoke(objectOfGaServer);
					throw t;
				}

			}
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}

	// 全局持有classloader用于隔离greys实现
	private static volatile ClassLoader aslanClassLoader;

	private static ClassLoader loadOrDefineClassLoader(String jarLibPath) throws Throwable {

		final ClassLoader classLoader;

		// 如果已经被启动则返回之前启动的classloader
		if (null != aslanClassLoader) {
			classLoader = aslanClassLoader;
		}

		// 如果未启动则重新加载
		else {
			classLoader = new AgentClassLoader(jarLibPath);
		}

		return aslanClassLoader = classLoader;
	}

}

class AgentClassLoader extends URLClassLoader {

	public AgentClassLoader(String dir) throws IOException {
		super(new URL[] {});
		loadJarFileFromDirctory(dir);
	}

	private void loadJarFileFromDirctory(String dir) throws IOException {
		Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (attrs.isDirectory()) {
					return FileVisitResult.CONTINUE;
				}
				addURL(file.toUri().toURL());
				return FileVisitResult.CONTINUE;
			}
		});
	}

	@Override
	protected void addURL(URL url) {
		super.addURL(url);
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		final Class<?> loadedClass = findLoadedClass(name);
		if (loadedClass != null) {
			return loadedClass;
		}

		try {
			Class<?> aClass = findClass(name);
			if (resolve) {
				resolveClass(aClass);
			}
			return aClass;
		} catch (Exception e) {
			return super.loadClass(name, resolve);
		}
	}
}