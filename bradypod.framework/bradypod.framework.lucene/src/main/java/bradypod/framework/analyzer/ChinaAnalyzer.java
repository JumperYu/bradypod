package bradypod.framework.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 中文分词测试
 * 
 * @author	zengxm<http://github.com/JumperYu>
 *
 * @date	2016年1月10日
 */
public class ChinaAnalyzer {

	public static void main(String[] args) throws IOException {

		String text = "榴莲干货";

		Analyzer analyzer = new SmartChineseAnalyzer();
//		Analyzer analyzer = new CJKAnalyzer();

		TokenStream stream = analyzer.tokenStream("", new StringReader(text));
		
		stream.reset();
		
		CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
		
		while (stream.incrementToken()) {
			System.out.print("[" + cta + "]");
		}

		analyzer.close();
		
	}

}
