package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;
import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;

@KMapper
public interface JzwjbxxMapper {
     int insertSelective(Jzwjbxx record);
    Jzwjbxx selectByPrimaryKey(String dzbm);
    
    long selectCountByPrimaryKey(String dzbm);
    
	public void updateByPrimaryKeySelective(Jzwjbxx jzwjbxx);
    //--------------------------------------------------------------------
	public List<Map<String,Object>> selectJzwjbxxList(Map<String,String> params);
	public Long selectJzwjbxxCount(Map<String,String> params);

	void batchcancelJzw(Map<String, Object> map);
	void batchActiveJzw(Map<String, Object> map);
	
	/**
	 * 	 * 修改建筑物的坐标和警务区信息
	 * //需要修改字段包括
		map.put("DZBM", dzbm);<br>
		map.put("XGR", SecurityUtils.getUserId());// 修改人<br>
		map.put("GXSJ",new Date());//修改时间<br>
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位<br>
		map.put("ZAGLSSJWZRQDM",jwq.getJwqbh());//数据归属单位<br>
		map.put("ZAGLSSJWZRQMC",jwq.getJwqmc());//数据归属名称<br>
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));<br>
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));<br>
	 * @param map
	 */
	void updateJzwLocation(Map<String, Object> map);
	
	void updateJzwjbxxBatchByDzbm(Map<String,Object> paramList);
	
	//---------Elastic 同步   -------------
		public List<Map<String, Object>> jzwListElastic(Map<String, String> params);
		public Long  jzwListElasticCount(Map<String, String> params);
		public void batchUpdateIndex(Map<String, Object> map);
	//---------end  Elastic-------------
		/**
		 * 统计采集建筑物个数
		 * @param map
		 * @return
		 */
		long loadWorkJzwCount(Map<String, Object> map);
		/**
		 * 加载今天统计建筑物的个数
		 * @param map
		 * @return
		 */
		List<Map<String, Object>> loadTodayWorkDetail(Map<String, Object> map);
		
		public List<Map<String,Object>> selectJzwForMlph(String mlph);
}