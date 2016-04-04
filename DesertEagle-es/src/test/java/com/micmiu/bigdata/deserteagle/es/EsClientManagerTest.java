package com.micmiu.bigdata.deserteagle.es;

import com.micmiu.bigdata.deserteagle.es.client.EsTransportClientManager;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 23:26
 */
public class EsClientManagerTest {

	public static void main(String[] args) {

		EsTransportClientManager clientManager = new EsTransportClientManager();
		try {
			clientManager.setClusterAddress("192.168.0.31:9300,192.168.0.32:9300,192.168.0.33:9300");
			clientManager.setup();
			System.out.println(clientManager.getClient().toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			clientManager.destroy();
		}

	}
}
