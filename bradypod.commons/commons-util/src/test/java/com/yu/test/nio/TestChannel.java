package com.yu.test.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.lang.time.StopWatch;
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
public class TestChannel {

	@Test
	public void testFileChannel() throws IOException {

		// 打开文件通道，1，传入路径；2，设置设定通道方式
		FileChannel channel = FileChannel.open(Paths.get("E://test.txt"),
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);

		// 写入数据结束后，需要重新flip
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putChar('A').flip();

		// 写入buffer TODO: 实际测试发现文件内多了一个空格符
		channel.write(buffer);
	}

	@Test
	public void transfer() throws MalformedURLException, IOException {
		// 读取一张网络图片到通道
		String url = "http://www.lagou.com/images/qr/job_qr_btm.png";
		InputStream in = new URL(url).openStream();
		ReadableByteChannel srcChannel = Channels.newChannel(in);

		FileChannel destChannel = FileChannel.open(Paths.get("E://test.png"),
				StandardOpenOption.CREATE, StandardOpenOption.WRITE);

		// 相对于循环读取到缓冲区， 直接交换两个通道要来的简单和快捷
		destChannel.transferFrom(srcChannel, 0, Integer.MAX_VALUE);

		destChannel.close();
	}

	@Test
	public void testCopyByBuffer() {
		// 相对于流来说还是简洁了一点点, 其实是自己骗自己
		try (FileChannel source = FileChannel.open(Paths.get("E://test.png"),
				StandardOpenOption.READ);
				FileChannel dest = FileChannel.open(Paths.get("E://test3.png"),
						StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
			ByteBuffer buffer = ByteBuffer.allocate(24);
			while (source.read(buffer) > 0 || buffer.position() != 0) {
				buffer.flip();
				dest.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCopyByChannel() {
		// 相对于流来说还是简洁了一点点, 其实是自己骗自己
		try (FileChannel source = FileChannel.open(Paths.get("E://test.png"),
				StandardOpenOption.READ);
				FileChannel dest = FileChannel.open(Paths.get("E://test4.png"),
						StandardOpenOption.WRITE, StandardOpenOption.CREATE)) {
			// 直接操作通道
			source.transferTo(0, source.size(), dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 测试内存映射
	// 原理就是内存地址映射到文件
	@Test
	public void testMapBuffer() throws IOException {
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		int mapSize = 1024 * 1024 * 1024;
		String file = "E://1gb.txt";

		FileChannel channel = FileChannel.open(Paths.get(file),
				StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);

		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE,
				0, mapSize);

		for (int i = 0; i < mapSize; i++) {
			buffer.put((byte) 'A');
		}
		
		// 尽量调用force同步更新文件
		buffer.force();
		
		channel.close();
		
		stopWatch.stop();
		
		System.out.println("cost time: " + stopWatch.getTime());
	}

	@Test
	public void createFileByBIO() throws Exception {

		int mapSize = 1024 * 1024 * 1024;
		String file = "E://1gb_by_bio.txt";

		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		for (int i = 0; i < mapSize; i++) {
			raf.writeByte('A');
		}

		raf.close();
	}
}
