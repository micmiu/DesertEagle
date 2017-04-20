package com.micmiu.bigdata.deserteagle.core.query;

/**
 * page query result
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 22:37
 */
public interface PageQueryResult<T> extends QueryResult<T> {

	int getPageNo();

	int getPageSize();
}
