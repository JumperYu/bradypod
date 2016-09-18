package bradypod.framework.agent;

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * 
 * Instrumentation的用法, 改变classfile, 类必须是完全相同
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class MyAgent {

	public static void premain(String agentOps, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException {
		// 1, main函数启动的时候, 每装在一个类, 就会尝试去是否匹配, 然后转换
		// inst.addTransformer(new Transformer());
		// 2, 直接指定转换类
		ClassDefinition def = new ClassDefinition(TransformClass.class,
				Transformer.getBytesFromFile(Transformer.NEW_CLASS_NAME));
		inst.redefineClasses(new ClassDefinition[] { def });
		System.out.println("Agent PreMain Done");
	}

	public static void agentmain(String agentArgs, Instrumentation inst)
			throws ClassNotFoundException, UnmodifiableClassException,
			InterruptedException {
		inst.addTransformer(new Transformer(), true);
		inst.retransformClasses(TransformClass.class);
		System.out.println("Agent Main Done");
	}
}
