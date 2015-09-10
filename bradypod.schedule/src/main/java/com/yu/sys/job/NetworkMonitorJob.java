package com.yu.sys.job;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
public class NetworkMonitorJob implements Job {

	private static final Logger log = LoggerFactory
			.getLogger(NetworkMonitorJob.class);

	private static int range = 50;

	private static Random random = new Random();

	private static String article_url_prefix = "http://www.bradypod.com/article/";

	private static ConcurrentHashMap<Integer, String> siteMap = new ConcurrentHashMap<Integer, String>();

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		// 打印sitemap
		log.info("sitemap: " + siteMap);
		/*for (String siteUrl : siteMap.values()) {
			postUrlToBaiDu(siteUrl);
		}*/

		// 生成随机地址
		int r = random.nextInt(range);
		String url = article_url_prefix + r;

		// 请求地址, 存在则放入sitemap
		requestWebSite(r, url);
	}

	/**
	 * 请求站点地址
	 * 
	 * @param r
	 *            - 随机数
	 * @param url
	 *            - 地址
	 */
	public static void requestWebSite(int r, String url) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
			HttpGet httpGet = new HttpGet(url);
			try (CloseableHttpResponse response = httpClient.execute(httpGet);) {
				// 加入sitemap
				if (response.getStatusLine().getStatusCode() == 200) {
					log.info("put in sitemap:" + url);
					siteMap.put(r, url);
				} else if (siteMap.containsKey(r)) {
					log.info("remove from sitemap:" + url);
					siteMap.remove(r);
				}
			}
		} catch (IOException e) {
			log.error(String.format("网络监测 %s 出现错误", url), e);
		}
	}

	/**
	 * 推送url到百度的站长
	 * 
	 */
	public static void postUrlToBaiDu(String siteUrl) {
		log.info("start post");
		HttpURLConnection conn = null;
		try {
			URL url = new URL(BAIDU_POST_URL + "?site=" + WEB_SITE + "&token="
					+ BAIDU_POST_TOKEN);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST"); // set GET/POST
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "text/plain"); // -->
			conn.setConnectTimeout(3000);
			conn.connect();
			if (siteUrl != null) {
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				// 要传的参数
				dos.writeBytes(siteUrl.toString());
				dos.flush();
				dos.close();
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			StringBuilder lines = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				lines.append(line);
			}
			reader.close();
			log.info(lines.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			log.info("end post");
		}
	}

	private static final String BAIDU_POST_URL = "http://data.zz.baidu.com/urls";
	private static final String BAIDU_POST_TOKEN = "17miCCQ64F2VjhHp";
	private static final String WEB_SITE = "blog.bradypod.com";
}
