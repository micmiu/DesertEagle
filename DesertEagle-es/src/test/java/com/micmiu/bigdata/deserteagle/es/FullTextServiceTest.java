package com.micmiu.bigdata.deserteagle.es;

import com.micmiu.bigdata.deserteagle.core.model.QueryFilter;
import com.micmiu.bigdata.deserteagle.es.client.EsTransportClientManager;
import com.micmiu.bigdata.deserteagle.es.service.EsFullTextIndexService;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 23:27
 */
public class FullTextServiceTest {

	public static void main(String[] args) {
		EsTransportClientManager clientManager = new EsTransportClientManager("192.168.0.31:9300,192.168.0.32:9300,192.168.0.33:9300");
		clientManager.setup();
		EsFullTextIndexService service = new EsFullTextIndexService();
		service.setClientManager(clientManager);

		System.out.println(service.queryCount("nb_log_api_ws"));

		System.out.println(service.queryCount("nb_log_api_ws", new QueryFilter("INFO:REQ_SERVICENAME", "term", "checkUpload")));

	}
}
