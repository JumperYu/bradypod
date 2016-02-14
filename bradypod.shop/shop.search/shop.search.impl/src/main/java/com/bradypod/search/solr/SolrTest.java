package com.bradypod.search.solr;

import java.io.IOException;
import java.util.HashMap;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;

public class SolrTest {

	public static void main(String[] args) throws SolrServerException,
			IOException {
//		index();
		//query();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("a", "1");
		map.put("b", "2");
		map.put("c", "3");
		map.put("d", "4");
		NamedList<String> namedList = new NamedList<String>(map);
		System.out.println(namedList.get("b"));
		System.out.println(namedList.getName(1));
	}

	public static void index() throws SolrServerException, IOException {
		String urlString = "http://localhost:8983/solr/gettingstarted";
		SolrClient solr = new HttpSolrClient(urlString);

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "22");
		document.addField("name", "好吃的榴莲干");
		document.addField("price", "49.99");

		UpdateResponse response = solr.add(document);

		// Remember to commit your changes!

		solr.commit();

		System.out.println(response.getStatus());

		solr.close();
	}

	public static void query() throws SolrServerException, IOException {
		String urlString = "http://localhost:8983/solr/gettingstarted";
		SolrClient solr = new HttpSolrClient(urlString);

		SolrQuery query = new SolrQuery();
		query.set("q", "榴莲");
		query.set("wt", "json");
		QueryResponse response = solr.query(query);

		System.out.println(response.toString());
		
		solr.close();
	}

}
