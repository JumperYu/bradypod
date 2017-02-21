package bradypod.framework.agent.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

public class AslanReflectUtil {

	public static void setValue(Field field, Object obj, Object val) {
		final boolean isAccessible = field.isAccessible();
		try {
			field.setAccessible(true);
			field.set(obj, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			field.setAccessible(isAccessible);
		}
	}

	public static byte[] getByteCode(Class<?> clazz) throws IOException {
		InputStream stream = clazz.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[65536];
		while ((nRead = stream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}
		buffer.flush();
		return buffer.toByteArray();
	}
}
