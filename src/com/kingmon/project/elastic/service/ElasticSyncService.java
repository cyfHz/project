package com.kingmon.project.elastic.service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.project.elastic.model.SyncTask;
import com.kingmon.project.elastic.util.ElasticTypes;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.jwq.mapper.JwqMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjbxxMapper;
import com.kingmon.project.psam.mlph.dao.MlphMapper;

@Service
public class ElasticSyncService {
	@Autowired
	private ElasticService elasticService;
	@Autowired
	private JzwjbxxMapper jzwjbxxMapper;
	@Autowired
	private MlphMapper mlphMapper;
	@Autowired
	private JwqMapper jwqMapper;
	
	@Value("${elastic.enableAutoSync}")
	private boolean enableAutoSync;
	

	private final static int BULKSIZE = 5000;
	private Map<String, SyncTask> TASKS = new TreeMap<String, SyncTask>();

	public void autoSync() {
		if(enableAutoSync){
			String currentTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
			createAndRunTask("System-mlph-" + currentTime, "MLPH", true, false);
			createAndRunTask("System-jzw-" + currentTime, "JZW", true, false);
			createAndRunTask("System-jwq-" + currentTime, "JWQ", true, false);
		}
	}

	private void runSyncTask(SyncTask task) {
		BulkProcessor bulkProcessor = null;
		try {
			task.setCurrentMsg("任务开始执行");
			task.setStatus(1);// 正在执行
			task.setCurrentMsg("正在查询本次任务需要导入的数据条数...");
			long total = getSyncTaskTotal(task);
			task.setCurrentMsg("查询成功：" + total);
			if (total < 1) {
				task.setCurrentMsg("没有需要导入的数据，任务将结束。");
				task.setEnd(new Date());
				task.setStatus(2);
				return;
			}

			bulkProcessor = buildBulkProcessor(task);
			
			long i = 0;
			while(true){
				if (task.isForceStop()) {
					task.setStatus(4);
					break;
				}
				StringBuilder sb = new StringBuilder();
				sb.append("正在从数据库中读取第").append(i).append("到").append(i + BULKSIZE).append("条记录...");
				task.setCurrentMsg(sb.toString());
				List<Map<String, Object>> dataList = getSyncTaskCurrentData(task);
				if(dataList==null||dataList.size()<1){
					break;
				}
				task.setCurrentMsg("从数据库中读取成功!");
				for (int j = 0; j < dataList.size(); j++) {
					Map<String, Object> map = dataList.get(j);
					IndexRequest indexRequest = taskData2IndexRequest(task, map);
					if (indexRequest != null) {
						bulkProcessor.add(indexRequest, indexRequest.id());
					}
				}
				bulkProcessor.flush();
				task.setCurrent(task.getCurrent() + BULKSIZE);
				 i += BULKSIZE;
			}
			task.setEnd(new Date());
			task.setStatus(2);// 执行完毕
			task.setCurrentMsg("任务执行成功，即将自动结束。");
		} catch (Throwable t) {
			task.setStatus(3);// 执行完毕，出现异常
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			pw.close();
			task.setErrorMsg(sw.toString());
			task.setEnd(new Date());
			task.setCurrentMsg("任务执行过程中出现异常！即将自动结束。");
		} finally {
			try {
				if(bulkProcessor!=null){
					bulkProcessor.awaitClose(10, TimeUnit.SECONDS);
					task.setCurrentMsg("bulkProcessor 成功关闭！任务结束。");
				}
			} catch (Throwable e) {
				e.printStackTrace();
				task.setErrorMsg(e.getLocalizedMessage());
				task.setCurrentMsg("bulkProcessor 关闭时出现异常！任务结束。");
			}
		}

	}

