package bradypod.framework.redis;

import java.util.Arrays;

public class RedisTest {

	public static void main(String[] args) {

		RedisDB redis = new RedisDB();

		// 1.Create user follow relationship
		redis.SAdd("users", "A", "B", "C");

		// User A follows B, C
		redis.SAdd("users:A:following", "B", "C");
		redis.SAdd("users:B:followers", "A");
		redis.SAdd("users:C:followers", "A");

		// User C follows B
		redis.SAdd("users:C:following", "B");
		redis.SAdd("users:B:followers", "C");

		// 2.1 B send tweet
		int tid = redis.Incr("tweets:next_id");
		redis.Set("tweets:" + tid, "B publish hello");
		redis.LPush("global:timeline", tid);
		redis.LPush("users:B:timeline", tid);
		for (Object follower : redis.SMembers("users:B:followers"))
			redis.LPush("users:" + follower + ":timeline", tid);

		// 2.2 C send tweet
		tid = redis.Incr("tweets:next_id");
		redis.Set("tweets:" + tid, "C publish world");
		redis.LPush("global:timeline", tid);
		redis.LPush("users:C:timeline", tid);
		for (Object follower : redis.SMembers("users:C:followers"))
			redis.LPush("users:" + follower + ":timeline", tid);

		Object[] tids = redis.LRange("global:timeline", 0, 9);
		String[] tweetids = new String[tids.length];
		for (int i = 0; i < tids.length; i++)
			tweetids[i] = "tweets:" + tids[i];
		System.out.println(Arrays.toString(redis.MGet(tweetids)));
	}

}
