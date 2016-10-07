package bradypod.framework.agent;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class TestAscii {

	@Test
	public void testLine() throws UnsupportedEncodingException {
		String line = System.lineSeparator();
		for (char c : line.toCharArray()) {
			System.out.println((int) c);
		}
		System.out.println("123" + new String(new byte[]{0x0D, 0x0A}) + "321");
		System.out.println((int)'\r');
	}
}
