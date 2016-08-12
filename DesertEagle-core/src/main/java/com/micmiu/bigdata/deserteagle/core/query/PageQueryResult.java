package com.micmiu.bigdata.deserteagle.core.query;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 4/1/2016
 * Time: 22:37
 */
public interface PageQueryResult<T> extends QueryResult<T> {

	int getPageNo();

	int getPageSize();
}
