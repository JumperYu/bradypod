package com.bradypod.search.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/** Simple command-line based search demo. */
public class SearchFiles {

	private SearchFiles() {
	}

	/** Simple command-line based search demo. */
	public static void main(String[] argxs) throws Exception {
		String index = "D://index";
		String field = "filename";
		String queryString = "ClusterRedisFactory.java";
		int hitsPerPage = 5;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);

		QueryParser parser = new QueryParser(field, new StandardAnalyzer());

		Query query = parser.parse(queryString);
		System.out.println("Searching for: " + query.toString(field));

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
		ScoreDoc[] hits = results.scoreDocs;
		for (int i = 0; i < hits.length; i++) {
			if (raw) { // output raw format
				System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
				continue;
			}// -->end if

			// output
			Document doc = searcher.doc(hits[i].doc);
			List<IndexableField> fields = doc.getFields();
			for (IndexableField field : fields) {
				System.out.println((i+1) + "." + field.name() + ": " + field.stringValue());
			}// --> end for
		}// --> end for
	}
}
