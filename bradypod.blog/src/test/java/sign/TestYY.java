package sign;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;

public class TestYY {

	private long time;

	public static final String KEY_93PK = "mmgs4cl3x_jwyegDT2e9oWzyx8iDOgh6";
	public static final String KEY_8090 = "7Zo5aWhjh4949b5G_7bBTnxVpQYTrHmi";
	public static final String KEY_game2 = "w2TV2_qvl6zmclLjAvn1jETrP9Wy20m8";

	@Before
	public void init() {
		time = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
	}

	@Test
	public void testLogin() {
		// gameName+platformName+serverCode+uid+time+fcm+key
		String signKey = String.format("%s%s%s%s%d%d%s", "smyy", "160yx", "s0",
				"71", 1430898530, 1, "miwEkL5mzCISiXnGgiuGJJPPkxaSw80r");
		String sign = DigestUtils
				.md5Hex("smyy2323wans02323wan_66769614310519261rvDihMOmyLMrs4EWTgxoSSwWjywkqVLQ")
				.toString();
		System.out.println("---------sign key:" + signKey);
		System.out.println("---------sign result:" + sign);
	}

	@Test
	public void testGetRole() {
		// gameName+platformName+serverCode+uid+time+key
		String signKey = String.format("%s%s%s%s%d%s", "smyy", "5132", "s0",
				"1111496", 1433402106, "A0MjKSlF6nPfzsOooj7lbhZWfeSwRFTh");
		String sign = DigestUtils.md5Hex(signKey).toString();
		System.out.println("---------sign key:" + signKey);
		System.out.println("---------sign result:" + sign);
	}

	// |http://proxy.gop.yy.com/common/smyy/93pk/charge.do?uid=wgole&serverCode=s0&time=1430886898&orderId+=201505061234582998&rolename=%E7%99%BD%E5%BD%B1%E5%AD%90&gameCurrency=10&rmb=1&sign=4306917de7099dbe5552d0271ad943ee

	@Test
	public void testCharge() {
		// gameName+platformName+serverCode+uid+orderId+rmb+gameCurrency+time+key
		String signKey = String.format("%s%s%s%s%s%s%s%d%s", "smyy", "93pk",
				"s0", "wgole", "201505061234582999", "1", "10", time,
				"mmgs4cl3x_jwyegDT2e9oWzyx8iDOgh6");
		String sign = DigestUtils.md5Hex(signKey).toString();
		System.out.println("---------sign key:" + signKey);
		System.out.println("---------sign result:" + sign);
	}

	// 测试神魔三国的充值回调
	@Test
	public void testPostCharge() {
		// sign=84f0eb3123a916dbc7f1705a889714f9
		String path = "http://proxy.gop.yy.com/common/smyy/8090/charge.do";
		String game = "smyy";
		String platform = "8090";
		String uid = "qiufuqi";
		String serverCode = "s0";
		String orderId = "cs3165555413132";
		int gameCurrency = 10; // 元宝
		int rmb = 1; // 元或分
		String signKey = String.format("%s%s%s%s%s%s%s%d%s", game, platform,
				serverCode, uid, orderId, rmb, gameCurrency, time, KEY_8090);
		String sign = DigestUtils.md5Hex(signKey).toString();
		String content = String
				.format("uid=%s&serverCode=%s&time=%d&orderId=%s&gameCurrency=%d&rmb=%d&sign=%s",
						uid, serverCode, time, orderId, gameCurrency, rmb, sign);
		System.out.println("content:" + content);
		request(path, "GET", null, content);
	}

	// 测试神魔批量查询角色
	@Test
	public void testGetRoles() {
		String path = "http://proxy.gop.yy.com/common/smyy/game2/playerInfos.do";
		String users = "21,haha-1";
		String server_id = "s1";
		String signKey = String.format("get_player_info_%d%s", time, KEY_game2);
		String sign = DigestUtils.md5Hex(signKey).toString();
		String content = String.format("users=%s&server_id=%s&time=%d&sign=%s",
				users, server_id, time, sign);
		System.out.println(content);
		request(path, "GET", null, content);
	}

	// 测试对接反馈系统
	@Test
	public void testSuggest() throws UnsupportedEncodingException {
		String path = "http://feedback.gop.yy.com/suggest/index.jspx";
		String game = "yssj";
		String platform = "yy";
		String server = "tcc";
		String uid = "ys341";
		String rolename = "林塞ず霍克";
		// String key = "LHtQYdFLqsT7WscVN3a1wxqSoCLMygU8"; // djwy
		// String key = "fadad9c1df7a4241a495a54804209570"; // smyy
		// String key = "EZ8CIxa_5u3HHiX4JZlLybUCxTPS_cI_"; // djwy(正)
		// String key = "3MMG4rpkeWLD5C3OpyABVS1eUMARZwJP"; // hjzz(test)
		// String key = "fLo8_F4vN5uu4hl6ejFX_LVmg5LCe3bB"; // hjzz(replease)
		// String key = "bkmqazFQpy4Ie2yGfCiObvHhzs95KgPL"; //yssj(test)
		String key = "0DxH0UaiOl3xd1vffgEBtok5FU906BNj"; // yssj(release)
		String signKey = String.format("%s%s%s%s%s%d%s", game, platform,
				server, uid, rolename, time, key);
		String sign = DigestUtils.md5Hex(signKey).toString();
		String content = String
				.format("game=%s&platform=%s&server=%s&uid=%s&rolename=%s&ts=%d&sign=%s",
						game, platform, server, uid, rolename, time, sign);
		System.out.println(signKey); //
		System.out.println(path + "?" + content);
	}

	public void request(String path, String method,
			Map<String, String> headers, String content) {
		String ret = "";
		HttpURLConnection conn = null;
		try {
			URL url = new URL(path);
			if (path.startsWith("https")) {// -->> Begin https init
				// ignoreSSL();
			}// -->> End of https init
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod(method); // set GET/POST has problem
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded"); // --> default header
			if (headers != null) {
				for (String key : headers.keySet()) {
					conn.setRequestProperty(key, headers.get(key));
				}// -->> end of for
			}// -->> end of if
			conn.connect();
			if (content != null) {
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				// 要传的参数
				dos.writeBytes(content.toString());
				dos.flush();
				dos.close();
			}
			int code = conn.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(), "UTF-8"));
				String lines = "";
				while ((lines = reader.readLine()) != null) {
					ret += lines;
				}
				reader.close();
			} else {
				System.out.println("request error");
			}
		} catch (Exception e) {
			ret = "";
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		System.out.println("request result=" + ret);
	}

	@Test
	public void testFor() {
		List<Integer> arrays = new ArrayList<Integer>();
		for (Integer a : arrays) {
			System.out.println(a);
		}
	}
}
