package com.micmiu.bigdata.deserteagle.core.model;

import java.util.List;

/**
 * Created
 * User: <a href="http://micmiu.com">micmiu</a>
 * Date: 3/31/2016
 * Time: 00:34
 */
public class FullTextIndexQueryResult extends AbstractPageQueryResult<String> {

	public List<String> getRowkeyList() {
		return getQueryList();
	}
}
