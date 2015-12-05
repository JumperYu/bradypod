package com.yu.test.concurrent;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import com.bradypod.util.file.FindFileUtil;
import com.bradypod.util.file.MD5Util;

public class NewNIO implements NewNIoInf {

	/***
	 * 拷贝或者移动文件
	 */
	@Override
	public boolean copeOrMoveFile(String src, String tar, OptionFile_TYPE type) {

		return realCopeOrMoveFile(Paths.get(src), tar, type);
	}

	private boolean realCopeOrMoveFile(Path srcPath, String tar, OptionFile_TYPE type) {

		Path tarPath = null;
		boolean copeSuccess = true;
		// 必须原始文件存在
		if (srcPath.toFile().exists()) {
			/***
			 * 如果原始路径是带斜杠的那么就认为这是一个文件夹
			 */
			if (isDir(tar))
				tarPath = Paths.get(tar + File.separator + srcPath.toFile().getName());
			else
				tarPath = Paths.get(tar);
			/***
			 * 然后进行N次（可以作为参数）拷贝操作(出错重连),是否覆盖拷贝，拷贝属性，拷贝操作不能使用回滚选项
			 */
			for (int i = 0; i < 3; i++) {
				/***
				 * 如果是目标文件已经存在
				 */
				if (tarPath.toFile().exists()) {
					/***
					 * 如果验证两个文件夹是相同的,拷贝选项下就不用拷贝，移动选项下就是删除原始文件
					 */
					// 拷贝
					if (OptionFile_TYPE.COPE.equals(type)) {
						if (equalsFile(srcPath.toFile(), tarPath.toFile()))
							return true;
						else
							copeSuccess = copeFile(srcPath, tarPath, true);
					}
					/***
					 * 移动操作，这里得非常小心,正常情况下，如果两个文件是一样的话，
					 * 那么直接删除原始文件就可以了。但是，如果两个文件的一样，并且地址也
					 * 是一样的话，那么就不能删除原始的了，因为就是同一个文件，不能删除的。
					 */
					else if (OptionFile_TYPE.MOVE.equals(type)) {
						if (equalsFile(srcPath.toFile(), tarPath.toFile())) {
							if (!srcPath.toFile().getAbsoluteFile()
									.equals(tarPath.toFile().getAbsoluteFile()))
								try {
									Files.delete(srcPath);
									/***
									 * 之所以要手动指向true，是因为可能存在前面删除失败的情况
									 */
									if (!copeSuccess)
										copeSuccess = true;
								} catch (IOException e) {
									copeSuccess = false;
								}
							// 前面因为有异常的可能就不直接return，这里就可以了
							else
								return true;
						} else
							copeSuccess = moveFile(srcPath, tarPath);
					}
				}
				/***
				 * 当目标文件不存在的时候,先判断父类文件夹是可创 建(父类文件夹存在或者可以创建)，可创建时就创建
				 */
				else {
					File par = tarPath.getParent().toFile();
					/***
					 * 如果父类文件夹不存在并且无法创建，那么就不用拷贝了
					 */
					if (!par.exists() && !par.mkdirs())
						copeSuccess = false;
					else if (OptionFile_TYPE.COPE.equals(type))
						copeSuccess = copeFile(srcPath, tarPath, false);
					else if (OptionFile_TYPE.MOVE.equals(type))
						copeSuccess = moveFile(srcPath, tarPath);
				}
				// 如果操作成功，跳出循环
				if (copeSuccess)
					break;
			}
		} else
			copeSuccess = false;
		return copeSuccess;
	}

	/****
	 * 拷贝文件
	 */

