package com.micmiu.bigdata.deserteagle.hbase.service;

import com.micmiu.bigdata.deserteagle.hbase.client.HbaseConnectionManager;
import com.micmiu.bigdata.deserteagle.hbase.entity.HbaseEntity;
import com.micmiu.bigdata.deserteagle.hbase.util.HBaseUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 12/30/2015
 * Time: 23:07
 */
public class HbaseServiceImpl implements HbaseService {

	private static final Logger LOGGER = LoggerFactory.getLogger(HbaseServiceImpl.class);

	private HbaseConnectionManager hbaseConnectionManager;

	public Boolean checkTable(String tableName) {
		if (null == tableName || "".equals(tableName)) {
			return false;
		}
		try {
			HBaseAdmin admin = new HBaseAdmin(hbaseConnectionManager.getConn());
			return admin.tableExists(tableName);
		} catch (Exception e) {
			LOGGER.error("check tableName=" + tableName + " error:", e);
			return false;
		}
	}

	@Override
	public HTableInterface getTable(String tableName) {
		if (null == tableName || "".equals(tableName)) {
			return null;
		}
		try {
			return hbaseConnectionManager.getConn().getTable(HBaseUtils.getBytes(tableName));
		} catch (Exception e) {
			LOGGER.error("get tableName=" + tableName + " error:", e);
			return null;
		}
	}

	public List<Map<String, String>> queryDataByRowkeyList(String tableName, String familyName, List<String> rowkeyList) {
		List<Map<String, String>> dataResult = new ArrayList<Map<String, String>>();
		HTableInterface htable = getTable(tableName);
		List<Get> getList = HBaseUtils.parseGetList(rowkeyList, familyName);
		try {
			Result[] results = htable.get(getList);
			for (Result result : results) {
				Map<String, String> kvMap = new LinkedHashMap<String, String>();
				kvMap.put(HbaseEntity.ROWKEY, new String(result.getRow()));
				for (Cell cell : result.rawCells()) {
					kvMap.put(new String(CellUtil.cloneFamily(cell)) + ":" + new String(CellUtil.cloneQualifier(cell)), new String(CellUtil.cloneValue(cell)));
				}
				dataResult.add(kvMap);
			}
		} catch (Exception e) {
			LOGGER.error("hbase get error:", e);
		} finally {
			HBaseUtils.closeHtable(htable);
		}

		return dataResult;
	}

	@Override
	public List<Map<String, String>> queryDataListLimit(String tableName, String familyName, int limit) {
		List<Map<String, String>> dataResult = new ArrayList<Map<String, String>>();
		try {
			HTableInterface table = getTable(tableName);
			Scan scan = new Scan();
			scan.addFamily(HBaseUtils.getBytes(familyName));
			scan.setMaxResultSize(limit);
			ResultScanner rs = table.getScanner(scan);
			for (Result result : rs) {
				Map<String, String> kvmap = HBaseUtils.parseResult2Map(result, HbaseEntity.ROWKEY);
				if (null != kvmap && !kvmap.isEmpty()) {
					dataResult.add(kvmap);
				}
			}
			return dataResult;
		} catch (Exception e) {
			LOGGER.error("getData error:", e);
			return null;
		}
	}

	@Override
	public Map<String, String> getDataByKey(String tableName, String rowkey, String familyName) {
		try {
			HTableInterface table = getTable(tableName);
			Get get = new Get(rowkey.getBytes());
			get.addFamily(HBaseUtils.getBytes(familyName));
			Result result = table.get(get);
			return HBaseUtils.parseResult2Map(result, HbaseEntity.ROWKEY);
		} catch (Exception e) {
			LOGGER.error("getData error:", e);
			return null;
		}

	}

	@Override
	public boolean putDataEntity(HbaseEntity entity) {
		HTableInterface htable = null;
		try {
			htable = getTable(entity.getTableName());
			String familyName = entity.getFamilyName();
			String rowKey = entity.getRowkey();
			Put put = new Put(Bytes.toBytes(rowKey));
			for (Map.Entry<String, Object> ent : entity.getQualifierValueMap().entrySet()) {
				if (null == ent.getValue()) {
					put.add(Bytes.toBytes(familyName), Bytes.toBytes(ent.getKey()), null);
				} else {
					put.add(Bytes.toBytes(familyName), Bytes.toBytes(ent.getKey()), Bytes.toBytes(ent.getValue() + ""));
				}
			}
			htable.put(put);
			return true;
		} catch (Exception e) {
			LOGGER.error("hbase put entity error:" + entity, e);
			return false;
		} finally {
			HBaseUtils.closeHtable(htable);
		}
	}

	@Override
	public boolean putData(String tableName, String rowKey, String familyName, Map<String, String> dataMap) {
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		dataList.add(dataMap);
		return this.putData(tableName, familyName, dataList);
	}

	@Override
	public boolean putData(String tableName, String familyName, Map<String, String> dataMap) {
		return putData(tableName, dataMap.get(HbaseEntity.ROWKEY), familyName, dataMap);
	}


	@Override
	public boolean putData(String tableName, String familyName, List<Map<String, String>> dataList) {
		HTableInterface htable = null;
		try {
			htable = getTable(tableName);
			List<Put> puts = new ArrayList<Put>();
			for (Map<String, String> dataMap : dataList) {
				String rowKey = dataMap.get(HbaseEntity.ROWKEY);
				if (null == rowKey) {
					LOGGER.warn("tableName={} put data={} with rowkey is null.", tableName, HBaseUtils.map2String(dataMap));
					continue;
				}
				Put put = new Put(Bytes.toBytes(rowKey));
				for (Map.Entry<String, String> entry : dataMap.entrySet()) {
					if (HbaseEntity.ROWKEY.equals(entry.getKey())) {
						continue;
					}
					if (null == entry.getValue()) {
						put.add(Bytes.toBytes(familyName), Bytes.toBytes(entry.getKey()), null);
					} else {
						put.add(Bytes.toBytes(familyName), Bytes.toBytes(entry.getKey()), Bytes.toBytes(entry.getValue()));
					}
				}
				puts.add(put);
			}
			htable.put(puts);
			return true;
		} catch (Exception e) {
			LOGGER.error("hbase put data error:", e);
			return false;
		} finally {
			HBaseUtils.closeHtable(htable);
		}

	}

	@Override
	public boolean deleteData(String tableName, String rowKey) {
		return deleteData(tableName, Arrays.asList(new String[]{rowKey}));
	}

	@Override
	public boolean deleteData(String tableName, List<String> keyList) {
		HTableInterface htable = null;
		try {
			htable = getTable(tableName);
			List<Delete> list = new ArrayList<Delete>();
			for (String rowKey : keyList) {
				if (null == rowKey || "".equals(rowKey)) {
					continue;
				}
				list.add(new Delete(Bytes.toBytes(rowKey)));
			}
			htable.delete(list);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			HBaseUtils.closeHtable(htable);
		}

	}

	public void setHbaseConnectionManager(HbaseConnectionManager hbaseConnectionManager) {
		this.hbaseConnectionManager = hbaseConnectionManager;
	}
}

