package com.kingmon.project.psam.jwq.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.jwq.model.JwqyJygx;
@KMapper
public interface JwqyJygxMapper {
    int deleteByPrimaryKey(String ID);

    int insert(JwqyJygx record);

    int insertSelective(JwqyJygx record);

    JwqyJygx selectByPrimaryKey(String ID);

    int updateByPrimaryKeySelective(JwqyJygx record);

    int updateByPrimaryKey(JwqyJygx record);
    /**
     * 根据用户id查询警务区和警员对应关系
     * @param id
     * @param jwqid 
     * @return
     */
	long selectByappuserid(Map<String, Object> map);
	/**
	 * 更新警员警员关系
	 * @param map1
	 */

	void updateJwqjy(Map<String, Object> map1);
   /**
    * 增加警务区警员关系
    * @param map2
    */
	void insertJwqJy(Map<String, Object> map2);
	
	
	
	
	List<Map<String, Object>> JwqyJygxList(Map<String, Object> map);
	
	Long JwqyJygxListCount(Map<String, Object> map);
	
	List<String> selectJwqBhByUserId(String appuser_id);
	//public List<String> seelctJwqIdByAppuserId(@Param("appuser_id")String appuser_id,@Param("sfyx") String sfyx);

} 