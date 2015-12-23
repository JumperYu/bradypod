package bradypod.framework.vm;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量池溢出
 *
 * @vm -XX:PermSize=10M -XX:MaxPermSize=10M
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午9:33:52
 */
public class ConstantOutOfMemory {

	public static void main(String[] args) throws Exception {

		try {

			List<String> strings = new ArrayList<String>();

			int i = 0;

			while (true) {

				strings.add(String.valueOf(i++).intern());

			}

		} catch (Exception e) {

			e.printStackTrace();

			throw e;

		}

	}

}
