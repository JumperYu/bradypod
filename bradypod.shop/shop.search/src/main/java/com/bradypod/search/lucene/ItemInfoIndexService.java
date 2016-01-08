package com.bradypod.search.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import bradypod.framework.lucene.LuceneUtils;

/**
 * 索引服务
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月28日 下午5:28:47
 */
public class ItemInfoIndexService {

	private Directory directory;
	private Analyzer analyzer;
	private IndexWriter writer;
	private IndexReader reader;
	private IndexSearcher searcher;

	public ItemInfoIndexService() {
		directory = LuceneUtils.openFSDirectory(INDEX_PATH);

		analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);

		writer = LuceneUtils.getIndexWrtier(directory, config);
	}

	/**
	 * 创建索引
	 * 
	 * @param itemIndex
	 * @throws IOException
	 */
	public void createIndex(ItemInfoIndex itemIndex) {
		Document document = new Document();
		document.add(new LongField("id", itemIndex.getId(), Field.Store.YES));
		document.add(new LongField("userId", itemIndex.getUserId(),
				Field.Store.YES));
		document.add(new IntField("itemType", itemIndex.getItemType(),
				Field.Store.YES));
		document.add(new LongField("ctgId", itemIndex.getCtgId(),
				Field.Store.YES));
		document.add(new TextField("title", itemIndex.getTitle(),
				Field.Store.YES));
		document.add(new LongField("createTime", itemIndex.getCreateTime()
				.getTime(), Field.Store.YES));
		LuceneUtils.addIndex(writer, document);
	}

	/**
	 * 搜索索引
	 * 
	 * @param itemIndex
	 */
	public void searchIndex(ItemInfoIndex itemIndex) {
		if (reader == null) {
			reader = LuceneUtils.getIndexReader(directory);
			searcher = LuceneUtils.getIndexSearcher(reader);
		}

		QueryParser queryParser = new QueryParser("title", analyzer);
		try {
			Query query = queryParser.parse(itemIndex.getTitle());
			TopDocs results = searcher.search(query, 5);
			ScoreDoc[] scores = results.scoreDocs;

			for (int i = 0; i < scores.length; i++) {

				// output
				Document doc = searcher.doc(scores[i].doc);
				List<IndexableField> fields = doc.getFields();
				for (IndexableField field : fields) {
					// Explanation explanation = searcher.explain(query,
					// scores[i].doc);
					System.out.println((i + 1) + ".\t" + field.name() + ":\t"
							+ field.stringValue());
					// 打印解释器
					// System.out.println("explain:" + explanation.toString());
				}// --> end for

			}// --> end for
			System.out.println("find out docs:" + results.totalHits);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		LuceneUtils.closeIndexWriter(writer);
	}

	static final String INDEX_PATH = "E://index";
}
