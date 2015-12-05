package com.bradypod.util.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import com.yu.test.concurrent.FileTypeMode;

public class FindFileUtil extends SimpleFileVisitor<Path> {

	/***
	 * 关键词列表，是否转换大小写，返回结果集
	 */
	private String[] keyArray = null;
	private boolean isMatchCase;
	private List<String> resultList;
	private FileTypeMode mode;

	public FindFileUtil(String[] keyArray, boolean isMatchCase, List<String> resultList,
			FileTypeMode mode) {

		this.keyArray = keyArray;
		this.isMatchCase = isMatchCase;
		this.resultList = resultList;
		this.mode = mode;
	}

	@SuppressWarnings("unused")
	private FindFileUtil() {
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

		File f = file.toFile();
		if (f.exists() && f.canRead() && !f.isHidden())
			if (this.keyArray != null) {
				for (String key : this.keyArray) {
					if (!this.isMatchCase)
						key = key.toLowerCase();
					if (matchFile(file, this.mode, key, isMatchCase))
						resultList.add(file.toString());
				}
			}
		return FileVisitResult.CONTINUE;
	}

	/***
	 * 根据大小写和类型或名称进行文件匹配
	 *
	 * @param file
	 * @param mode
	 * @param key
	 * @param isMatchCase
	 * @return
	 */
	private boolean matchFile(Path file, FileTypeMode mode, String key, boolean isMatchCase) {

		File f = file.toFile();
		if (f.exists() && f.canRead() && !f.isHidden()
				&& !"System Volume Information".equals(f.getName())) {
			String fileName = null;
			if (FileTypeMode.TYPES.equals(mode)) {

				fileName = file.toString();
				return isMatchCase ? fileName.endsWith(key) : fileName.toLowerCase().endsWith(key);
			} else if (FileTypeMode.NAMES.equals(mode)) {
				fileName = file.toFile().getName();
				return isMatchCase ? (fileName.indexOf(key) == -1 ? false : true) : (fileName
						.toLowerCase().indexOf(key) == -1 ? false : true);
			}
		}
		return false;
	}

	@Override
	public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
		// 如果错误信息中包含X:\System Volume Information，这是表示系统的隐藏盘，是不能读的
		System.out.println(exc.getMessage());
		return FileVisitResult.CONTINUE;
	}
}