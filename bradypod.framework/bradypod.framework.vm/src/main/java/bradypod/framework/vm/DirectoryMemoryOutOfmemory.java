package bradypod.framework.vm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 直接内存溢出
 * 
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午10:05:00
 */
@SuppressWarnings("restriction")
public class DirectoryMemoryOutOfmemory {

	private static final int ONE_MB = 1024 * 1024;

	private static int count = 1;

	public static void main(String[] args) {

		try {

			Field field = Unsafe.class.getDeclaredField("theUnsafe");

			field.setAccessible(true);

			Unsafe unsafe = (Unsafe) field.get(null);

			while (true) {

				unsafe.allocateMemory(ONE_MB);

				count++;

			}

		} catch (Exception e) {

			System.out.println("Exception:instance created " + count);

			e.printStackTrace();

		} catch (Error e) {

			System.out.println("Error:instance created " + count);

			e.printStackTrace();

		}

	}

}
