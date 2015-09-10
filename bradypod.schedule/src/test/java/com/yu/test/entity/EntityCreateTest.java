package com.yu.test.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.yu.util.sql.ConnectionUtil;
import com.yu.util.sql.EntityUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 测试自动生成实体对象
 * 
 *
 * @author zengxm
 * @date 2015年7月8日
 *
 */
public class EntityCreateTest {

	@Test
	public void testEntity() {
		// 生成类文件
		EntityUtil entity = new EntityUtil(ConnectionUtil.getConnection());
		entity.createJavaFile("cms.task", "zengxm", "com.yu.po",
				System.getProperty("user.dir") + "\\src\\main\\java\\");
	}

	// 更具模板生成基础sql
	@Test
	public void testXMLTemplate() throws IOException, TemplateException {
		String xml_dir = System.getProperty("user.dir")
				+ "\\src\\main\\\\resources\\sql\\";
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(xml_dir));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		// 创建根哈希表
		Map<String, String> root = new HashMap<>();
		root.put("mapper_namespace", "com.yu.mapper.TaskMapper");
		root.put("mapper_class", "com.yu.po.Task");
		root.put("mapper_table", "task");
		root.put(
				"mapper_talbe_cols",
				"task_id, task_desc, task_type, start_time, end_time, state, create_time, create_operator, last_modify, last_operator, job_id");
		root.put(
				"mapper_po_cols",
				"${taskId}, '${taskDesc}', ${taskType}, '${startTime}', '${endTime}', ${state}, now(), '${createOperator}', now(), '${lastOperator}', ${jobId}");
		root.put("mapper_if_condition", "");
		Template temp = cfg.getTemplate("entity_template.ftl");
		Writer out = new OutputStreamWriter(new FileOutputStream(xml_dir
				+ "\\Task.xml", false));
		temp.process(root, out);
		out.flush();
	}

	@Test
	public void testFile() {
		System.out.println(System.getProperty("user.dir"));
		System.out
				.println(new File(
						"E:\\work\\yy-workspace\\bradypod\\schedule\\src\\main\\resources\\sql")
						.exists());
	}
}
