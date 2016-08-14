package com.micmiu.bigdata.deserteagle.hbase.util;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
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

	public static Map<String, String> parseResult2Map(Result result) {
		return parseResult2Map(result, null);
	}

	public static Map<String, String> parseResult2Map(Result result, String rowkeyName) {
		if (null == result || result.isEmpty()) {
			return null;
		}
		Map<String, String> kvMap = new LinkedHashMap<String, String>();
		if (null != rowkeyName && !"".equals(rowkeyName)) {
			kvMap.put(rowkeyName, Bytes.toString(result.getRow()));
		}
		for (Cell cell : result.rawCells()) {
			String value = Bytes.toString(CellUtil.cloneValue(cell));
			if ("\"\"".equals(value)) {
				value = null;
			}
			kvMap.put(Bytes.toString(CellUtil.cloneQualifier(cell)), value);
		}
		return kvMap;
	}
}
