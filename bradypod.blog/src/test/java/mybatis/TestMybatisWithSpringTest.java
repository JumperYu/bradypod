package mybatis;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

import com.yu.article.po.Article;
import com.yu.article.service.ArticleService;
import com.yu.user.po.Account;
import com.yu.user.service.UserService;
import com.yu.util.redis.RedisPool;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/spring/applicationContext**.xml")
public class TestMybatisWithSpringTest {

	@Resource
	private UserService userService;

	@Resource
	private ArticleService articleService;

	@Test
	public void testUserService() throws InterruptedException {
		long startTime = System.currentTimeMillis();
		int count = 3000;
		final CountDownLatch latch = new CountDownLatch(count);
		ExecutorService ex = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; i++) {
			ex.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(userService.validateAccount(new Account(
							"zxm", "123")));
					latch.countDown();
				}
			});
		}
		latch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("------------finish at:" + (endTime - startTime));
	}

	@Test
	public void testArticleServiceAdd() {
		Article article = new Article();
		article.setArticleId(21);
		article.setTitle("Do not g");
		article.setContent("Do not go gentle into that good night, rage, rage agianst the dying of time.");
		articleService.saveOrUpdateArticle(article);
	}

	@Test
	public void testArticleServiceQuery() {
		List<Article> articles = articleService.getAllArticles();
		System.out.println(articles.get(0).getArticleId());
		Jedis jedis = RedisPool.getJedis();
		for (int i = 0, len = articles.size(); i < len; i++) {
			long ret = jedis.del(new String("article:"
					+ articles.get(i).getArticleId()).getBytes());
			System.out.println(ret);
		}
		jedis.close();
	}

	@Test
	public void testQueryArticle() {
		System.out.println(articleService.getArticle(6));
	}
}
