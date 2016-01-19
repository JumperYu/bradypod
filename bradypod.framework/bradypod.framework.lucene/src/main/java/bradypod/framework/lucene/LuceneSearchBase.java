package bradypod.framework.lucene;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public abstract class LuceneSearchBase {
	
	protected IndexWriter writer;
	protected Analyzer analyzer;

	protected Version version = Version.LATEST;
	protected OpenMode openMode = OpenMode.CREATE_OR_APPEND;

	protected ControlledRealTimeReopenThread<IndexSearcher> realTimeReopenThread;
	protected TrackingIndexWriter trackingIndexWriter;
	
	protected SearcherManager searcherManager;

	protected Directory directory;

	protected SimpleHTMLFormatter formatter;

	protected Highlighter highlighter;

	protected IndexWriterConfig indexWriterConfig;

	protected String threadName = "Index-NRT-Thread";

	protected double targetMaxStaleSec = 5;

	protected double targetMinStaleSec = 0.025;

	public LuceneSearchBase() {

	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	public void setDirectory(Path indexPath) throws IOException {
		this.directory = FSDirectory.open(indexPath);
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void setIndexWriterConfig(IndexWriterConfig indexWriterConfig) {
		this.indexWriterConfig = indexWriterConfig;
	}

	public void setOpenMode(OpenMode openMode) {
		this.openMode = openMode;
	}

	public void setSimpleHTMLFormatter(SimpleHTMLFormatter formatter) {
		this.formatter = formatter;
	}

	public void setRealTimeReopenThreadName(String name) {
		this.threadName = name;
	}

	public void setRealTimeReopenThreadStaleSec(double targetMaxStaleSec,
			double targetMinStaleSec) {
		this.targetMaxStaleSec = targetMaxStaleSec;
		this.targetMinStaleSec = targetMinStaleSec;
	}

	/**
	 * 各种 set 设置之后再调用此方法，表示完成初始化
	 * 
	 * @throws IOException
	 */
	public void endConfig() throws IOException {
		if (this.directory == null) {
			throw new IOException("没有设置索引文件路径");
		}

		if (this.analyzer == null) {
			this.analyzer = new SmartChineseAnalyzer();
		}

		if (this.indexWriterConfig == null) {
			indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(openMode);
		}

		if (this.formatter == null) {
			formatter = new SimpleHTMLFormatter("<span class='highlight'>",
					"</span>");
		}

		writer = new IndexWriter(directory, indexWriterConfig);

		trackingIndexWriter = new TrackingIndexWriter(writer);
		searcherManager = new SearcherManager(writer, true,
				new SearcherFactory());

		// 启动一个守护线程用来检测索引变化
		realTimeReopenThread = new ControlledRealTimeReopenThread<>(
				trackingIndexWriter, searcherManager, this.targetMaxStaleSec,
				this.targetMinStaleSec);
		realTimeReopenThread.setDaemon(true);
		realTimeReopenThread.setName(this.threadName);
		realTimeReopenThread.start();
	}

	/**
	 * 如果不设置上述的那些 set 属性，则可以直接调用此方法设置默认属性。
	 * 
	 * @param indexPath
	 * @throws IOException
	 */
	public void defaultConfig(Path indexPath) throws IOException {
		setDirectory(indexPath);

		endConfig();
	}

	/**
	 * 用 TrackingIndexWriter 来 add、update、delete Document。
	 * 
	 * @return
	 */
	public TrackingIndexWriter getTrackingIndexWriter() {
		return this.trackingIndexWriter;
	}

	public IndexWriter getIndexWriter() {
		return this.writer;
	}

	/**
	 * 将给定字段内容转换成带高亮信息的字符串，方便网页中使用。
	 * 
	 * @param field
	 * @param value
	 * @return
	 */
	public String getBestFragment(String field, String value) {
		if (value == null) {
			return value;
		}

		try {
			return this.highlighter.getBestFragment(analyzer, field, value);
		} catch (IOException | InvalidTokenOffsetsException e) {
			return value;
		}
	}

	/**
	 * 手工调用提交。 还有一种方式是创建个线程专门定时干这事，不过没啥必要。
	 */
	public void commit() {
		try {
			this.writer.commit();
		} catch (IOException e) {
		}
	}

	/**
	 * 清除所有索引文档。
	 * 
	 * @throws IOException
	 */
	public void deleteAllDocument() throws IOException {
		this.trackingIndexWriter.deleteAll();
	}

	/**
	 * 由于采用了单例模式，在服务器环境下这个 close 需要在 {@link
	 * com.someok.module.book.web.listener.StartupListener.contextDestroyed(
	 * ServletContextEvent)} 下调用
	 */
	public void close() {

/*		IOUtils.closeQuietly(realTimeReopenThread);
		IOUtils.closeQuietly(searcherManager);

		IOUtils.closeQuietly(writer);
		IOUtils.closeQuietly(analyzer);
		IOUtils.closeQuietly(directory);*/

	}

	/**
	 * 单字段查询
	 * 
	 * @param field
	 *            查询的字段
	 * @param q
	 *            查询字符串
	 * @param pageNum
	 *            页数，从 1 开始
	 * @param limit
	 *            每页记录数
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public void query(String field, String q, int pageNum,
			int limit) {
	}

	/**
	 * 采用 SmartChineseAnalyzer 解析查询字符串
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> analyzerCnStr(String str) {
		List<String> result = new ArrayList<String>();
		Analyzer analyzer = new SmartChineseAnalyzer(true);
		try {

			TokenStream tokenStream = analyzer.tokenStream("field", str);
			CharTermAttribute term = tokenStream
					.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				result.add(term.toString());
			}
			tokenStream.end();
			tokenStream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			analyzer.close();
		}
		return result;
	}

}
