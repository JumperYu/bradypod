
package bradypod.framework.agent.core;

import java.lang.instrument.Instrumentation;

public abstract class EnhanceAction implements Action {
	
	public abstract Enhancer enhance(Session session, Instrumentation inst);

}
