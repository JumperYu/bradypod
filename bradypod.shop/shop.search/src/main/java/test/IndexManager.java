package test;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.surround.parser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

public class IndexManager {

	protected String[] ids = { "1", "2" };

	protected String[] content = { "Amsterdam has lost of add  cancals", "i love  add this girl" };

	protected String[] city = { "Amsterdam", "Venice" };

	private Directory dir;

	/**
	 * 文件索引
	 * 
	 * @throws Exception
	 */
	@Test
	public void initFileIndex() throws Exception {
		String pathFile = "D://index";
		dir = FSDirectory.open(Paths.get(pathFile));
		IndexWriter writer = getWriter();
		for (int i = 0; i < ids.length; i++) {
			Document doc = new Document();
			doc.add(new StringField("id", ids[i], Store.YES));
			doc.add(new TextField("content", content[i], Store.YES));
			doc.add(new StringField("city", city[i], Store.YES));
			writer.addDocument(doc);
		}
		System.out.println("init ok?");
		writer.close();
	}

	@Test
	public void initRAMIndex() throws IOException {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		Directory directory = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(directory, config);
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
		String querystr = "lucene";
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
