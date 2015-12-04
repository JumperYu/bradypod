package test;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexManager {

	protected String[] ids = { "1", "2" };

	protected String[] content = { "Amsterdam has lost of add cancals", "i love add this girl" };

	protected String[] city = { "Amsterdam", "Venice" };

	private IndexWriter writer;

	private IndexReader reader;

	private Directory dir;

	private static final String INDEX_DIR = "D://index";

	@Before
	public void init() throws Exception {
		dir = FSDirectory.open(Paths.get(INDEX_DIR));
		writer = getWriter();
		reader = DirectoryReader.open(dir);
	}

	@After
	public void close() throws Exception {
		writer.close();
	}

	/**
	 * 文件索引
	 * 
	 * @throws Exception
	 */
	@Test
	public void initFileIndex() throws Exception {
		for (int i = 0; i < ids.length; i++) {
			Document doc = new Document();
			doc.add(new StringField("id", ids[i], Store.YES));
			doc.add(new TextField("content", content[i], Store.YES));
			doc.add(new StringField("city", city[i], Store.YES));
			writer.addDocument(doc);
		}
		System.err.println("init ok!");
		writer.close();
	}

	/**
	 * 内存索引
	 * 
	 * @throws IOException
	 */
	@Test
	public void initRAMIndex() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory dir = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(dir, config);
		// 写入数据
		addDoc(writer, "Lucene in Action", "193398817");
		addDoc(writer, "Lucene for Dummies", "55320055Z");
		addDoc(writer, "Managing Gigabytes", "55063554A");
		addDoc(writer, "The Art of Computer Science", "9900333X");
		writer.close();
	}

	/**
	 * 查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void search() throws Exception {
		String text = "add";
		String field = "content";
		IndexSearcher searcher = new IndexSearcher(reader);
		// 查询语句
		QueryParser parser = new QueryParser(field, new StandardAnalyzer());
		Query query = parser.parse(text);
		// 得到查询结果
		TopDocs topDocs = searcher.search(query, 1000);
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println("总共匹配多少个：" + topDocs.totalHits + "多少条数据：" + hits.length);
		// 应该与topDocs.totalHits相同
		for (ScoreDoc scoreDoc : hits) {
			System.out.println("匹配得分：" + scoreDoc.score);
			System.out.println("文档索引ID：" + scoreDoc.doc);
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println(document.get(field));
		}// --> end
	}

	@Test
	public void update() throws Exception {
		
	}

	/**
	 * 获得IndexWriter对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public IndexWriter getWriter() throws Exception {
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		return new IndexWriter(dir, iwc);
	}

	private static void addDoc(IndexWriter w, String title, String isbn) throws IOException {
		Document doc = new Document();
		doc.add(new TextField("title", title, Store.YES));
		doc.add(new StringField("isbn", isbn, Store.YES));
		w.addDocument(doc);
	}
}
