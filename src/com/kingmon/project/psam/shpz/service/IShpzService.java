package com.kingmon.project.psam.shpz.service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.shpz.model.Shpz;

public interface IShpzService {
	
    int deleteShpzById(String pzid);

    int addShpz(Shpz record);

    Shpz selectShpzById(String pzid);

    int updateShpz(Shpz record);
    
    Shpz findShpzByLxAndOrgCode(String pzlx,String orgCode);
    
    String findShpzDm(String pzlx,String orgCode);
    
	public DataSet loadShpzDataSet(ParamObject paramObject);
}