package com.bradypod.shop.entity;

import org.junit.Test;

import com.yu.util.sql.ConnectionUtil;
import com.yu.util.sql.EntityUtil;

/**
 * 测试自动生成实体对象
 * 
 *
 * @author zengxm
 * @date 2015年7月8日
 *
 */
public class DbToJavaInitTest {

	@Test
	public void testEntity() {
		// 生成类文件
		EntityUtil entityUtil = new EntityUtil(ConnectionUtil.getConnection());
		entityUtil.setServiceCreated(true);
		entityUtil.setXMLCreated(false);
		entityUtil.setDaoCreated(false);
		
		entityUtil.createJavaFile("item_center.t_comment", "Comment",
				"zengxm<https://github.com/JumperYu/bradypod>",
				"com.bradypod.shop.item.center", System.getProperty("user.dir")
						+ "\\src\\main\\java\\");
		
		entityUtil.setServiceCreated(false);
		
		entityUtil.createJavaFile("item_center.t_comment_count",
				"CommentCount", "zengxm<https://github.com/JumperYu/bradypod>",
				"com.bradypod.shop.item.center", System.getProperty("user.dir")
						+ "\\src\\main\\java\\");
	}

}
