package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.Jzwlc;

@KMapper
public interface JzwlcMapper {

	int insertSelective(Jzwlc record);
	
    int deleteByPrimaryKey(String jzwlcid);

    Jzwlc selectByPrimaryKey(String JZWLCID);

    int updateByPrimaryKeySelective(Jzwlc record);
	
	public int batchInsertJzwlc(List<Jzwlc> records);
	
//--------------------------------------by jgid--------------------------------------	
//	public List<Jzwlc> selectDistinctLcxhByJzwJgid(String jzwjgId);
	
	/**
	 *  DISTINCT(LCXH) ,查找该建筑物，唯一的楼层序号，用户显示结构时，加载楼层信息。
	 */
	public List<Jzwlc> selectDistinctSortedLcxhByJzwJgid(String jzwjgId);
	
//	public List<Jzwlc> selectJzwlcByJzwJgid(String jzwjgId);
	
	public List<Jzwlc> selectSortedJzwlcByJzwJgid(String jzwjgId);
	
//--------------------------------------by dyid--------------------------------------
	public List<Jzwlc> selectSortedJzwlcByJzwdyid(String jzwdyId);
	
//	public List<Jzwlc> selectJzwlcByJzwDyid(String jzwdyId);
	
	public Long selectJzwlcCountByJzwDyid(String jzwdyId);
	
	////select max(xxx) from(select count(1) as xxx from dz_jzwlc l where l.jzwjgid='19b861b3-a467-4809-8572-f12fd79dff30' group by l.jzwdyid);
	public Long selectMaxJzwlcCountGroupByJzwDyid(String jzwjgId);
	public Long selectMaxJzwlcDXCountGroupByJzwDyid(String jzwjgId);
	public Long selectMaxJzwlcDSCountGroupByJzwDyid(String jzwjgId);
	/**
	 * 楼层信息查询服务 查询接口
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> jzwlcListForWebService(Map<String, String> params);
	/**
	 * 楼层信息查询服务 查询总条数
	 * @param params
	 * @return
	 */
	public Long countForWebService(Map<String,String> params);
}