package com.kingmon.project.psam.mlph.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.mlph.model.Mlph;
@KMapper
public interface MlphMapper {

	public Mlph selectMlphByYwlsh(String ywlsh);
	
	void insertSelective(Mlph mlph);
	
	List<Map<String, Object>> mlphList(Map<String, String> params);

	Long mlphListCount(Map<String, String> params);

	void addMlph(Map<String, Object> params);

	void saveMlph(Map<String, Object> params);

	void settag(Map<String, Object> params);
//	/**
//	 * 申请添加
//	 * @param shpzdm
//	 * @param dzbm
//	 */
//	void applyUseMlph(String shpzdm,List<String> ywlshs);
	 
	void applyUseMlph(@Param("spzt") String spzt,@Param("shpzdm")String shpzdm,@Param("ywlsh")String ywlsh);
//	String queryShpzdm(String pzlx);
	/**
	 * 审批
	 * @param dzbm
	 * @param spzt
	 */
	void reviewMlph(@Param("spzt") String spzt,@Param("shpzdm")String shpzdm,@Param("ywlsh") String ywlsh);


	/**
	 *  //需要修改字段包括<br>
		map.put("YWLSH", ywlsh);<br>
		map.put("JWQBH",jwq.getJwqbh());//警务区编号<br>
		map.put("JWQMC", jwq.getJwqmc());//警务区名称<br>
		map.put("SJGSDWDM",jwq.getJwqbh());//数据归属单位<br>
		map.put("SJGSDWMC",jwq.getJwqmc());//数据归属名称<br>
		map.put("XGR", SecurityUtils.getUserId());// 修改人<br>
		map.put("GXSJ",new Date());//修改时间<br>
		map.put("XGDW", SecurityUtils.getUserOrgCode());//修改单位<br>
		map.put("SSPCS", jwq.getJwqbh().substring(0,9)+"000");//所属派出所<br>
		map.put("SSFJ",  jwq.getJwqbh().substring(0,6)+"000000");//所属分局<br>
		map.put("SSSJ", jwq.getJwqbh().substring(0,4)+"00000000");//所属市局<br>
		map.put("ZXDHZB", new BigDecimal(point.getLon()).setScale(8,RoundingMode.CEILING));<br>
		map.put("ZXDZZB", new BigDecimal(point.getLat()).setScale(8,RoundingMode.CEILING));<br>
	 * @param params
	 */
	void updateMlphLocation(Map<String, Object> params);
	
//	List<Map<String, Object>>  findShpz(String sssj, String pzlx);
	
	void batchUpdateIndex(Map<String, Object> map);
	
	public Mlph selectMlphByJzwId(String jzwjbxxid);

	public String selectSsJzwDzbm(String ywlsh);
	
	/**
	 * 校验门楼牌数据
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> checkmlphList(Map<String, String> params);
	/**
	 * 校验门楼牌数据的个数
	 * @param params
	 * @return
	 */
	public Long selectCheckCount(Map<String, String> params);
	/**
	 * 通过业务流水号批量修改楼门牌的警务区名称和编号
	 * 并记录原始警务区编号
	 * @param list 业务流水号 集合
	 * @param jwqmc 警务区名称
	 * @param jwqbh 警务区编号
	 * @param originaljwqbh 原警务区编号
	 */
	void updateMlphJwqBatchByYwlshs(Map<String,Object> paramList);
}