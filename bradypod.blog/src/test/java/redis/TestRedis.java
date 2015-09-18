package redis;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Test;

import redis.clients.jedis.Jedis;

import com.yu.article.po.Article;
import com.yu.user.po.Account;
import com.yu.util.redis.RedisPool;
import com.yu.util.redis.RedisUtil;

public class TestRedis {
	
	// commons 不可以序列化对象
	@Test
	public void testCommonsSerial() {

		Account account = new Account();
		account.setPassport("account");
		byte[] bytes = SerializationUtils.serialize(account);
		Account anotherAccount = (Account) SerializationUtils
				.deserialize(bytes);
		System.out.println(anotherAccount);
	}
	
	// 反之
	@Test
	public void testSrpingSerial() {
		Article article = new Article();
		article.setArticleId(1);
		article.setContent("12312");
		article.setPath("1");
		byte[] bytes = org.springframework.util.SerializationUtils
				.serialize(article);
		try (Jedis jedis = RedisPool.getJedis()) {
			jedis.set("article:1".getBytes(), bytes);
			jedis.expire("article:1".getBytes(), 60 * 60);
		}
		try (Jedis jedis = RedisPool.getJedis()) {
			byte[] value = jedis.get("article:1".getBytes());
			Article another = (Article) org.springframework.util.SerializationUtils
					.deserialize(value);
			System.out.println(another);
		}
	}
	
	@Test
	public void testLocalRedis(){
		System.out.println(RedisUtil.createJedis("192.168.1.199", 7001).getSet("a", "1"));
		System.out.println(RedisUtil.createJedis("192.168.1.199", 7002).getSet("a", "1"));
		System.out.println(RedisUtil.createJedis("192.168.1.199", 7003).getSet("a", "1"));
	}
}