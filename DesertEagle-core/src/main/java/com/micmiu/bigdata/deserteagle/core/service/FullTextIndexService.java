package com.micmiu.bigdata.deserteagle.core.service;

import com.micmiu.bigdata.deserteagle.core.model.FullTextIndexQueryResult;
import com.micmiu.bigdata.deserteagle.core.model.QueryFilter;

import java.util.List;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 3/31/2016
 * Time: 00:32
 */
public interface FullTextIndexService {

	FullTextIndexQueryResult search(String tableName, int fromIndex, int pageSize, List<QueryFilter> filterList);

	Long queryCount(String tableName, QueryFilter... filters);

	Long queryCount(String tableName, List<QueryFilter> filterList);
}
