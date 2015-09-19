package com.yu.shop.item.center.service.impl;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.bradypod.common.junit.BaseTest;
import com.bradypod.shop.item.center.mapper.CommentMapper;
import com.bradypod.shop.item.center.po.Comment;

public class CommentMapperTest extends BaseTest {

	CommentMapper mapper;

	@Before
	public void getBean() {
		mapper = applicationContext.getBean(CommentMapper.class);
	}

	@Test
	public void testGet() {
		
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testDelete() {

	}

	@Test
	public void testSave() {
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setCreateTime(new Date());
		comment.setUserId(1L);
		comment.setEntityId(1L);
		comment.setEntityUserId(1L);
		comment.setEntityType(1);
		comment.setEntityInfo("1");
		comment.setStarNum(1);
		comment.setTitle("title");
		comment.setDescription("desc");
		comment.setPicUrl("1.jpg");
		comment.setStatus(1);
		mapper.save(comment);
	}
}
