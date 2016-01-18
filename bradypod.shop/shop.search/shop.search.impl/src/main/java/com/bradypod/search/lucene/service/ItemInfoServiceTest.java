package com.bradypod.search.lucene.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.bradypod.bean.bo.PageData;
import com.bradypod.search.lucene.bo.ItemIndex;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.shop.item.center.service.ItemInfoService;
import com.bradypod.util.bean.BeanCopyUtil;
import com.bradypod.util.thread.ThreadPool;
import com.bradypod.util.thread.ThreadWorker;

public class ItemInfoServiceTest {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		testSearch();
		// testCreate();
	}

	static final ItemIndexService itemInfoIndexService = new ItemIndexService();

	public static void testSearch() {

		int pageNO = 1;
		int pageSize = 5;
		int count = 0;

		ItemIndex itemIndex = new ItemIndex();
		itemIndex.setTitle("榴莲干");
		itemIndex.setSortField("createTime");
		itemIndex.setDescending(true);
		itemIndex.setPageNO(pageNO);
		itemIndex.setPageSize(pageSize);

		PageData<ItemInfo> pageData = itemInfoIndexService
				.searchIndex(itemIndex);
		count = pageData.getTotalPage();
		
		for (; pageNO <= count; pageNO++) {
			itemIndex.setPageNO(pageNO);
			pageData = itemInfoIndexService.searchIndex(itemIndex);
			System.out.println(JSON.toJSONString(pageData));
		}
	}

	/**
	 * 单线程创建索引
	 */
	public static void testCreate() {
		ApplicationContext context = startSpring();
		ItemInfoService itemInfoService = context.getBean("itemInfoService",
				ItemInfoService.class);
		AtomicLong id = new AtomicLong(0);
		Integer pageNO = 1;
		Integer pageSize = 10;
		// 40801
		while (id.get() < 40770) {
			ThreadPool pool = new ThreadPool(100);
			pool.executeThread(new ThreadWorker() {

				@Override
				public void execute() {
					// 每次查询大于上一页的数据
					PageData<ItemInfo> pageData = itemInfoService.findPageData(
							id.getAndIncrement() * pageSize + 1, pageNO,
							pageSize);
					// 如果查到的数据没有则停止程序
					List<ItemInfo> data = pageData.getList();
					if (data == null || data.size() <= 0) {
						return;
					}

					for (ItemInfo itemInfo : data) {
						ItemIndex index = BeanCopyUtil.copyProperties(
								itemInfo, ItemIndex.class);
						// 打印数据
						logger.info("检索到数据: " + JSON.toJSONString(index));
						// 调用lucene索引服务
						itemInfoIndexService.createIndex(index);
						// 纪录最新id
					}
				}
			});

			try {
				TimeUnit.MILLISECONDS.sleep(10);
			} catch (InterruptedException e) {
				logger.error("wait error", e);
			}

		}// -> end while

		// 关闭写入器
		itemInfoIndexService.close();
		logger.info("完成");
	}
	
	public static void testCreate2(){
		ItemIndex itemIndex = new ItemIndex();
		itemIndex.setId(200000L);
		itemIndex.setTitle("zxm at home");
		itemInfoIndexService.createIndex(itemIndex);
	}

	public static ApplicationContext startSpring() {
		return new ClassPathXmlApplicationContext("test-rmi.xml");
	}

	static Logger logger = LoggerFactory.getLogger(ItemInfoServiceTest.class);
}
