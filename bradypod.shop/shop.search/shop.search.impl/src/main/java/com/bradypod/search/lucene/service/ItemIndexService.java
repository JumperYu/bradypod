package com.bradypod.search.lucene.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;

import bradypod.framework.lucene.LuceneCallback;
import bradypod.framework.lucene.LuceneManager;

import com.bradypod.bean.bo.PageData;
import com.bradypod.search.lucene.bo.ItemIndex;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.util.array.ArrayUtil;

/**
 * 索引服务
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月28日 下午5:28:47
 */
public class ItemIndexService {

	private LuceneManager luceneManager;

	public ItemIndexService() {
		luceneManager = new LuceneManager(INDEX_PATH);
	}

	/**
	 * 创建索引
	 * 
	 * @param itemIndex
	 * @throws IOException
	 */
	public void createIndex(ItemIndex itemIndex) {
		IndexWriter writer = luceneManager.getWriter();
		Document document = new Document();
		document.add(new LongField("id", itemIndex.getId(), Field.Store.YES));
		document.add(new LongField("ctgId", itemIndex.getCtgId(),
				Field.Store.YES));
		document.add(new TextField("title", itemIndex.getTitle(),
				Field.Store.YES));
		// 只做评分和排序
		document.add(new NumericDocValuesField("createTime", itemIndex
				.getCreateTime().getTime()));
		// 添加并且提交
		try {
			writer.addDocument(document);
			writer.commit();
		} catch (IOException e) {
		}
	}

	/**
	 * 搜索索引
	 * 
	 * @param itemIndex
	 */
	public PageData<ItemInfo> searchIndex(ItemIndex itemIndex) {

		PageData<ItemInfo> pageData = new PageData<ItemInfo>();

		String queryField = "title";

		Analyzer analyzer = luceneManager.getAnalyzer();

		QueryParser queryParser = new QueryParser(queryField, analyzer);

		try {

			TokenStream ts = analyzer.tokenStream("", new StringReader(
					itemIndex.getTitle()));
			ts.reset(); // required set to begin
			CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
			Set<String> words = new HashSet<String>();
			while (ts.incrementToken()) {
				words.add("+" + cta.toString());
			}
			String queryValue = ArrayUtil.join(words, " ");

			ts.end(); // required set to final

			ts.close();

			Query query = queryParser.parse(queryValue);

			// 排序true正序, false和默认是倒序
			Sort sort = new Sort();
			sort.setSort(new SortField(itemIndex.getSortField(), Type.LONG,
					itemIndex.isDescending()));

			// 内部获取search对象进行实时查询
			luceneManager.search(new LuceneCallback<Void>() {
				@Override
				public Void executeQuery(IndexSearcher searcher) {

					TopDocs results;
					try {
						results = searcher.search(
								query,
								itemIndex.getPageSize() * itemIndex.getPageNO(),
								sort);

						ScoreDoc[] scores = results.scoreDocs;// 这里是全部的集合

						int begin = itemIndex.getPageSize()
								* (itemIndex.getPageNO() - 1);
						int end = Math.min(begin + itemIndex.getPageSize(),
								scores.length);

						pageData.setPageSize(itemIndex.getPageSize());
						pageData.setTotalNum(results.totalHits);

						List<ItemInfo> itemInfos = new ArrayList<ItemInfo>();

						for (int i = begin; i < end; i++) {

							// output
							Document doc = searcher.doc(scores[i].doc);
							ItemInfo itemInfo = new ItemInfo();
							itemInfo.setId(Long.parseLong(doc.get("id")));
							itemInfo.setTitle(doc.get("title"));
							itemInfos.add(itemInfo);

						}// --> end for

						pageData.setList(itemInfos);
					} catch (IOException e) {
					}

					return null;
				}
			});

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pageData;
	}

	// close
	public void close() {
		luceneManager.closeWriter();
	}

	private static final String INDEX_PATH = "E://index";
}
