# DesertEagle #

## info ##
DesertEagle（沙漠之鹰）,一个灵活小巧的HBase 操作的工具

## Module ##

* DesertEagle-parent  	: 父模块，配置依赖关系
* DesertEagle-core  	: 主要核心模块
* DesertEagle-es	  	: elasticsearch查询功能模块
* DesertEagle-hbase   	: hbase操作功能模块


## DesertEagle-core ##

* Long queryCount(String tableName, QueryFilter... filters)		:根据搜索条件查询总数
* Long queryCount(String tableName, List<QueryFilter> filterList)	:根据搜索条件查询总数
* FullTextIndexQueryResult search(String tableName, List<QueryFilter> filterList)	: 根据搜索条件查询所有记录
* FullTextIndexQueryResult search(String tableName, QueryFilter... filters)		:根据搜索条件查询所有记录
* FullTextIndexQueryResult search(String tableName, int limit, List<QueryFilter> filterList)	:根据搜索条件查询指定数据的记录	
* FullTextIndexQueryResult search(String tableName, int limit, QueryFilter... filters)		:根据搜索条件查询指定数据的记录
* FullTextIndexQueryResult search(String tableName, int fromIndex, int pageSize, List<QueryFilter> filterList)	:根据搜索条件实现分页查询

## DesertEagle-hbase ##

* Boolean checkTable(String tableName)	: 判断表是否存在
* HTableInterface getTable(String tableName)	:获取表
* Map<String, String> getDataByKey(String tableName, String rowkey, String familyName)	: 根据rowkey获取数据
* List<Map<String, String>> queryDataByRowkeyList(String tableName, String familyName, List<String> rowkeyList) : 根据List<rowkey>获取数据集
* List<Map<String, String>> queryDataListLimit(String tableName, String familyName, int limit)	: 获取表指定数量的数据
* boolean putDataEntity(HbaseEntity entity)	: 保存数据
* boolean putData(String tableName, String rowKey, String familyName, Map<String, String> dataMap)	:保存数据
* boolean putData(String tableName, String familyName, Map<String, String> dataMap)		:保存数据
* boolean putData(String tableName, String familyName, List<Map<String, String>> dataList)		:保存数据
* boolean deleteData(String tableName, String rowKey)	:删除数据
* boolean deleteData(String tableName, List<String> keyList)	:删除数据

## DesertEagle-es ##

* EsFullTextIndexService	:利用elasticsearch 建立全文索引,实现 DesertEagle-core 的查询接口功能