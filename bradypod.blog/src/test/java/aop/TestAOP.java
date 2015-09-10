package aop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yu.aop.RedisAOP;
import com.yu.aop.Waiter;
import com.yu.article.service.ArticleService;

/**
 * 测试AOP拦截service方法并使用redis缓存
 *
 * @author zengxm
 * @date 2015年8月8日
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring/applicationContext**.xml")
public class TestAOP {

	@Resource
	private Waiter waiter;
	
	@Resource
	private ArticleService articleService;

	@Test
	public void testAop() {
		System.out.println(articleService.getArticle(20).getArticleId());
		System.out.println(articleService.getArticle(23).getArticleId());
		System.out.println(articleService.getAllArticles().size());
	}

	@Test
	public void testHandOn() {
		AspectJProxyFactory factory = new AspectJProxyFactory();
		factory.setTarget(waiter);
		factory.addAspect(RedisAOP.class);
		Waiter waiter = factory.getProxy();
		waiter.greet("zxm");
		waiter.serverTo("zxm");
	}
}
