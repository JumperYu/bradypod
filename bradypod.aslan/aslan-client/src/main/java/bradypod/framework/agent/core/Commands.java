package bradypod.framework.agent.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bradypod.framework.agent.core.cmd.Command;
import bradypod.framework.agent.util.AslanReflectUtil;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

public class Commands {

	public static Commands getInstanst() {
		return instanst;
	}

	/**
	 * watch [class] [method] [-ab]
	 * 
	 * @param cmd
	 * @return
	 */
	public Command newCommand(String cmd) {

		String[] args = cmd.split(" ");

		if (!commands.containsKey(args[0])) {
			throw new RuntimeException("command not found");
		}

		Class<?> clazz = commands.get(args[0]);

		Object obj = null;
		try {
			obj = clazz.newInstance();

		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		final OptionParser parser = new OptionParser();
		parser.accepts("b").withOptionalArg().ofType(String.class);
		parser.accepts("a").withOptionalArg().ofType(String.class);
		OptionSet os = parser.parse(args);

		List<String> nonOptionArgs = os.nonOptionArguments();

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(NameArg.class)) {
				NameArg nameArg = field.getAnnotation(NameArg.class);
				String name = nameArg.name();
				if (os.has(name)) {
					AslanReflectUtil.setValue(field, obj, true);
				}
			} else if (field.isAnnotationPresent(IndexArg.class)) {
				IndexArg indexArg = field.getAnnotation(IndexArg.class);
				int index = indexArg.index();
				if (nonOptionArgs.size() > index) {
					String value = nonOptionArgs.get(index);
					AslanReflectUtil.setValue(field, obj, value);
				}
			}
		}

		return (Command) obj;
	}

	private static Map<String, Class<?>> commands = new HashMap<>();

	private static final Commands instanst = new Commands();

	private volatile boolean isInitialized = false;

	private Commands() {
		if (!isInitialized) {
			init();
		}
	}

	private void init() {
		Set<Class<?>> classes = GaClassUtils.scanPackage(Commands.class.getClassLoader(),
				"bradypod.framework.agent.core.cmd");

		for (Class<?> clazz : classes) {
			if (!clazz.isAnnotationPresent(Cmd.class)) {
				continue;
			}
			Cmd cmd = clazz.getAnnotation(Cmd.class);
			commands.put(cmd.name(), clazz);
		}

		isInitialized = true;
	}

	public static void main(String[] args) {
		System.out.println(Commands.getInstanst().newCommand("watch com.bradypod doCoding -b -a"));
	}
}
