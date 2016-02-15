package com.bradypod.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.bradypod.bean.bo.PageData;
import com.bradypod.search.lucene.bo.ItemIndex;
import com.bradypod.search.lucene.service.ItemIndexService;
import com.bradypod.shop.item.center.po.ItemInfo;

/**
 * 搜索接口
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年1月18日
 */
@WebServlet("/search")
public class SeachController extends HttpServlet {
	
	private ItemIndexService itemIndexService;
	
	public SeachController() {
		itemIndexService = new ItemIndexService();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.info("someone call from {}", req.getRemoteHost());
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("utf-8");
		
		String title = req.getParameter("title");
		String jsonp = req.getParameter("jsonp");
		
		ItemIndex itemIndex = new ItemIndex();
		itemIndex.setTitle(title);
		itemIndex.setSortField("createTime");
		
		PageData<ItemInfo> pageData = itemIndexService.searchIndex(itemIndex);
		
		String ret = jsonp + "(" + JSON.toJSONString(pageData) + ")";
		
		PrintWriter out = resp.getWriter();
		out.write(ret);
		out.flush();
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String title = req.getParameter("title");
		Long id = Long.parseLong(req.getParameter("id"));
		
		ItemIndex itemIndex = new ItemIndex();
		itemIndex.setId(id);
		itemIndex.setCtgId(2000L);
		itemIndex.setCreateTime(new Date());
		itemIndex.setTitle(title);
		itemIndexService.createIndex(itemIndex);
		return;
	}
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SeachController.class);
	
	private static final long serialVersionUID = 1L;
}
