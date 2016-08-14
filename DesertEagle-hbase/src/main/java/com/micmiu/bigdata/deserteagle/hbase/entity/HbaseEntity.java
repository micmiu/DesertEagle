package com.micmiu.bigdata.deserteagle.hbase.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 3/9/2016
 * Time: 10:59
 */
public class HbaseEntity {

	public static final String ROWKEY = "@ROWKEY@";

	private String tableName;

	private String familyName;

	private String rowkey;

	private Map<String, Object> qualifierValueMap = new HashMap<String, Object>();

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public Map<String, Object> getQualifierValueMap() {
		return qualifierValueMap;
	}

	public void setQualifierValueMap(Map<String, Object> qualifierValueMap) {
		this.qualifierValueMap = qualifierValueMap;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getRowkey() {
		return rowkey;
	}

	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}

}
