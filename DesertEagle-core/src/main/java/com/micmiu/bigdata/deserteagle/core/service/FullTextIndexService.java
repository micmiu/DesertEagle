package com.micmiu.bigdata.deserteagle.core.service;

import com.micmiu.bigdata.deserteagle.core.query.FullTextIndexQueryResult;
import com.micmiu.bigdata.deserteagle.core.query.QueryFilter;

import java.util.List;

/**
 * 全文索引查询服务接口
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 3/31/2016
 * Time: 00:32
 */
public interface FullTextIndexService {

	/**
	 * 根据搜索条件实现分页查询
	 * @param tableName
	 * @param fromIndex
	 * @param pageSize
	 * @param filterList
	 * @return
	 */
	FullTextIndexQueryResult search(String tableName, int fromIndex, int pageSize, List<QueryFilter> filterList);

	/**
	 * 根据搜索条件查询总数
	 * @param tableName
	 * @param filters
	 * @return
	 */
	Long queryCount(String tableName, QueryFilter... filters);

	/**
	 * 根据搜索条件查询总数
	 * @param tableName
	 * @param filterList
	 * @return
	 */
	Long queryCount(String tableName, List<QueryFilter> filterList);

	/**
	 * 根据搜索条件查询所有记录
	 * @param tableName
	 * @param filterList
	 * @return
	 */
	FullTextIndexQueryResult search(String tableName, List<QueryFilter> filterList);

	/**
	 * 根据搜索条件查询所有记录
	 * @param tableName
	 * @param filters
	 * @return
	 */
	FullTextIndexQueryResult search(String tableName, QueryFilter... filters);

	/**
	 * 根据搜索条件查询指定数据的记录
	 * @param tableName
	 * @param limit
	 * @param filterList
	 * @return
	 */
	FullTextIndexQueryResult search(String tableName, int limit, List<QueryFilter> filterList);

	/**
	 * 根据搜索条件查询指定数据的记录
	 * @param tableName
	 * @param limit
	 * @param filters
	 * @return
	 */
	FullTextIndexQueryResult search(String tableName, int limit, QueryFilter... filters);

}
