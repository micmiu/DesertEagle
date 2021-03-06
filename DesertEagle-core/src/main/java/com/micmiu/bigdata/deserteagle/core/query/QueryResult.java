package com.micmiu.bigdata.deserteagle.core.query;


import java.util.List;

/**
 * query result bean
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 03/31/2016
 */
public interface QueryResult<T> {

	long getTotal();

	List<T> getQueryList();
}
