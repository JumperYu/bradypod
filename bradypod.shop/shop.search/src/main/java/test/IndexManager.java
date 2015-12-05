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
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexManager {

	protected String[] ids = { "1", "2", "3", "4", "5"};

	protected String[] content = { "Amsterdam love candlers", "i love add this girl", "i love add this child", "i love add this woman", "i love add my wife"};

	protected String[] city = { "GuangZhou", "MeiZhou", "ZhanJiang", "MaoMin", "ZhaoQing"};

	private IndexWriter writer;

	private IndexReader reader;

	private Directory dir;

	private static final String INDEX_DIR = "D://test_index";

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
		for (int i = 0; i < city.length; i++) {
			Document document = new Document();
			document.add(new StringField("id", ids[i], Store.YES));
			document.add(new StringField("city", city[i], Store.YES));
			writer.addDocument(document);
		}
		IndexReader reader = DirectoryReader.open(writer.getDirectory());
		IndexSearcher searcher = new IndexSearcher(reader);
		Query query = new TermQuery(new Term("city", "guangzhou"));
		TopDocs docs = searcher.search(query, 1);
		System.out.println("hits:" + docs.totalHits);
		writer.close();
		reader.close();
	}

	/**
	 * 查询
	 * 
	 * @throws Exception
	 */
	@Test
	public void search() throws Exception {
		String text = "love";
		String field = "content";
		IndexSearcher searcher = new IndexSearcher(reader);
		// 查询语句
		QueryParser parser = new QueryParser(field, new StandardAnalyzer());
		Query query = parser.parse(text);
		// 得到查询结果
		TopDocs topDocs = searcher.search(query, 1);
		ScoreDoc[] hits = topDocs.scoreDocs;
		System.out.println("总共匹配多少个：" + topDocs.totalHits + "多少条数据：" + hits.length);
		// 应该与topDocs.totalHits相同
		for (ScoreDoc scoreDoc : hits) {
			System.out.println("匹配得分：" + scoreDoc.score);
			System.out.println("文档索引ID：" + scoreDoc.doc);
			Document document = searcher.doc(scoreDoc.doc);
			System.out.println(document.get(field));
		}// --> end
//		writer.deleteDocuments(query);
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

}
