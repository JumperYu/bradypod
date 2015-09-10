package com.yu.util.gzip;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class GzipUtil {

	public static String GZIP_PATTERN = ",.htm,.html,.jsp,.js,.json,.css,.do,";

	/**
	 * 压缩
	 * 
	 * @param req
	 *            - 请求
	 * @param resp
	 *            - 响应
	 * @throws Exception
	 */
	public static void compress(HttpServletRequest req, HttpServletResponse resp) {
		String uri = req.getRequestURI();
		String ext = FilenameUtils.getExtension(uri);
		try {
			if (isGzipEncoding(req)) {
				// 需要过滤的扩展名：.htm,.html,.jsp,.js,.ajax,.css
				if (StringUtils.indexOf(GZIP_PATTERN, ",." + ext + ",") != -1) {
					// 设置响应头部
					req.setCharacterEncoding("UTF-8");
					resp.setHeader("Pragma", "No-cache");
					resp.setHeader("Cache-Control", "no-cache");
					resp.setDateHeader("Expires", -1);
					resp.setCharacterEncoding("UTF-8");
					resp.setContentType("text/plain;charset=utf-8");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 压缩
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		GZIPOutputStream gos = new GZIPOutputStream(baos);

		gos.write(data, 0, data.length);

		gos.finish();

		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		return output;
	}

	/**
	 * 判断浏览器是否支持GZIP
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isGzipEncoding(HttpServletRequest request) {
		boolean flag = false;
		String encoding = request.getHeader("Accept-Encoding");
		if (encoding.indexOf("gzip") != -1) {
			flag = true;
		}
		return flag;
	}
}
