package com.micmiu.bigdata.deserteagle.core.model;

import java.util.List;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 22:45
 */
public abstract class AbstractPageQueryResult<T> implements PageQueryResult<T> {

	private long total;

	private int pageNo;

	private int pageSize;

	private List<T> queryList;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getQueryList() {
		return queryList;
	}

	public void setQueryList(List<T> queryList) {
		this.queryList = queryList;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}
