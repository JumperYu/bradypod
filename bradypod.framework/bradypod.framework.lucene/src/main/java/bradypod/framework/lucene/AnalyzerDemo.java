package bradypod.framework.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
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
import org.junit.Test;

public class AnalyzerDemo {

	@Test
	public void initIndex() throws IOException, ParseException {
		Analyzer analyzer = new SmartChineseAnalyzer();

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

	@Test
	public void testTokenNizer() throws IOException {

		// analyzer
		try (Analyzer analyzer = new SmartChineseAnalyzer();
				TokenStream ts = analyzer.tokenStream("myfield", new StringReader(
						"今天是周五，明天不上班。"))) {
			// 获取词元位置属性
			OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 获取词元文本属性
			TypeAttribute type = ts.addAttribute(TypeAttribute.class);

			// 重置TokenStream（重置StringReader）
			ts.reset();
			// 迭代获取分词结果
			while (ts.incrementToken()) {
				System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : "
						+ term.toString() + " | " + type.type());
			}
			// 关闭TokenStream（关闭StringReader）
			ts.end(); // Perform end-of-stream operations, e.g. set the final
						// offset.
		}
	}

}
