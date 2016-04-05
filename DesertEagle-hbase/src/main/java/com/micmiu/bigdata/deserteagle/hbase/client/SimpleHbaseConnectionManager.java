package com.micmiu.bigdata.deserteagle.hbase.client;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 7/8/2015
 * Time: 08:17
 */
public class SimpleHbaseConnectionManager implements HbaseConnectionManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHbaseConnectionManager.class);

	private String quorum;

	private int port;

	private String znode = DEF_ZNODE;

	private Configuration config;

	private HConnection conn;

	public SimpleHbaseConnectionManager() {
		this.config = HBaseConfiguration.create();
	}

	public SimpleHbaseConnectionManager(Configuration config) {
		this.config = config;
	}

	public SimpleHbaseConnectionManager(String quorum, int port) {
		//默认为 : /hbase  EDH: /hyperbase1
		this(quorum, port, DEF_ZNODE);
	}


	public SimpleHbaseConnectionManager(String quorum, int port, String znode) {
		this.quorum = quorum;
		this.port = port;
		this.znode = znode;
	}

	public synchronized HConnection getConn() {
		if (null == conn) {
			try {
				this.conn = HConnectionManager.createConnection(config);
			} catch (Exception ex) {
				LOGGER.error("create conn err:", ex);
			}
		}
		return conn;
	}


	public void destroy() {
		if (null != conn) {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void setup() {
		if (null == config) {
			Configuration conf = HBaseConfiguration.create();
			conf.set("hbase.zookeeper.property.clientPort", port + "");
			conf.set("hbase.zookeeper.quorum", quorum);
			conf.set("zookeeper.znode.parent", znode);
			config = conf;
		}
		try {
			this.conn = HConnectionManager.createConnection(config);
		} catch (Exception ex) {
			LOGGER.error("create conn err:", ex);
		}
		LOGGER.info("hbase setup success.");
	}

	public Configuration getConfig() {
		return config;
	}

}
