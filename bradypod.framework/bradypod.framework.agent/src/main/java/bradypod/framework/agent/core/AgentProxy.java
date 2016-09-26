package bradypod.framework.agent.core;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentProxy {

	public static void premain(String agentOps, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException {
		System.out.println("Agent PreMain Done");
	}

	public static void agentmain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException,
			InterruptedException {
		System.out.println("Agent Main Done");
	}

}
