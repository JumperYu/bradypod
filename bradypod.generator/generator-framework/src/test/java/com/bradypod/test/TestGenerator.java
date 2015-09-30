package com.bradypod.test;

import org.junit.Test;

import cn.org.rapid_framework.generator.GeneratorFacade;

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
//		generatorFacade.deleteByTable("t_brand_info", "template");
//		generatorFacade.generateByTable("t_brand_info", "template");
//		generatorFacade.deleteByTable("t_comment", "template");
//		generatorFacade.generateByTable("t_comment", "template");
//		generatorFacade.deleteByTable("t_item_info", "template");
//		generatorFacade.generateByTable("t_item_info", "template");
//		generatorFacade.deleteByTable("t_brand_info", "template");
//		generatorFacade.generateByTable("t_brand_info", "template");
//		generatorFacade.deleteByTable("t_ctg_info", "template");
//		generatorFacade.generateByTable("t_ctg_info", "template");
//		generatorFacade.deleteByTable("t_ctg_item", "template");
//		generatorFacade.generateByTable("t_ctg_item", "template");
//		generatorFacade.deleteByTable("t_ctg_attri", "template");
//		generatorFacade.generateByTable("t_ctg_attri", "template");
//		generatorFacade.deleteByTable("t_dict_sys", "template");
//		generatorFacade.generateByTable("t_dict_sys", "template");
		generatorFacade.deleteByTable("t_dict_user", "template");
		generatorFacade.generateByTable("t_dict_user", "template");
		
		// generatorFacade.generateByAllTable("template");
		// 自动搜索数据库中的所有表并生成文件,template为模板的根目录
		// g.generateByClass(Blog.class,"template_clazz");

		// g.deleteByTable("table_name", "template"); //删除生成的文件
		// 打开文件夹
//		Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
	}

}