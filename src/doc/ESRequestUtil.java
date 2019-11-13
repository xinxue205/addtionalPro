package doc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESRequestUtil {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	String address;
	int port;
	String clusterName;
	
	public ESRequestUtil(String address, int port, String clusterName) {
		this.address = address;
		this.port = port;
		this.clusterName = clusterName;
//		connect();
	}
	
	
	TransportClient client;
	
	public boolean connect(){
		Settings settingsBuilder = Settings.builder()
                .put("cluster.name", clusterName)
                .put("client.transport.sniff", true).build();
		client = new PreBuiltTransportClient(settingsBuilder);
		try {
			client.addTransportAddress( new TransportAddress( InetAddress.getByName( address ), port ) );
			return true;
		} catch (Exception e) {
			LOG.error("初始化连接失败", e);
			return false;
		}
	}
	
	public void disconnect(){
		client.close();
	}
	
	public void reconnect(String address, int port, String clusterName){
		client.close();
		connect();
	}
	
//	public BulkProcessor prepareUse(){
//		return BulkProcessor.builder(client, new BulkProcessor.Listener() {
//			
//			@Override
//			public void beforeBulk(long executionId, BulkRequest request) {
//				LOG.info("序号：{} 开始执行{} 条记录保存",executionId,request.numberOfActions());
//			}
//			
//			@Override
//			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
//				LOG.error(String.format("序号：%s 执行失败; 总记录数：%s",executionId,request.numberOfActions()),failure);
//			}
//			
//			@Override
//			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
//				LOG.info("序号：{} 执行{}条记录保存成功,耗时：{}毫秒,",executionId,request.numberOfActions(),response.getIngestTookInMillis());
//			}
//		}).setBulkActions(1000)
//				.setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))
//				.setConcurrentRequests(4)
//				.setFlushInterval(TimeValue.timeValueSeconds(5))
//				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500),3))  //失败后等待多久及重试次数
//				.build();
//	}
	
	/**
	 * 添加文档
	 * @return 
     */
	public String addDocument(String index, String type, Map content, String pipeName, String id) {
	    IndexResponse resp = client.prepareIndex(index, type).setId(id).setSource(content).get();
	    LOG.info("添加结果：{}",resp.toString());
	    return resp.getId();
    }
	
	/**
     * 按id删除
     * 
     */
    public void deleteDocumentById(String index, String type, String id, String pipeName) {
	   DeleteResponse resp = client.prepareDelete(index, type, id).get();
	   LOG.info("删除结果：{}",resp.toString());
    }
	
	/**
     * 按条件删除
     * 
     */
    public void deleteDocumentByQuery(String index, String type, String pipeName) {
    	DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE.newRequestBuilder(client);
    	builder.source().setIndices(index).setTypes(type);
    	BulkByScrollResponse resp = builder.get();
    	LOG.info("删除结果：{}",resp.toString());
    }
    
    /**
     * 按ID更新
     * 
     */
    public void updateDocument(String indices, String type,String id, Map content, String pipeName) {
    	UpdateResponse resp = client.prepareUpdate(indices, type, id).setDoc(content).get();
    	LOG.info("更新结果：{}",resp.toString());
    }
    

    /**
     * 按条件更新
     * 
     */
    public void updateDocumentByQuery(String indices, String type, Object object) {
    	UpdateByQueryRequestBuilder builder = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
    	builder.source().setIndices(indices).setTypes(type);//.setQuery(convertParam(param));
    	BulkByScrollResponse resp = builder.get();
    	LOG.info("删除结果：{}",resp.toString());
    }
    
    public static class Server {
        public String address;
        public int port;

        public TransportAddress getAddr() throws UnknownHostException {
          return new TransportAddress( InetAddress.getByName( address ), port );
        }
    }

	public TransportClient getClient() {
		return client;
	}
    
//    private BoolQueryBuilder convertParam(DocSearchParam param) {
//
//    	BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//    	if (StringUtils.hasText(param.getUserName())) {
//    	    boolQueryBuilder.must(QueryBuilders.termQuery("userName", param.getUserName()));
//    	}
//    	if (param.getAge() != null) {
//    	    boolQueryBuilder.must(QueryBuilders.rangeQuery("age").gt(param.getAge()));
//    	}
//    	if (StringUtils.hasText(param.getDescription())) {
//    	    boolQueryBuilder.must(QueryBuilders.matchQuery("description", param.getDescription()));
//    	}
//    	if(StringUtils.hasText(param.getRoleName())) {
//    	    boolQueryBuilder.must(QueryBuilders.nestedQuery("roles", QueryBuilders.termQuery("roles.name", param.getRoleName()), ScoreMode.None));
//    	}
//    	
//    	return boolQueryBuilder;
//    }
//    
//    public static class DocSearchParam{
//    	String docName;
//    	String docPath;
//    	String docDesc;
//    	
//    }
}
