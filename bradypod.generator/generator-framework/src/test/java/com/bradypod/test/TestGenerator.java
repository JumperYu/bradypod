package com.bradypod.test;

import java.lang.reflect.Type;
import java.util.Map;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

/**
 * 测试生成数据
 *
 * @author zengxm<github.com/JumperYu>
 *
 * @date 2015年9月16日 下午3:37:33
 */
public class TestGenerator {

	@Test
	public void test() throws Exception {
		GeneratorFacade generatorFacade = new GeneratorFacade();
		generatorFacade.printAllTableNames(); // 打印数据库中的表名称

		// g.deleteOutRootDir(); // 删除生成器的输出目录

		// 通过数据库表生成文件,template为模板的根目录
		generatorFacade.deleteByTable("t_item_info", "template");
		generatorFacade.generateByTable("t_item_info", "template");

		// generatorFacade.generateByAllTable("template");
		// 自动搜索数据库中的所有表并生成文件,template为模板的根目录
		// g.generateByClass(Blog.class,"template_clazz");

		// g.deleteByTable("table_name", "template"); //删除生成的文件
		// 打开文件夹
		Runtime.getRuntime().exec(
				"cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
	}

	@Test
	public void testGson() {
		Gson gson = new Gson();
		Person p = new Person(1, "a");
		System.out.println(gson.toJson(p));
		System.out.println(gson.fromJson("{5:2,3:4}", Map.class));
	}

}

class Person {
	private int id;
	private String name;

	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}