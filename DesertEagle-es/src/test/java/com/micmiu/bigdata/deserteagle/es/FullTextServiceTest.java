package com.micmiu.bigdata.deserteagle.es;

import com.micmiu.bigdata.deserteagle.core.query.QueryFilter;
import com.micmiu.bigdata.deserteagle.es.client.EsTransportClientManager;
import com.micmiu.bigdata.deserteagle.es.service.EsFullTextIndexService;
import org.elasticsearch.client.transport.TransportClient;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 23:27
 */
public class FullTextServiceTest {

	public static void main(String[] args) {
		EsTransportClientManager clientManager = new EsTransportClientManager("192.168.0.40:9300,192.168.0.41:9300,192.168.0.42:9300");
		clientManager.setEsclusterName("EDH-4.3.4");//特殊字符有异常
		clientManager.setup();

		EsFullTextIndexService service = new EsFullTextIndexService();
		service.setClientManager(clientManager);

		System.out.println(service.queryCount("zj_log_api_ws"));

		System.out.println(service.queryCount("zj_log_api_ws", new QueryFilter("INFO:REQ_SERVICENAME", "term", "checkUpload")));

	}
}
