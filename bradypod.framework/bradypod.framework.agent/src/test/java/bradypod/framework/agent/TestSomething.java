package bradypod.framework.agent;

import org.junit.Test;

public class TestSomething {
	
	@Test
	public void testIt(){
		System.out.println(Test.class.getProtectionDomain().getCodeSource().getLocation().getFile());
	}
	
}
