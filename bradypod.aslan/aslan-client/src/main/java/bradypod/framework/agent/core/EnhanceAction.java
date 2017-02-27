
package bradypod.framework.agent.core;

import java.lang.instrument.Instrumentation;

import bradypod.framework.agent.core.asm.Printer;

public abstract class EnhanceAction implements Action {
	
	public abstract Enhancer enhance(Session session, Instrumentation inst, Printer printer);

}
