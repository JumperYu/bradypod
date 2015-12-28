package test;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * META-INF/spring.handlers指定的处理类http\://www.bradypod.com/schema/test=test.TestNamespaceHandler
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年10月24日 下午3:24:12
 */
public class TestNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("custom", new TestCustomBeanDefinitionParser());
	}
}
