package bradypod.framework.agent.core.cmd;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;

import com.bradypod.reflect.jdk.Programmer;

import bradypod.framework.agent.core.Action;
import bradypod.framework.agent.core.Cmd;
import bradypod.framework.agent.core.EnhanceAction;
import bradypod.framework.agent.core.Enhancer;
import bradypod.framework.agent.core.IndexArg;
import bradypod.framework.agent.core.NameArg;
import bradypod.framework.agent.core.Session;
import bradypod.framework.agent.core.asm.Printer;
import bradypod.framework.agent.util.AslanReflectUtil;

@Cmd(name = "watch")
public class WatchCommand extends Command {

	@NameArg(name = "b")
	private boolean isBefore;

	@NameArg(name = "a")
	private boolean isAfter;

	@NameArg(name = "e")
	private boolean isException;

	@IndexArg(index = 1, isRequired = true)
	private String classPattern;

	@IndexArg(index = 2, isRequired = true)
	private String methodPattern;

	public WatchCommand() {
	}

	/**
	 * 
	 * watch [class] [-bae]
	 * 
	 * -b -a -e -x
	 * 
	 * 
	 * @param cmd
	 */
	public WatchCommand(String cmd) {

	}

	@Override
	public Action action() {
		return new EnhanceAction() {
			@Override
			public Enhancer enhance(Session session, Instrumentation inst, Printer printer) {
				Enhancer enhancer = new Enhancer(classPattern, methodPattern);
				enhancer.addPrinter(printer);
				inst.addTransformer(enhancer, true);
				try {
					inst.redefineClasses(
							new ClassDefinition(Programmer.class, AslanReflectUtil.getByteCode(Programmer.class)));
				} catch (Exception e) {
					inst.removeTransformer(enhancer);
				}
				return enhancer;
			}
		};
	}

	@Override
	public String toString() {
		return "WatchCommand [isBefore=" + isBefore + ", isAfter=" + isAfter + ", isException=" + isException
				+ ", classPattern=" + classPattern + ", methodPattern=" + methodPattern + "]";
	}

}
