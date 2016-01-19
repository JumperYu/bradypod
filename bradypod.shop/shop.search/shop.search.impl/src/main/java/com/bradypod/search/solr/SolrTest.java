package com.bradypod.search.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

public class SolrTest {

	public static void main(String[] args) throws SolrServerException,
			IOException {
		index();
		query();
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
		QueryResponse response = solr.query(query);

		System.out.println(response.toString());
		
		solr.close();
	}

}
