package bradypod.framework.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.SearcherFactory;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * Lucene实时搜索
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年2月14日
 */
public class LuceneManager {

	private SearcherManager searcherManager; // 实时搜索管理

	private IndexWriter writer;

	private TrackingIndexWriter tkWriter;

	private ControlledRealTimeReopenThread<IndexSearcher> crtThread;

	private Analyzer analyzer;

	private static final double targetMaxStaleSec = 5.0;

	private static final double targetMinStaleSec = 0.025;

	public LuceneManager(String path) {
		try {
			Directory fsDir = FSDirectory.open(Paths.get(path));
			// 使用中文分词器
			analyzer = new SmartChineseAnalyzer();
			// 创建writer
			writer = new IndexWriter(fsDir, new IndexWriterConfig(analyzer));
			// true 表示在内存中删除，false可能删可能不删，设为false性能会更好一些
			searcherManager = new SearcherManager(writer, false,
					new SearcherFactory());
			// ControlledRealTimeReopenThread
			tkWriter = new TrackingIndexWriter(writer);// 为writer 包装了一层
			// 创建线程，线程安全的，我们不须处理
			crtThread = new ControlledRealTimeReopenThread<IndexSearcher>(
					tkWriter, searcherManager, targetMaxStaleSec,
					targetMinStaleSec);
			crtThread.setDaemon(true);// 设为后台线程
			crtThread.setName("lucene-realtime-reopen");
			crtThread.start();// 启动线程
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public <T> T search(LuceneCallback<T> callback) {
		IndexSearcher searcher = null;
		try {

			// 更新看看内存中索引是否有变化如果，有一个更新了，其他线程也会更新
			searcherManager.maybeRefresh();

			// 利用acquire 方法获取search，执行此方法前须执行maybeRefresh
			searcher = searcherManager.acquire();

			return callback.executeQuery(searcher);

		} catch (IOException e) {
		} finally {
			try {
				// 释放searcher，
				searcherManager.release(searcher);
			} catch (IOException e) {
			}
		}
		// need hack
		return null;
	}
	
	/**
	 * close writer
	 */
	public void closeWriter() {
		try {
			writer.close();
		} catch (IOException e) {
			// ignore
		}
	}

	// get/set

	public IndexWriter getWriter() {
		return writer;
	}

	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}

	public SearcherManager getSearcherManager() {
		return searcherManager;
	}

	public void setSearcherManager(SearcherManager searcherManager) {
		this.searcherManager = searcherManager;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

}
