package com.kingmon.project.psam.dataStatistic.service;

import java.util.List;
import java.util.Map;

public interface IDataStatisticService {

 public List<Map<String,Object>> loadSjStatisticData();

 public List<Map<String,Object>> loadFjStatisticData(String sjName);

 /**
  * 数据完整度统计(新)
  * @return
  */
 List<Map<String, Object>> loadNewStatisticData(String orgCode,int level,boolean isFirst);

 /**
  * ES数据完整度统计(新)
  * @return
  */
 List<Map<String, Object>> loadStatisticDataFromEs(String orgCode,int level,boolean isQueryChild);


}
