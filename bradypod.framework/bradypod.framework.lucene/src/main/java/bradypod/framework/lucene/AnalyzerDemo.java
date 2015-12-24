package bradypod.framework.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

public class AnalyzerDemo {

	public static void standardAnalyzer() throws IOException, ParseException {
		Analyzer analyzer = new Analyzer() {
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				Tokenizer source = new WhitespaceTokenizer();
				TokenStream result = new LengthFilter(source, 4, Integer.MAX_VALUE);
				return new TokenStreamComponents(source);
			}
		};// new StandardAnalyzer();

		// Store the index in memory:
		Directory directory = new RAMDirectory();
		// To store an index on disk, use this instead:
		// Directory directory = FSDirectory.open("/tmp/testindex");
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter iwriter = new IndexWriter(directory, config);
		Document doc1 = new Document();
		Document doc2 = new Document();
		String text1 = "This is the text1 to be indexed.";
		String text2 = "This is the textt2 to be indexed.";
		doc1.add(new Field("fieldname", text1, TextField.TYPE_STORED));
		doc2.add(new Field("fieldname", text2, TextField.TYPE_STORED));
		iwriter.addDocument(doc1);
		iwriter.addDocument(doc2);
		iwriter.close();

		// Now search the index:
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		// Parse a simple query that searches for "text":
		QueryParser parser = new QueryParser("fieldname", analyzer);
		Query query = parser.parse("text");
		ScoreDoc[] hits = isearcher.search(query, 1000).scoreDocs;
		// Iterate through the results:
		for (int i = 0; i < hits.length; i++) {
			Document hitDoc = isearcher.doc(hits[i].doc);
			System.out.println(hitDoc.get("fieldname"));
		}
		ireader.close();
		directory.close();
	}

	public static void test() throws IOException {
		Analyzer analyzer = new Analyzer() {
			@Override
			protected TokenStreamComponents createComponents(String fieldName) {
				Tokenizer source = new WhitespaceTokenizer();
				TokenStream result = new LengthFilter(source, 4, Integer.MAX_VALUE);
				return new TokenStreamComponents(source);
			}
		}; // or any other
		// analyzer
		TokenStream ts = analyzer.tokenStream("myfield", new StringReader("some text goes here"));
		// The Analyzer class will construct the Tokenizer, TokenFilter(s), and
		// CharFilter(s),
		// and pass the resulting Reader to the Tokenizer.
		OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);

		try {
			ts.reset(); // Resets this stream to the beginning. (Required)
			while (ts.incrementToken()) {
				// Use AttributeSource.reflectAsString(boolean)
				// for token stream debugging.
				System.out.println("token: " + ts.reflectAsString(true));

				System.out.println("token start offset: " + offsetAtt.startOffset());
				System.out.println("  token end offset: " + offsetAtt.endOffset());
			}
			ts.end(); // Perform end-of-stream operations, e.g. set the final
			// offset.
		} finally {
			ts.close(); // Release resources associated with this stream.
			analyzer.close();
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
//		standardAnalyzer();
		test();
	}

}
