package bradypod.framework.vm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import bradypod.framework.vm.HeapOutOfMemory.TestCase;

/**
 * 方法内存溢出, 其实不是很理解
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月23日 上午10:02:20
 */
public class MethodAreaOutOfMemory {

	public static void main(String[] args) {

		while (true) {

			Enhancer enhancer = new Enhancer();

			enhancer.setSuperclass(TestCase.class);

			enhancer.setUseCache(false);

			enhancer.setCallback(new MethodInterceptor() {

				@Override
				public Object intercept(Object arg0, Method arg1, Object[] arg2,

				MethodProxy arg3) throws Throwable {

					return arg3.invokeSuper(arg0, arg2);

				}

			});

			enhancer.create();

		}

	}
}
