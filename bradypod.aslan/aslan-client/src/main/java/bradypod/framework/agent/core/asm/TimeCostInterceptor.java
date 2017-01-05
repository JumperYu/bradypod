package bradypod.framework.agent.core.asm;

public class TimeCostInterceptor {

	private static final ThreadLocal<Long> context = new ThreadLocal<>();

	public static void begin() {
		context.set(System.currentTimeMillis());
	}

	public static void end() throws IllegalAccessException {
		if (context.get() == null) {
			throw new IllegalAccessException("先调用TimeCostInterceptor#begin");
		}
		long stopPoint = System.currentTimeMillis();
		long startPoint = context.get();
		System.out.println("cost:" + (stopPoint - startPoint));
	}
}
