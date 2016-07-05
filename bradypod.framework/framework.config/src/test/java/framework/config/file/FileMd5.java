package framework.config.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.codec.digest.DigestUtils;

public class FileMd5 {

	public static void main(String[] args) {

		Path path1 = Paths.get("D://a.txt");
		Path path2 = Paths.get("D://b.txt");

		try {
			byte[] byte1 = Files.readAllBytes(path1);
			byte[] byte2 = Files.readAllBytes(path2);
			System.out.println(DigestUtils.md5Hex(byte1));
			System.out.println(DigestUtils.md5Hex(byte2));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
