package com.yu.test.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * NIO.2提供了Files的实用方法
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月20日
 */
public class TestFiles {

	@Test
	public void testFiles() throws IOException {
		Path file = Paths.get("E://1.txt");
		List<String> lines = Lists.newArrayList("Java", "C++", "C#", "Dophil",
				"加油");
		// 写入
		Files.write(file, lines, StandardCharsets.UTF_8);
		// 获取文件大小
		Files.size(file);
		// 获取字节
		Files.readAllBytes(file);
		// 拷贝文件
		FileOutputStream out = new FileOutputStream("E://2.txt");
		Files.copy(file, out);
	}

}
