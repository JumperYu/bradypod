package bradypod.framework.agent;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @see MyAgent
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class Transformer implements ClassFileTransformer {

	private static final String EQUAL_CLASS_NAME = "bradypod/framework/agent/TransformClass";

	public static final String NEW_CLASS_NAME = "TransformClass.class.2";

	@Override
	public byte[] transform(ClassLoader loader, String className,
			Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
			byte[] classfileBuffer) throws IllegalClassFormatException {
		if (!className.equals(EQUAL_CLASS_NAME)) {
			return null;
		}
		return getBytesFromFile(NEW_CLASS_NAME);
	}

	public static byte[] getBytesFromFile(String classQualifer) {
		try {
			InputStream in = Transformer.class
					.getResourceAsStream(classQualifer);

			ByteArrayOutputStream out = new ByteArrayOutputStream();

			byte[] buf = new byte[128];

			int len;
			do {
				len = in.read(buf);
				if (len < 0) {
					break;
				}
				out.write(buf, 0, len);
			} while (true);

			return out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