	private BulkProcessor buildBulkProcessor(final SyncTask task) {
		return BulkProcessor.builder(elasticService.getClient(), new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				StringBuilder sb = new StringBuilder();
				sb.append("准备向Elasticsearch中导入[").append(request.numberOfActions()).append("]条数据!");
				task.setCurrentMsg(sb.toString());
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				StringBuilder sb = new StringBuilder();
				sb.append("往Elasticsearch中导入[").append(request.numberOfActions()).append("]条数据，成功! 返回结果 [hasFailures：").append(response.hasFailures()).append(", tookInMillis:")
						.append(response.getTookInMillis()).append(",failureMessage:").append(response.buildFailureMessage()).append("] ");
				task.setCurrentMsg(sb.toString());
				task.setSuccessCnt(task.getSuccessCnt() + request.numberOfActions());
				
				List<Object> succuessedIds = new ArrayList<Object>();
				List<Object> failIds = new ArrayList<Object>();
				
				BulkItemResponse[] items = response.getItems();
				for(BulkItemResponse item: items){
					if(item.isFailed()){
						Failure failure = item.getFailure();
						failIds.add(item.getId());
						task.setFailCnt(task.getFailCnt()+1);
						task.setCurrentMsg("数据有误，ID["+item.getId()+"] "+failure.getMessage());
					}else{
						succuessedIds.add(item.getId());
					}
				}
				
				if(succuessedIds.size()>0){
					setIsIndexed(task,1,succuessedIds);
				}
				if(failIds.size()>0){
					setIsIndexed(task,2,failIds);
				}
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				failure.printStackTrace();
				StringBuilder sb = new StringBuilder();
				sb.append("往Elasticsearch中导入数据出现异常:").append(failure.getMessage());
				task.setCurrentMsg(sb.toString());
				task.setErrorMsg(failure.getMessage());
				if (task.isStopOnError()) {
					task.setCurrentMsg("导入ElasticSearch出现异常，任务将自动终止！");
					task.setForceStop(true);
				}
			}
		}).setBulkActions(1000).setFlushInterval(TimeValue.timeValueSeconds(20)).setConcurrentRequests(0).build();
	}

	@Transactional
	protected void setIsIndexed(SyncTask task, int isIndexed, List<Object> list) {
		task.setCurrentMsg("正在设置IS_INDEX");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", list);
		map.put("IS_INDEXED", isIndexed);
		switch (task.getType()) {
		case MLPH:
			mlphMapper.batchUpdateIndex(map);
			break;
		case JZWJBXX:
			jzwjbxxMapper.batchUpdateIndex(map);
			break;
		case JWQ:
			jwqMapper.batchUpdateIndex(map);
			break;
		}
		task.setCurrentMsg("设置IS_INDEX,成功!");
	}

	private long getSyncTaskTotal(SyncTask task) {
		Map<String, String> params = new HashMap<String, String>();
		if (task.isNotIndexedOnly()) {
			params.put("IS_INDEXED", "0");
		}
		long total;
		switch (task.getType()) {
		case MLPH:
			total = mlphMapper.mlphListCount(params);
			break;
		case JZWJBXX:
			total = jzwjbxxMapper.jzwListElasticCount(params);
			break;
		case JWQ:
			total = jwqMapper.jwqListElasticCount(params);
			break;
		default:
			total = 0;
		}
		task.setTotalCnt(total);
		return total;
	}

	private List<Map<String, Object>> getSyncTaskCurrentData(SyncTask task) {
		Map<String, String> params = new HashMap<String, String>();
		if (task.isNotIndexedOnly()) {// 每次只取IS_INDEXED=0,前BULKSIZE条
			params.put("IS_INDEXED", "0");
			params.put("pagestart", 0 + "");
			params.put("pageend", BULKSIZE + "");
		} else {
			params.put("pagestart", task.getCurrent() + "");
			params.put("pageend", (task.getCurrent() + BULKSIZE) + "");
		}
		List<Map<String, Object>> result;
		switch (task.getType()) {
		case MLPH:
			result = mlphMapper.mlphList(params);
			break;
		case JZWJBXX:
			result = jzwjbxxMapper.jzwListElastic(params);
			break;
		case JWQ:
			result = jwqMapper.jwqListElastic(params);
			break;
		default:
			result = new ArrayList<Map<String, Object>>();
		}
		return result;

	}

	private IndexRequest taskData2IndexRequest(SyncTask task, Map<String, Object> map) {
		IndexRequest indexRequest = null;

		switch (task.getType()) {
		case MLPH:
			indexRequest = new IndexRequest("psam", "mlph", String.valueOf(map.get("YWLSH")));
			indexRequest.source(ElasticUtil.mlphMapToDocument(map));
			break;
		case JZWJBXX:
			indexRequest = new IndexRequest("psam", "jzwjbxx", String.valueOf(map.get("DZBM")));
			indexRequest.source(ElasticUtil.jzwMapToDocument(map));
			break;
		case JWQ:
			indexRequest = new IndexRequest("psam", "jwq", String.valueOf(map.get("JWQID")));
			indexRequest.source(ElasticUtil.jwqMapToDocument(map));
			break;
		default:
			indexRequest = null;
		}

		return indexRequest;
	}

	public Map<String, SyncTask> getTASKS() {
		return this.TASKS;
	}

	@Async
	public void createAndRunTask(String taskName, String type, boolean notIndexedOnly, boolean stopOnError) {
		SyncTask task = new SyncTask();
		task.setTaskId(UUID.randomUUID().toString());
		task.setStart(new Date());
		task.setStatus(0);
		task.setTaskName(taskName);
		task.setType(getTypeByName(type));
		task.setNotIndexedOnly(notIndexedOnly);
		task.setStopOnError(stopOnError);

		task.setCurrent(0);
		task.setFailCnt(0l);
		task.setSuccessCnt(0l);
		task.setTotalCnt(0l);

		TASKS.put(task.getTaskId(), task);

		runSyncTask(task);

	}

	private ElasticTypes getTypeByName(String type) {
		if ("MLPH".equals(type)) {
			return ElasticTypes.MLPH;
		}
		if ("JZW".equals(type)) {
			return ElasticTypes.JZWJBXX;
		}
		return ElasticTypes.JWQ;
	}

	public void stopTask(String taskId) {
		SyncTask task = TASKS.get(taskId);
		if (task != null) {
			task.setForceStop(true);
		}
	}
	
	public boolean isEnableAutoSync() {
		return enableAutoSync;
	}

	public void setEnableAutoSync(boolean enableAutoSync) {
		this.enableAutoSync = enableAutoSync;
	}

}
