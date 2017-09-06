package com.kingmon.project.psam.datasync.service;

import java.util.List;

import com.kingmon.project.psam.jwq.model.Jwq;

public interface IMlphAndJzwDataSyncService {
//	/**
//	 * 修改相应警务区下的门楼牌信息
//	 * @param oldList 原数据集合
//	 * @param newList 新数据集合
//	 * @param Jwq 警务区对象
//	 * @param tableName 处理的表名称
//	 */
//	void changeMlphByJwq(List<String> oldList, List<String> newList,Jwq Jwq,String tableName);

	void asyncForJwqBjzbChange(Jwq jwq,String newBjzbz,String oldBjzbz);
}
