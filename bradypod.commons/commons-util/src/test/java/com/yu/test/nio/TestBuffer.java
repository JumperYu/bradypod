package com.yu.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * NIO(New I/O) jdk1.4推出
 * 
 * 三大主要元素：1，缓冲 （Buffer）；2，通道（Channel）；3，选择器（Selector）
 * 
 * 其中Buffer的三大要素：1，Capacity；2，Limit；3，Position
 * 
 * Buffer的几个知识点：1，缓冲区切割和共享；2，直接缓冲区；3，内存映射
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月17日
 */
public class TestBuffer {

	@Test
	public void testIntBuffer() {

		// 指定capacity, 能存储N个int值
		IntBuffer buffer = IntBuffer.allocate(8);

		for (int i = 0; i < buffer.capacity(); i++) {
			int j = 2 * (i + 1);
			// position递增
			buffer.put(j);
		}

		// 写入完成需要开始读的时候需要重置position为零
		buffer.flip();

		// 查看position是否到达limit位置
		while (buffer.hasRemaining()) {
			// position递增
			System.out.print(buffer.get() + "\t");
		}

		// 要重置position为零
		buffer.clear();

		System.out.println();
		System.out.format("capacity:%d, limit:%d, position:%d.\n",
				buffer.capacity(), buffer.limit(), buffer.position());

		// 并没有真正的清空
		while (buffer.hasRemaining()) {
			// position递增
			System.out.print(buffer.get() + "\t");
		}
	}

	@Test
	public void testByteBuffer() {

		// java中，4个字节可以表示一个整数（int）或单精度浮点数（float）
		// 8个字节可以表示一个长整形（long），双精度浮点数（double）
		ByteBuffer buffer = ByteBuffer.allocate(2);

		buffer.put((byte) 1);
		buffer.put((byte) 1);
		// buffer.put((byte)1);

		// 写入完成需要开始读的时候需要重置position为零
		buffer.flip();

		// 查看position是否到达limit位置
		while (buffer.hasRemaining()) {
			// position递增
			System.out.print(buffer.get() + "\t");
		}

	}

	@Test
	public void sliceBuffer() {

		ByteBuffer buffer = ByteBuffer.allocate(10);

		// 缓冲区中的数据0-9
		for (int i = 0; i < buffer.capacity(); ++i) {
			buffer.put((byte) i);
		}

		// 创建子缓冲区
		buffer.position(3);
		buffer.limit(7);
		ByteBuffer slice = buffer.slice();

		// 改变子缓冲区的内容
		for (int i = 0; i < slice.capacity(); ++i) {
			byte b = slice.get(i);
			b *= 10;
			slice.put(i, b);
		}

		buffer.position(0);
		buffer.limit(buffer.capacity());
		buffer.flip();

		while (buffer.remaining() > 0) {
			System.out.println(buffer.get());
		}
	}

	@Test
	public void testReadOnlyBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(10);

		// 缓冲区中的数据0-9
		for (int i = 0; i < buffer.capacity(); ++i) {
			buffer.put((byte) i);
		}

		// 创建只读缓冲区
		ByteBuffer readonly = buffer.asReadOnlyBuffer();

		// 改变原缓冲区的内容
		for (int i = 0; i < buffer.capacity(); ++i) {
			byte b = buffer.get(i);
			b *= 10;
			buffer.put(i, b);
		}

		readonly.position(0);
		readonly.limit(buffer.capacity());

		// 只读缓冲区的内容也随之改变
		while (readonly.remaining() > 0) {
			System.out.println(readonly.get());
		}
	}

	@Test
	public void testDirectBuffer() throws IOException {

		String infile = "E:\\test.pdf";

		FileInputStream fin = new FileInputStream(infile);
		FileChannel fcin = fin.getChannel();

		String outfile = String.format("E:\\testcopy.pdf");
		FileOutputStream fout = new FileOutputStream(outfile);
		FileChannel fcout = fout.getChannel();

		// 使用allocateDirect，而不是allocate
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

		while (true) {
			buffer.clear();

			int r = fcin.read(buffer);

			if (r == -1) {
				break;
			}

			buffer.flip();

			fcout.write(buffer);
		}

		fout.close();
		fin.close();
	}

	// 测试allocate的内存块
	public static void main(String[] args) {

		// -Xms10m -Xmx10m

		int capacity = 1000 * 1000 * 10; // 1GB

		// java.lang.OutOfMemoryError: 
		// Java heap space

		// ByteBuffer.allocate(capacity);
		
		ByteBuffer.allocateDirect(capacity);
		// java.lang.OutOfMemoryError: Direct buffer memory
	}
}
