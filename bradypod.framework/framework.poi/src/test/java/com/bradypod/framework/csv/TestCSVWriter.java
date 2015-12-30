package com.bradypod.framework.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class TestCSVWriter {

	@Test
	public void testWriteDirect() throws FileNotFoundException, Exception {
		SimpleCSVWriter.writeDirect(new FileOutputStream(new File("E://test.csv")),
				new WriterCallback() {
					@Override
					public void withWriter(SimpleCSVWriter writer) throws IOException {
						for (int i = 1; i <= 10000; i++) {
							writer.append(new Object[] { "11", "2" });
						}
					}
				});
	}

	@Test
	public void testWriteBuffer() throws Exception {
		FileOutputStream out = new FileOutputStream(new File("E://test2.csv"));
		SimpleCSVWriter.writeBuffer(out, new WriterCallback() {

			@Override
			public void withWriter(SimpleCSVWriter writer) throws Exception {
				for (int i = 1; i <= 10000; i++) {
					writer.append(new Object[] { "11", "2" });
				}
			}
		});
		out.flush();
		out.close();
	}

	@Test
	public void testFile() throws Exception {
		SimpleCSVWriter.writeFile(new File("E://test3.csv"), new WriterCallback() {

			@Override
			public void withWriter(SimpleCSVWriter writer) throws Exception {
				for (int i = 1; i <= 10000; i++) {
					writer.append(new Object[] { "11", "2" });
				}
			}
		}, false);
	}
}
