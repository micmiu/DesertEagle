package com.micmiu.bigdata.deserteagle.es.client;

import org.elasticsearch.client.Client;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 23:16
 */
public interface EsClientManager {

	void setup();

	void destroy();

	String getIndexPrefix();

	Client getClient();
}
