package com.yu.shop.item.center.service.impl;

import java.text.DecimalFormat;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.shop.item.center.mapper.CommentCountMapper;
import com.bradypod.shop.item.center.po.CommentCount;
import com.bradypod.shop.item.center.service.CommentService;

public class CommentServiceImplTest extends BaseTest {

	private CommentService commentService;

	CommentCountMapper commentCountMapper;

	@Before
	public void getBean() {
		commentService = applicationContext.getBean(CommentService.class);
		commentCountMapper = applicationContext
				.getBean(CommentCountMapper.class);
	}

	@Test
	public void testInsert() {
		for (;;) {
			CommentCount commentCount = new CommentCount();
			commentCount
					.setEntityId(Long.parseLong(RandomUtils.nextInt(5) + ""));
			commentCount.setEntityType(1);
			commentCount.setStarNum(5);
			commentCount.setCommentId(RandomUtils.nextLong());
			commentService.saveInCommentCount(commentCount);
		}
	}

	@Test
	public void testGetGoodCount() {
		for (int i = 0, len = 5; i < len; i++) {
			int goodCounts = commentService.getGoodCommentCount(1L, 1);
			int allCounts = commentService.getAllCommentCount(1L, 1);

			DecimalFormat df = new DecimalFormat("#");
			System.out.println(df.format((float) goodCounts / allCounts * 100)
					+ "%");
			if (i == 3) {
				CommentCount commentCount = new CommentCount();
				commentCount.setId(6L);
				commentCount.setStarNum(1);
				commentService.update(commentCount);
			}
		}
	}

	@Test
	public void testGet() {
		CommentCount commentCount = new CommentCount();
		commentCount.setId(1L);
		System.out.println(commentCountMapper.get(commentCount));
	}

	@Test
	public void testUpdate() {
		CommentCount commentCount = new CommentCount();
		commentCount.setId(1L);
		commentCount.setStarNum(4);
		commentCountMapper.update(commentCount);
	}
}
