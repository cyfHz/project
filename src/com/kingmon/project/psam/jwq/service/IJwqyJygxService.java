package com.kingmon.project.psam.jwq.service;

import java.util.List;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;



public interface IJwqyJygxService {


	/**
	 * 加载派出所中，已经分配警务区的警务区警员列表
	 * @param po
	 * @param jwqid
	 * @return
	 */
	public DataSet loadPcsJyInJwqDataSet(ParamObject po,String jwqid);
	
	/**
	 * 加载派出所内，未分配警务区的警员列表
	 * @param po
	 * @param jwqid
	 * @return
	 */
	public DataSet loadPcsJyNotInJwqDataSet(ParamObject po,String jwqid);
	
	public void removeJyFromJwq(String[] ids, String jwqid);

	void addJyToJwq(String[] app_userids, String jwqid);

//	void enableJwqyJygx(String app_userid, String jwqid);

	void disableJwqyJygx(String app_userid, String jwqid);
	
	public List<String> findJwqIdByAppuserId(String appuser_id,String sfyx);
	
	public boolean isUserAssignedToJwq(String appuser_id);
}
