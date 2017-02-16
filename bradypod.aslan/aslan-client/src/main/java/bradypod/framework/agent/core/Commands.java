package bradypod.framework.agent.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Commands {

	public static Commands getInstanst() {
		return instanst;
	}

	public Command newCommand(String cmd) {

		String[] args = cmd.split(" ");

		if (!commands.containsKey(args[0])) {
			throw new RuntimeException("command not found");
		}

		Command watchCommand = new WatchCommand(cmd);
		return watchCommand;
	}

	private static final Commands instanst = new Commands();

	private static Map<String, Object> commands = new HashMap<>();

	private Commands() {
		init();
	}

	private void init() {
		Set<Class<?>> classes = GaClassUtils.scanPackage(Commands.class.getClassLoader(),
				"bradypod.framework.agent.core");

		for (Class<?> clazz : classes) {
			Cmd cmd = clazz.getAnnotation(Cmd.class);
			try {
				Object object = clazz.newInstance();
				commands.put(cmd.name(), object);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
	}
}
