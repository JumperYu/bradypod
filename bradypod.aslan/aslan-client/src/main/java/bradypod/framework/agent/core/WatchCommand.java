package bradypod.framework.agent.core;

public class WatchCommand extends Command {

	@NameArg(name = "b")
	private boolean isBefore;

	@NameArg(name = "a")
	private boolean isAfter;

	@NameArg(name = "e")
	private boolean isException;

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
	public void execute(Action action) {

	}

}
