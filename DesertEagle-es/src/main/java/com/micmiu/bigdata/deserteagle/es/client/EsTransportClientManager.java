package com.micmiu.bigdata.deserteagle.es.client;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 23:19
 */
public class EsTransportClientManager implements EsClientManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsTransportClientManager.class);

	private Client client;

	private String clusterAddress;

	private String esclusterName = "elasticsearch";

	private String indexPrefix = "elasticsearch";

	public EsTransportClientManager() {
	}

	public EsTransportClientManager(String clusterAddress) {
		this.clusterAddress = clusterAddress;
	}

	@Override
	public void destroy() {
		try {
			client.close();
		} catch (Exception e) {
			LOGGER.error("es client close error:", e);
		}

	}

	@Override
	public void setup() {
		LOGGER.info("start es client connect to clusterName={}, address={}", esclusterName, clusterAddress);
		String clusterName = (null == esclusterName || "".equals(esclusterName)) ? "elasticsearch" : esclusterName;
		Settings settings = Settings.settingsBuilder().put("cluster.name", clusterName).build();
		if (null == clusterAddress || "".equals(clusterAddress)) {
			LOGGER.warn("es clusterAddress must not empty.");
			return;
		}
		try {
			TransportClient transportClient = TransportClient.builder().settings(settings).build();
			for (String str : clusterAddress.split(",")) {
				String[] ipaddr = str.split(":");
				transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipaddr[0]), Integer.parseInt(ipaddr[1])));
			}
			LOGGER.info("es TransportClient listedNodes  size = " + transportClient.listedNodes().size());
			client = transportClient;
		} catch (Exception e) {
			LOGGER.warn("ES client setup error:", e);
			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	@Override
	public Client getClient() {
		return client;
	}

	@Override
	public String getIndexPrefix() {
		return indexPrefix;
	}

	public void setClusterAddress(String clusterAddress) {
		this.clusterAddress = clusterAddress;
	}

	public void setEsclusterName(String esclusterName) {
		this.esclusterName = esclusterName;
	}

	public void setIndexPrefix(String indexPrefix) {
		this.indexPrefix = indexPrefix;
	}
}
