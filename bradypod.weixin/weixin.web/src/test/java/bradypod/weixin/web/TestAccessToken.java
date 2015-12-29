package bradypod.weixin.web;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bradypod.web.util.HttpRequest;

public class TestAccessToken {

	@Test
	public void testGetToken() {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=%s&appid=%s&secret=%s";
		String grantType = "client_credential";
		String appId = "wx035bc4cff757c829";
		String appSecret = "8c09fe5e5f0b0e718d33b70d7d407e80";
		String ret = HttpRequest.request(
				String.format(url, grantType, appId, appSecret), "GET", null,
				null);
		JSONObject json = JSON.parseObject(ret);
		String token = json.getString("access_token");
		// 创建按钮
//		createBtn(token);
		deleteBtn(token);
	}

	public void createBtn(String token) {
		String createBtnUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
		JSONObject data = new JSONObject();
		JSONArray btns = new JSONArray();
		JSONObject btn = new JSONObject();
		btn.put("type", "view");
		btn.put("name", "开始天天微逛");
		btn.put("url", "http://1ovemaker.imwork.net/index.html");
		btns.add(btn);
		data.put("button", btns);
		System.out.println(data.toJSONString());
		HttpRequest.request(String.format(createBtnUrl, token), "POST", null,
				data.toJSONString());
	}

	public void deleteBtn(String token) {
		String deleteBtnUrl = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";
		HttpRequest.request(String.format(deleteBtnUrl, token), "GET", null,
				null);
	}

}
