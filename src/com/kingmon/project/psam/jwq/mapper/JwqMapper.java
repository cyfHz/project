package com.kingmon.project.psam.jwq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jwq.model.Jwq;

@KMapper
public interface JwqMapper {
	
    
    int insertSelective(Jwq record);
    
    Jwq selectByPrimaryKey(String JWQID);
    
    int updateByPrimaryKeySelective(Jwq record);
    

	void cancelJwq(Map<String, Object> map);
	
	void activateJwq(Map<String, Object> map);

	int updateBjzbz(@Param("jwqid") String jwqid,@Param("bjzbz")String bjzbz);
	
	
	List<Jwq> selectJwqListByUserId(String app_userid);
	
    
    Jwq selectByPrimaryKeyForUpdate(String jwqid);
    
	
	String selectPcsIdByJwqid(String jwqid);
	
	List<String> selectJwqBhListByPcsId(String pcsid);
	
	List<Jwq> selectJwqListByPcsId(String pcsid);
    
  //---------Elastic 同步   -------------
    public List<Map<String, Object>> jwqListElastic(Map<String, String> params);

	Long jwqListElasticCount(Map<String, String> params);
	
	void batchUpdateIndex(Map<String, Object> map);
	
  //---------end  Elastic-------------
	
	Jwq selectByJwqbh(String jwqbh);
	
	
	/**
	 * j.JWQID, j.PCSID,j.JWQBH,j.JWQMC,j.SFYX
	 * @param jwqIds
	 * @return
	 */
	List<Jwq> selectJwqByIds(@Param("jwqIds") List<String> jwqIds);
	
	String selectJwqBhById(String jwqid);
}