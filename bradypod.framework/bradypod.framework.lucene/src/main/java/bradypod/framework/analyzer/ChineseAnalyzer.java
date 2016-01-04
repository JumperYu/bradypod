package bradypod.framework.analyzer;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class ChineseAnalyzer {

	public static void main(String[] args) throws IOException {

		String text = "有意见分歧";

		Analyzer analyzer = new SmartChineseAnalyzer();

		TokenStream stream = analyzer.tokenStream("", new StringReader(text));
		
		stream.reset();
		
		CharTermAttribute cta = stream.addAttribute(CharTermAttribute.class);
		
		while (stream.incrementToken()) {
			System.out.print("[" + cta + "]");
		}

		analyzer.close();

	}

}
