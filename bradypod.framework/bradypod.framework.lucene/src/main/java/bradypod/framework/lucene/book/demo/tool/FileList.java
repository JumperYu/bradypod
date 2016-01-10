package bradypod.framework.lucene.book.demo.tool;

import java.io.File;
import java.io.IOException;

public class FileList {
	
	private static StringBuffer sb = new StringBuffer("");
	private static File[] localObject;

	public static String[] getFiles(File paramFile) throws IOException {
		if (paramFile.isDirectory()) {
			localObject = paramFile.listFiles();
			for (int i = 0; i < localObject.length; i++) {
				getFiles(localObject[i]);
			}
		}
		sb.append(paramFile.getPath() + "/");

		Object localObject = sb.toString();
		String[] arrayOfString = ((String) localObject).split("/");
		return arrayOfString;
	}

	public static String[] getFiles(String paramString) throws IOException {
		File localFile = new File(paramString);
		if (localFile.isDirectory()) {
			localObject = localFile.listFiles();
			for (int i = 0; i < localObject.length; i++) {
				getFiles(localObject[i]);
			}
		}
		sb.append(localFile.getPath() + "/");

		Object localObject = sb.toString();
		String[] arrayOfString = ((String) localObject).split("/");
		return arrayOfString;
	}

	public static void main(String[] paramArrayOfString) throws IOException {
		String[] arrayOfString = getFiles("D:/tomcat/bin");
		for (int i = 0; i < arrayOfString.length; i++) {
			System.out.println(arrayOfString[i]);
		}
	}
}
