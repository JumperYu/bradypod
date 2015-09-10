package com.yu.util.gzip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.http.HttpMethod;

/**
 * gzip压缩过滤器
 *
 * @author zengxm
 * @date 2015年7月23日
 *
 */
public class GzipFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		// TODO 如果method=POST不进行压缩, 是否正确
		if (GzipUtil.isGzipEncoding(req) && req.getMethod() == HttpMethod.GET.name()) {
			try {
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setHeader("Content-Encoding", "gzip");
				// TODO 如何设置content-type; 如何设置大小; 如何判断小于一定大小不压缩(越小压缩反而更大)
				resp.setContentType("text/html;charset=UTF-8");
//				resp.setContentLength(length);
				GZipResponse gzip = new GZipResponse(resp);
				chain.doFilter(request, gzip);
				int status = resp.getStatus();
				if (HttpStatus.SC_OK == status) {
					gzip.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
