package com.bradypod.framework.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * csv读取器
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月31日 上午9:56:36
 */
public class SimpleCSVReader {

	private static LineSplitter splitter = new LineSplitter();
	private static String commentToken = null;

	public static List<String[]> parse(InputStream in) throws IOException {
		return parse(new InputStreamReader(in));
	}

	public static List<String[]> parse(Reader in) throws IOException {
		final List<String[]> list = new ArrayList<>();
		parse(in, new ReaderCallback() {
			public void onRow(String[] fields) {
				list.add(fields);
			}
		});
		return list;
	}

	public static void parse(Reader in, ReaderCallback callback) throws IOException {
		BufferedReader reader = new BufferedReader(in);
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (commentToken == null || !line.startsWith(commentToken)) {
				callback.onRow(splitter.split(line));
			}
		}
	}

}
