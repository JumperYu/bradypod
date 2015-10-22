package com.yu.test.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestSecurity {

	// -Djava.security.manager -Djava.security.policy=E:/my.policy
	public static void main(String[] args) {
		
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		File file = new File("D:/test.txt");
		try {
			read(file);
			System.out.println("file read ok");
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}

		try {
			write(file);
			System.out.println("file write ok");
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	private static void read(File file) throws Throwable {
		InputStream in = null;
		BufferedReader reader = null;
		try {
			in = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(in));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				System.out.println("read-->" + temp);
			}
		} catch (Throwable e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
			if (reader != null) {
				reader.close();
			}
		}
	}

	private static void write(File file) throws Throwable {
		FileWriter fw = new FileWriter(file);
		for (int i = 0; i < 10; i++) {
			String temp = new java.util.Date() + " " + new java.util.Random().nextLong();
			System.out.println("write-->" + temp);
			fw.write(temp + "\r\n");
		}
		fw.flush();
		fw.close();
	}
}