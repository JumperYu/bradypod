package bradypod.framework.client;

/**
 * 
 * 这些包在tools.jar上, 所以使用反射来实现, import的话需要强依赖不然编译不过
 * 
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class JvmAttach {

	public void bind(String pid, String ip, String agent) throws Exception {
		// AttachJVM进程上
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		Class<?> vmClass = classLoader
				.loadClass("com.sun.tools.attach.VirtualMachine");

		Object virtualmachine = null;
		try {
			virtualmachine = vmClass.getMethod("attach", String.class).invoke(
					null, pid);
			vmClass.getMethod("loadAgent", String.class).invoke(virtualmachine,
					agent);
		} finally {
			// Detach
			if (virtualmachine != null)
				vmClass.getMethod("detach", (Class<?>[]) null).invoke(
						virtualmachine, (Object[]) null);
		}

	}
}
