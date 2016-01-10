package bradypod.framework.lucene.book.demo.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileText {

	public static String getText(File paramFile) {
		StringBuffer localStringBuffer = new StringBuffer("");
		try {
			BufferedReader localBufferedReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(paramFile), "GBK"));

			String str = localBufferedReader.readLine();
			while (str != null) {
				localStringBuffer.append(str);
				str = localBufferedReader.readLine();
			}
			localBufferedReader.close();
		} catch (Exception localException) {
			localStringBuffer.append("");
		}
		return localStringBuffer.toString();
	}

	public static String getText(String paramString) {
		String str = "";
		try {
			File localFile = new File(paramString);
			str = getText(localFile);
		} catch (Exception localException) {
			str = "";
		}
		return str;
	}

	public static void main(String[] paramArrayOfString) throws IOException {
		String str = getText("test.htm");
		System.out.println(str);
	}
}
