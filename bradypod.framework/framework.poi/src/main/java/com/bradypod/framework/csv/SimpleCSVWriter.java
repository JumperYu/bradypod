package com.bradypod.framework.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import org.apache.poi.util.IOUtils;

/**
 * 简单实现csv的写入操作, 依赖于commons-csv, 可用于下载导出
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月30日 上午10:27:26
 */
public class SimpleCSVWriter {

	private char[] newline = Defaults.LINE_SEPERATOR;

	private Writer writer;

	private final FieldMunger munger = new FieldMunger();

	public SimpleCSVWriter(Writer writer) {
		this.writer = writer;
	}

	/**
	 * 缓冲输出, 并且不关闭外部输出流
	 * 
	 * @param out
	 *            输出流需要外部调用者执行关闭操作
	 * @param callback
	 *            回调闭包
	 * @throws Exception
	 */
	public static void writeBuffer(OutputStream out, WriterCallback callback) throws Exception {

		// 默认会走到java.io.tempdir定义的目录
		File tempFile = File.createTempFile(UUID.randomUUID().toString().replaceAll("-", ""),
				".csv");
		// 当线程完成执行删除
		tempFile.deleteOnExit();
		// 闭包输出
		try (Writer bufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				tempFile), "GBK"));) {
			SimpleCSVWriter simpleWriter = new SimpleCSVWriter(bufferWriter);
			// 闭包 执行
			callback.withWriter(simpleWriter);
		}
		// 闭包结束执行拷贝
		try (FileInputStream in = new FileInputStream(tempFile)) {
			IOUtils.copy(in, out);
		}
	}

	/**
	 * 缓冲输出, 但需要关闭外部输出流
	 * 
	 * @param out
	 *            输出流需要外部调用者执行关闭操作
	 * @param callback
	 *            回调闭包
	 * @throws Exception
	 */
	public static void writeDirect(OutputStream out, WriterCallback callback) throws Exception {
		if (out == null) {
			throw new RuntimeException("output stream is null");
		}
		// 闭包输出
		try (Writer bufferWriter = new BufferedWriter(new OutputStreamWriter(out))) {
			SimpleCSVWriter simpleWriter = new SimpleCSVWriter(bufferWriter);
			// 闭包 执行
			callback.withWriter(simpleWriter);
		}
	}

	/**
	 * 缓冲输出到文件
	 * 
	 * @param out
	 *            输出流需要外部调用者执行关闭操作
	 * @param callback
	 *            回调闭包
	 * @param appended
	 *            是否拼接
	 * @throws Exception
	 */
	public static void writeFile(File file, WriterCallback callback, boolean appended)
			throws Exception {
		// 闭包输出
		try (Writer bufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				file, appended)))) {
			SimpleCSVWriter simpleWriter = new SimpleCSVWriter(bufferWriter);
			// 闭包 执行
			callback.withWriter(simpleWriter);
		}
	}

	/**
	 * 回调使用
	 * 
	 * @param fields
	 *            字段
	 * @throws IOException
	 */
	public void append(Object[] fields) throws IOException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			CharSequence field = munger.munge(String.valueOf(fields[i]));
			buffer.append(field);
			if (i < fields.length - 1)
				buffer.append(munger.getSeperator());
		}
		buffer.append(newline);
		writer.write(buffer.toString());
	}
}
