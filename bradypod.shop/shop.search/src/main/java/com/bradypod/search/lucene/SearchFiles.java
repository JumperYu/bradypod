package com.bradypod.search.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.FSDirectory;

/**
 * Lucene搜索
 *
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2015年12月9日 下午5:23:15
 */
public class SearchFiles {

	public static void main(String[] argxs) throws Exception {
		// 指定索引目录和需要搜索的域
		String index = "D://index";
		String field = "filename";
		String queryString = "htl";
		int hitsPerPage = 10000; // 页大小

		// 创建索引读取器
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));

		IndexSearcher searcher = new IndexSearcher(reader);
		// 分词器
		// StandardAnalyzer analyzer = new StandardAnalyzer();
		// SimpleAnalyzer analyzer = new SimpleAnalyzer();
		// 查询语句
		// QueryParser parser = new QueryParser(field, analyzer);
		// Query query = parser.parse(queryString);
		// 范围查询
		// TermRangeQuery query = new TermRangeQuery(field, new
		// BytesRef("a".getBytes()),
		// new BytesRef("c".getBytes()), true, true);
		// 正则查询
		//WildcardQuery query = new WildcardQuery(new Term(field, queryString));
		// 模糊查询
		//FuzzyQuery query = new FuzzyQuery(new Term(field, queryString));
		Query query = new MatchAllDocsQuery();
		
		System.out.println(String.format("looking for field %s is %s", query.toString(field),
				queryString));

		doPagingSearch(searcher, query, hitsPerPage, false, true);

		reader.close();

	}

	/**
	 * 分页查询
	 * 
	 * @param searcher
	 * @param query
	 * @param hitsPerPage
	 * @param raw
	 * @param interactive
	 * @throws IOException
	 */
	public static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage,
			boolean raw, boolean interactive) throws IOException {
		// 查询指定页数
		TopDocs results = searcher.search(query, hitsPerPage);
		ScoreDoc[] scores = results.scoreDocs;
		for (int i = 0; i < scores.length; i++) {

			if (raw) { // output raw format
				System.out.println("doc=" + scores[i].doc + " score=" + scores[i].score);
				continue;
			}// -->end if

			// output
			Document doc = searcher.doc(scores[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField field : fields) {
				// Explanation explanation = searcher.explain(query,
				// scores[i].doc);
				System.out.println((i + 1) + "." + field.name() + ": " + field.stringValue());
				// 打印解释器
				// System.out.println("explain:" + explanation.toString());
			}// --> end for

		}// --> end for
		System.out.println("find out docs:" + results.totalHits);
	}
}
