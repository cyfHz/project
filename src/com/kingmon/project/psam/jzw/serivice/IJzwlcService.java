package com.kingmon.project.psam.jzw.serivice;

import java.util.List;
import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.project.psam.jzw.model.Jzwlc;

public interface IJzwlcService {
	
	public DataSet loadJzwlcDataSet(ParamObject paramObject);
	
	public List<Jzwlc> loadJzwlcByJzwJgid(String jzwjgID);
}
