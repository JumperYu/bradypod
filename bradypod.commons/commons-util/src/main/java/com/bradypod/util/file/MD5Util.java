package com.bradypod.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
	/***
	 * 加密字符串
	 *
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encoderStringByMd5(String str) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		return DigestUtils.md5Hex(str);
	}

	/***
	 * 加密文件
	 *
	 * @param file
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String encoderFileByMd5(File file) {

		String newstr = null;
		try (FileInputStream fis = new FileInputStream(file);) {
			// 确定计算方法
			// 加密后的字符串
			newstr = DigestUtils.md5Hex(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newstr;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {

		System.out.println(encoderStringByMd5("23"));
	}
}