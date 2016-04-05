package com.micmiu.bigdata.deserteagle.hbase.client;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.HConnection;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/5/2016
 * Time: 08:47
 */
public interface HbaseConnectionManager {

	String DEF_ZNODE = "/hbase";

	void setup();

	Configuration getConfig();

	HConnection getConn();

	void destroy();

}
