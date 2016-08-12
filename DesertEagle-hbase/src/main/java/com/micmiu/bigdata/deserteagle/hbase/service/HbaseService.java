package com.micmiu.bigdata.deserteagle.hbase.service;

import com.micmiu.bigdata.deserteagle.hbase.entity.HbaseEntity;
import org.apache.hadoop.hbase.client.HTableInterface;

import java.util.List;
import java.util.Map;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 12/30/2015
 * Time: 22:54
 */
public interface HbaseService {

	String ROWKEY = "@ROW_KEY@";

	Boolean checkTable(String tableName);

	HTableInterface getTable(String tableName);

	List<Map<String, String>> queryDataByRowkeyList(String tableName, String familyName, List<String> rowkeyList);

	boolean putDataEntity(HbaseEntity entity);

	boolean putData(String tableName, String rowKey, String familyName, Map<String, String> dataMap);

	boolean putData(String tableName, String familyName, Map<String, String> dataMap);

	boolean putData(String tableName, String familyName, List<Map<String, String>> dataList);

	boolean deleteData(String tableName, String rowKey);

	boolean deleteData(String tableName, List<String> keyList);

	boolean deleteDataMap(String tableName, List<Map<String, String>> keyList);


}
