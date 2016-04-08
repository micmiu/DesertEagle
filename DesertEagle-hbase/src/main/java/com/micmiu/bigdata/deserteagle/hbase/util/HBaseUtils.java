package com.micmiu.bigdata.deserteagle.hbase.util;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 12/30/2015
 * Time: 22:58
 */
public class HBaseUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(HBaseUtils.class);

	/* 转换byte数组 */
	public static byte[] getBytes(String str) {
		if (str == null)
			str = "";
		return Bytes.toBytes(str);
	}

	public static List<Get> parseGetList(List<String> rowList, String familyName) {
		List<Get> list = new LinkedList<Get>();
		for (String row : rowList) {
			Get get = new Get(row.getBytes());
			get.addFamily(getBytes(familyName));
			list.add(get);
		}
		return list;
	}

	public static void closeHtable(HTableInterface htable) {
		try {
			if (null != htable) {
				htable.close();
			}
		} catch (Exception e) {
			LOGGER.error("close hbase table error:", e);
		}
	}

	public static String map2String(Map<String, String> dataMap) {
		StringBuffer sb = new StringBuffer("[");
		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			sb.append(entry.getKey() + " = " + entry.getValue());
		}
		return sb.append("]").toString();
	}
}
