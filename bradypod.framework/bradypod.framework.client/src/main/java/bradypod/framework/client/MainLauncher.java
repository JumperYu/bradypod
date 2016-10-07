package bradypod.framework.client;

import joptsimple.OptionParser;
import joptsimple.OptionSet;

/**
 * 
 * agent入口
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月24日
 */
public class MainLauncher {

	/**
	 * 
	 * - pid - pid ip - pid ip:port
	 * 
	 */
	public static void main(String[] args) throws Exception {

		final OptionParser parser = new OptionParser();
		parser.accepts("pid").withOptionalArg().ofType(int.class);
		parser.accepts("target").withOptionalArg().ofType(String.class);
		parser.accepts("multi").withOptionalArg().ofType(int.class);
		parser.accepts("core").withOptionalArg().ofType(String.class);
		parser.accepts("agent").withOptionalArg().ofType(String.class);

		OptionSet os = parser.parse(args);

		if (os.has("target")) {
			target = (String) os.valueOf("target");
		}
		JvmAttach jvmAttach = new JvmAttach();
		jvmAttach.bind(String.valueOf(os.valueOf("pid")), "127.0.0.1",
				(String) os.valueOf("agent"));
	}

	static String target = "127.0.0.1";
}
