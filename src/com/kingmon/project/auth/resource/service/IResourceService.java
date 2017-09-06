package com.kingmon.project.auth.resource.service;

import java.util.List;






import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.auth.resource.model.Resource;

public interface IResourceService {
	
	/**
	 *r.RES_ID ,
      r.RES_NAME,
      r.RES_TYPE ,
      r.RES_PVALUE,
      r.RES_ORDER,
      r.RES_PID,
      r.ENABLED,
      r.IS_HREF
	 * @param userId
	 * @return
	 */
	public List<Resource> findResListByUserId(String userId);
	
	public List<String> findResCodeListByUserId(String userId);
	
//	public void scanResource(String packagz);
	
	public DataSet  loadResourceTreeGrid(ParamObject paramObject);
	
	public DataSet loadResourceTree(String pid);
}
