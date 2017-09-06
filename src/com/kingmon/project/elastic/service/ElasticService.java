package com.kingmon.project.elastic.service;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kingmon.base.common.KConstants;
import com.kingmon.project.elastic.util.ElasticTypes;

@Service
public class ElasticService {
	private TransportClient client;

	@Value("${elastic.url}")
	private String elasticUrl;

	@Value("${elastic.cluster.name}")
	private String clusterName;

	@Value("${elastic.index.name}")
	private String indexName;;

	@Value("${dev.data.process}")
	private String devDataProcess;

	@PostConstruct
	public void initClient() throws Throwable {
		/*
		 * prod Node node = nodeBuilder().clusterName("elastic_psam")
		 * .settings(ImmutableSettings.settingsBuilder().put("http.enabled",
		 * false) .put("discovery.zen.ping.unicast.hosts","192.168.201.192")
		 * .put("discovery.zen.ping.multicast.enabled",false)
		 * .put("node.name","psam_java") .put("network.host","202.194.67.109"))
		 * .client(true).node(); client = node.client();
		 

		// dev
		if (KConstants.DEV_DATA_PROCESS_DATABASE.equals(devDataProcess)) {
			return;
		}*/
		// pro
		client = new TransportClient(ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build());

		if (elasticUrl == null || elasticUrl.isEmpty()) {
			throw new RuntimeException("elastic.url 为空");
		}

		String[] ips = elasticUrl.split(",");
		for (String ip : ips) {
			client.addTransportAddress(new InetSocketTransportAddress(ip, 9300));
		}

	}

	public GetResponse get(ElasticTypes type, String id) {
		if(client ==null){
			return null;
		}
		GetResponse getResponse = client.prepareGet(indexName, type.getTypeName(), id).execute().actionGet();
		return getResponse;
	}

	public UpdateResponse updateDocument(ElasticTypes type, String id, Map<String, Object> fields) {
		if(client ==null){
			return null;
		}
		try {
			UpdateRequest updateRequest = new UpdateRequest();
			updateRequest.index(indexName);
			updateRequest.type(type.getTypeName());
			updateRequest.id(id);

			XContentBuilder doc = jsonBuilder().startObject();
			Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				doc.field(entry.getKey(), entry.getValue());
			}
			updateRequest.doc(doc.endObject());
			return client.update(updateRequest).get();
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}

	}

	public DeleteResponse deleteDocument(ElasticTypes type, String id) {
		if(client ==null){
			return null;
		}
		return client.prepareDelete(indexName, type.getTypeName(), id).execute().actionGet();
	}

	/**
	 * @param type
	 * @param id
	 *            主键
	 * @param fields
	 * @return
	 * @throws Throwable
	 */
	public IndexResponse indexDocument(ElasticTypes type, String id, Map<String, Object> fields) {
		if(client ==null){
			return null;
		}
		try {
			IndexRequest indexRequest = new IndexRequest(indexName, type.getTypeName(), id);
			XContentBuilder doc = jsonBuilder().startObject();
			Iterator<Entry<String, Object>> it = fields.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				doc.field(entry.getKey(), entry.getValue());
			}
			indexRequest.source(doc);
			return client.index(indexRequest).actionGet();
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public Client getClient() {
		return client;

	}

	@PreDestroy
	public void closeClient() {
		if (client != null) {
			client.close();
		}
	}
	
	
	
}
