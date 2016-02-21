package com.yu.test.nio;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * NIO(New I/O) jdk1.4推出
 * 
 * 三大主要元素：1，缓冲 （Buffer）；2，通道（Channel）；3，选择器（Selector）
 * 
 * 创建Channel有两种方式： 1，使用FileChannel.open; 2，使用FileInputStream, FileOutputStream,
 * RandomAccessFile
 * 
 * 通道是用来读取和写入数据，但不会直接操作通道，需要结合缓冲层来进行IO
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月17日
 */
public class TestPath {

	@Test
	public void testFileCopy() throws URISyntaxException, IOException {
		String file = "E://test.png";
		Path src = Paths.get(file);
		Path dest = Paths.get("E://test2.png");
		Files.copy(src, dest);
	}
}
