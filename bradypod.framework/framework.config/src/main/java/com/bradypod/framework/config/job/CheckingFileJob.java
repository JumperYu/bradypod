package com.bradypod.framework.config.job;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;

public class CheckingFileJob {

	static ThreadLocal<String> holder = new ThreadLocal<>();

	static boolean compileAndSet(String path) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(path));
			String md5Hex = DigestUtils.md5Hex(bytes);
			if (holder.get() != null && holder.get().equals(md5Hex)) {
				return true;
			} else{
				holder.set(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
