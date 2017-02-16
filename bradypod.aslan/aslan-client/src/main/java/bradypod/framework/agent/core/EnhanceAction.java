
package bradypod.framework.agent.core;

public abstract class EnhanceAction implements Action {

	public abstract void beforeMethod();

	public abstract void afterReturn();

	public abstract void onException();

}