	private boolean copeFile(Path srcPath, Path tarPath, boolean isExist) {

		if (isExist)
			try {
				Files.copy(srcPath, tarPath, StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
			} catch (IOException e) {
				return false;
			}
		else
			try {
				Files.copy(srcPath, tarPath, StandardCopyOption.COPY_ATTRIBUTES);
			} catch (IOException e) {
				return false;
			}
		return true;
	}

	/***
	 * 移动文件,不能使用属性选项
	 *
	 * @param srcPath
	 * @param tarPath
	 * @return
	 */
	private boolean moveFile(Path srcPath, Path tarPath) {

		try {
			Files.move(srcPath, tarPath, StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/***
	 * 判断path路径是否是一个文件夹
	 *
	 * @param path
	 * @return
	 */
	private boolean isDir(String path) {

		char lastC = path.charAt(path.length() - 1);
		if (lastC == '\\' || lastC == '/')
			return true;
		return false;
	}

	/***
	 * 这是来验证两个文件是否相同，只是简单验证，可以强制必须使用md5进行验证
	 */

	public boolean equalsFile(File src, File tar) {

		// 如果两个文件的长度不一样，那么肯定两个文件是不一样的
		if (src.length() != tar.length())
			return false;
		if (!src.getName().equals(tar.getName()) || src.lastModified() != tar.lastModified())
			return MD5Util.encoderFileByMd5(src).equals(MD5Util.encoderFileByMd5(tar));
		return true;
	}

	/***
	 * 拷贝或者移动文件夹
	 */
	@Override
	public void copeOrMoveDirectory(String src, final String tar, int tierSize,
			final OptionFile_TYPE type) {

		if (!new File(src).exists())
			throw new RuntimeException("找不到原始文件夹" + src);
		final int rootPos = getRootPosition(new File(src), tierSize);
		if (rootPos != -1) {
			try {
				Files.walkFileTree(Paths.get(src), new FileVisitor<Path>() {

					String tarDirString = null;

					/***
					 * 到达文件夹前,先把目标路径写好
					 *
					 * @param dir
					 * @param attrs
					 * @return
					 * @throws IOException
					 */
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
							throws IOException {

						tarDirString = dir.toFile().getAbsolutePath();
						tarDirString = tar + tarDirString.substring(rootPos) + File.separator;
						return FileVisitResult.CONTINUE;
					}

					/***
					 * 到达文件之后,进行拷贝或者移动操作
					 *
					 * @param file
					 * @param attrs
					 * @return
					 * @throws IOException
					 */
					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
							throws IOException {

						File f = file.toFile();
						if (f.exists() && f.canRead() && !f.isHidden())
							realCopeOrMoveFile(file, tarDirString, type);
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc)
							throws IOException {

						return FileVisitResult.CONTINUE;
					}

					/***
					 * 到达文件夹后
					 *
					 * @param dir
					 * @param exc
					 * @return
					 * @throws IOException
					 */
					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc)
							throws IOException {
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 如果是剪切操作,并且剪切成功，那么就要删除所有文件夹
			if (OptionFile_TYPE.MOVE.equals(type) && isBlockDir(src))
				delDir(src);
		} else
			throw new RuntimeException("指定父类文件夹层次错误~~~");
	}

	/***
	 * 根据指定层次获取指定盘符的位置
	 */

	private int getRootPosition(File file, int tier) {

		if (file != null) {
			String path = file.getAbsolutePath();
			int cc = 0;
			for (int i = path.length() - 1; i >= 0; i--) {
				if (path.charAt(i) == '\\') {
					cc++;
					if (cc == tier + 1) {
						cc = i;
						return cc;
					}
				}
			}
		}
		return -1;
	}

	/***
	 * 查看该文件夹下是否还有文件
	 *
	 * @param dirPath
	 * @return
	 */
	private boolean isBlockDir(String dirPath) {

		File dir = new File(dirPath);
		File[] dirList = dir.listFiles();
		if (dirList == null || dirList.length == 0)
			return true;
		else {
			// 寻找文件
			for (File f : dirList)
				if (!f.isDirectory())
					return false;
		}
		return true;
	}

	/***
	 * 删除空文件夹
	 *
	 * @param dirPath
	 */
	private void delDir(String dirPath) {

		File dir = new File(dirPath);
		File[] dirList = dir.listFiles();
		if (dirList == null || dirList.length == 0)
			dir.delete();
		else {
			// 删除所有文件
			for (File f : dirList)
				if (f.isDirectory())
					delDir(f.getAbsolutePath());
				else
					f.delete();
			// 删除完当前文件夹下所有文件后删除文件夹
			dirList = dir.listFiles();
			if (dirList.length == 0)
				dir.delete();
		}
	}

	/***
	 * 根据文件类型查找相关的文件
	 */
	@Override
	public List<String> findFilesByType(String dir, String[] keys, boolean isMatchCase)
			throws IOException {

		List<String> list = new ArrayList<String>();
		Files.walkFileTree(Paths.get(dir), new FindFileUtil(keys, isMatchCase, list,
				FileTypeMode.TYPES));
		return list;
	}

	/***
	 * 根据文件名称查找相关的文件
	 */
	@Override
	public List<String> findFilesByName(String dir, String[] keys, boolean isMatchCase)
			throws IOException {

		List<String> list = new ArrayList<String>();
		Files.walkFileTree(Paths.get(dir), new FindFileUtil(keys, isMatchCase, list,
				FileTypeMode.NAMES));
		return list;
	}

	public static void main(String[] args) throws IOException {

		NewNIoInf inf = new NewNIO();
		inf.copeOrMoveFile("e://aaaa.txt", "e://ccc.txt", OptionFile_TYPE.COPE);
//		inf.copeOrMoveDirectory("e://aaaa.txt", "e://aa.txt", 1, OptionFile_TYPE.MOVE);
		System.out.println(inf.findFilesByName("E://", new String[] { "txt" }, false)
				.size());

	}
}