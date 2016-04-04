package com.micmiu.bigdata.deserteagle.es.service;


import com.micmiu.bigdata.deserteagle.core.model.FullTextIndexQueryResult;
import com.micmiu.bigdata.deserteagle.core.model.QueryFilter;
import com.micmiu.bigdata.deserteagle.core.service.FullTextIndexService;
import com.micmiu.bigdata.deserteagle.es.client.EsClientManager;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 3/31/2016
 * Time: 01:03
 */
public class EsFullTextIndexService implements FullTextIndexService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EsFullTextIndexService.class);

	private EsClientManager clientManager;

	public void setClientManager(EsClientManager clientManager) {
		this.clientManager = clientManager;
	}

	@Override
	public Long queryCount(String tableName, QueryFilter... filters) {
		SearchRequestBuilder builder = clientManager.getClient().prepareSearch(clientManager.getIndexPrefix() + "_" + tableName.toLowerCase())
				.setSearchType(SearchType.COUNT);
		QueryBuilder qb = parseQuery(Arrays.asList(filters));
		if (null != qb) {
			builder.setQuery(qb);
		}
		SearchResponse response = builder.execute().actionGet();
		return response.getHits().totalHits();

	}

	@Override
	public Long queryCount(String tableName, List<QueryFilter> filterList) {
		SearchRequestBuilder builder = clientManager.getClient().prepareSearch(clientManager.getIndexPrefix() + "_" + tableName.toLowerCase())
				.setSearchType(SearchType.COUNT);

		QueryBuilder qb = parseQuery(filterList);
		if (null != qb) {
			builder.setQuery(qb);
		}
		SearchResponse response = builder.execute().actionGet();
		return response.getHits().totalHits();

	}

	@Override
	public FullTextIndexQueryResult search(String tableName, int fromIndex, int pageSize, List<QueryFilter> filterList) {

		SearchRequestBuilder builder = clientManager.getClient().prepareSearch(clientManager.getIndexPrefix() + "_" + tableName.toLowerCase())
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);

		QueryBuilder qb = parseQuery(filterList);
		if (null != qb) {
			builder.setQuery(qb);
		}
		SearchResponse response = builder.setFrom(fromIndex)//从第几行开始查询
				.setSize(pageSize)//查出了多少行
				.setExplain(true).execute().actionGet();

		FullTextIndexQueryResult result = new FullTextIndexQueryResult();
		result.setTotal(response.getHits().totalHits());

		List<String> rowkeyList = new ArrayList<String>();
		SearchHit[] hits = response.getHits().getHits();
		LOGGER.debug("es search hits count = " + hits.length);
		for (SearchHit hit : response.getHits().getHits()) {
			rowkeyList.add(hit.getId());
		}
		result.setQueryList(rowkeyList);
		return result;
	}

	private QueryBuilder parseQuery(List<QueryFilter> filterList) {
		if (null != filterList && !filterList.isEmpty()) {
			BoolQueryBuilder qb = QueryBuilders.boolQuery();
			for (QueryFilter filter : filterList) {
				Object[] datas = filter.getDatas();
				if (null == datas || datas.length < 1 || (datas.length == 1 && null == datas[0])) {
					continue;
				}
				if ("prefix".equals(filter.getOp())) {
					qb.must(QueryBuilders.prefixQuery(filter.getField(), datas[0] + ""));
				} else if ("term".equals(filter.getOp())) {
					qb.must(QueryBuilders.termQuery(filter.getField(), datas[0]));
				} else if ("range".equals(filter.getOp())) {
					if (filter.getDatas().length < 2) {
						qb.must(QueryBuilders.rangeQuery(filter.getField()).gte(datas[0]));
					} else {
						if (null == filter.getDatas()[0]) {
							qb.must(QueryBuilders.rangeQuery(filter.getField()).lt(datas[1]));
						} else {
							qb.must(QueryBuilders.rangeQuery(filter.getField()).from(datas[0]).to(datas[1]));
						}
					}
				} else if ("gte".equals(filter.getOp())) {
					qb.must(QueryBuilders.rangeQuery(filter.getField()).gte(datas[0]));
				} else if ("gt".equals(filter.getOp())) {
					qb.must(QueryBuilders.rangeQuery(filter.getField()).gt(datas[0]));
				} else if ("lte".equals(filter.getOp())) {
					qb.must(QueryBuilders.rangeQuery(filter.getField()).lte(datas[0]));
				} else if ("lt".equals(filter.getOp())) {
					qb.must(QueryBuilders.rangeQuery(filter.getField()).lt(datas[0]));
				} else {
					qb.must(QueryBuilders.queryStringQuery("*" + datas[0] + "*").field(filter.getField()));
				}

			}
			return qb;
		}
		return null;
	}
}
