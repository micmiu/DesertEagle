package com.micmiu.bigdata.deserteagle.core.query;

/**
 * query filter
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 03/31/2016
 */
public class QueryFilter {

	private String field;

	private String op;

	private Object data;

	private Object[] datas;

	public QueryFilter() {
	}

	public QueryFilter(String field, String op, Object data) {
		this.data = data;
		this.field = field;
		this.op = op;
	}

	public QueryFilter(String field, String op, Object[] datas) {
		this.datas = datas;
		this.field = field;
		this.op = op;
	}

	public Object[] getDatas() {
		if ((null == datas || datas.length < 1) && null != data) {
			return new Object[]{data};
		}
		return datas;
	}

	public void setDatas(Object[] datas) {
		this.datas = datas;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}
}
