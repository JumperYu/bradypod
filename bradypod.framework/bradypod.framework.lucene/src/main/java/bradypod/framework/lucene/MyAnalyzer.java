package bradypod.framework.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

public final class MyAnalyzer extends Analyzer {

	public MyAnalyzer() {
	}

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		return new TokenStreamComponents(new Tokenizer() {
			
			@Override
			public boolean incrementToken() throws IOException {
				return false;
			}
		}, new TokenStream() {
			
			@Override
			public boolean incrementToken() throws IOException {
				return false;
			}
		});
	}
}