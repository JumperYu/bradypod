package com.bradypod.search.lucene.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;

import bradypod.framework.lucene.LuceneUtils;

import com.bradypod.bean.bo.PageData;
import com.bradypod.search.lucene.bo.ItemInfoIndex;
import com.bradypod.shop.item.center.po.ItemInfo;
import com.bradypod.util.array.ArrayUtil;

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
		document.add(new LongField("ctgId", itemIndex.getCtgId(), Field.Store.YES));
		document.add(new TextField("title", itemIndex.getTitle(), Field.Store.YES));
		// 只做评分和排序
		document.add(new NumericDocValuesField("createTime", itemIndex.getCreateTime().getTime()));
		LuceneUtils.addIndex(writer, document);
	}

	/**
	 * 搜索索引
	 * 
	 * @param itemIndex
	 */
	public PageData<ItemInfo> searchIndex(ItemInfoIndex itemIndex) {

		if (reader == null) {
			reader = LuceneUtils.getIndexReader(directory);
			searcher = LuceneUtils.getIndexSearcher(reader);
		}

		PageData<ItemInfo> pageData = new PageData<ItemInfo>();

		String queryField = "title";
		QueryParser queryParser = new QueryParser(queryField, analyzer);

		try {

			TokenStream ts = analyzer.tokenStream("", new StringReader(itemIndex.getTitle()));
			ts.reset();
			CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
			Set<String> words = new HashSet<String>();
			while (ts.incrementToken()) {
				words.add("+" + cta.toString());
			}
			String queryValue = ArrayUtil.join(words, " ");

			System.out.println("slit word: " + queryValue);
			ts.close();

			Query query = queryParser.parse(queryValue);
			
			// 排序true正序, false和默认是倒序
			Sort sort = new Sort();
			if (StringUtils.isNotBlank(itemIndex.getSortField())) {
				sort.setSort(new SortField(itemIndex.getSortField(), Type.LONG, itemIndex
						.isDescending()));
			}// --> end if sort

			TopDocs results = searcher.search(query, itemIndex.getPageSize(), sort);
			
			ScoreDoc[] scores = results.scoreDocs;// 这里是全部的集合
			
			int begin = itemIndex.getPageSize() * (itemIndex.getPageNO() - 1);
			int end = Math.min(begin + itemIndex.getPageSize(), scores.length);

			pageData.setPageNO(itemIndex.getPageNO());
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

		} catch (Exception e) {
			e.printStackTrace();
		}

		return pageData;
	}

	/**
	 * 模糊查询
	 * 
	 * @param itemIndex
	 */
	public void fuzzySearch(ItemInfoIndex itemIndex) {

		if (reader == null) {
			reader = LuceneUtils.getIndexReader(directory);
			searcher = LuceneUtils.getIndexSearcher(reader);
		}

		String queryField = "title";
		Term term = new Term(queryField, itemIndex.getTitle());
		// 在FuzzyQuery中，默认的匹配度是0.5，当这个值越小时，通过模糊查找出的文档的匹配程度就
		// 越低，查出的文档量就越多,反之亦然
		FuzzyQuery query = new FuzzyQuery(term);
		try {
			TopDocs results = searcher.search(query, itemIndex.getPageSize());
			ScoreDoc[] scores = results.scoreDocs;
			for (int i = 0; i < scores.length; i++) {
				// output
				Document doc = searcher.doc(scores[i].doc);
				System.out.println(doc.get(queryField));
			}// --> end for
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通配符查询
	 * 
	 * @param itemIndex
	 */
	public void wildcardSearch(ItemInfoIndex itemIndex) {

		if (reader == null) {
			reader = LuceneUtils.getIndexReader(directory);
			searcher = LuceneUtils.getIndexSearcher(reader);
		}

		String queryField = "title";
		Term term = new Term(queryField, itemIndex.getTitle());
		WildcardQuery query = new WildcardQuery(term);
		try {
			TopDocs results = searcher.search(query, itemIndex.getPageSize());
			ScoreDoc[] scores = results.scoreDocs;
			for (int i = 0; i < scores.length; i++) {
				// output
				Document doc = searcher.doc(scores[i].doc);
				System.out.println(doc.get(queryField));
			}// --> end for
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		LuceneUtils.closeIndexWriter(writer);
	}

	static final String INDEX_PATH = "E://index";
}
