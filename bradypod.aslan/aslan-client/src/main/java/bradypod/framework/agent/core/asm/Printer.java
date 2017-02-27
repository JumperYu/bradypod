package bradypod.framework.agent.core.asm;

public interface Printer {
	public void println(String msg);
	
	public void println(int msg);
	
	public void println(Exception exception);
}