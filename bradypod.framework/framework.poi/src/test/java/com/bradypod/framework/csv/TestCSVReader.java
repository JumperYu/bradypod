package com.bradypod.framework.csv;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

/**
 * 测试读取
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月31日 上午10:58:21
 */
public class TestCSVReader {

	@Test
	public void testReadForList() throws FileNotFoundException, IOException {
		List<String[]> list = SimpleCSVReader.parse(new FileInputStream(new File("E://test.csv")));
		for (String[] fields : list) {
			
		}
	}

}
