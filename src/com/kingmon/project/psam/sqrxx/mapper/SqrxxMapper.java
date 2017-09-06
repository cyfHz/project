package com.kingmon.project.psam.sqrxx.mapper;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sqrxx.model.Sqrxx;
@KMapper
public interface SqrxxMapper {
	
	/**
	 * 根据申请人编号删除申请人信息
	 * @param SQRID 申请人编号
	 * @return
	 */
    int deleteByPrimaryKey(String SQRID);

    /**
     * 添加
     * @param record 申请人信息
     * @return
     */
    int insert(Sqrxx record);

    /**
     * 添加信息
     * @param 
     *       record 申请人信息
     *       (部分字段可为空)
     * @return
     */
    int insertSelective(Sqrxx record);

    /**
     * 根据申请人编号查询申请人信息
     * @param SQRID 申请人编号
     * @return
     */
    Sqrxx selectByPrimaryKey(String SQRID);
    
    /**
     * 根据申请人身份证号查询申请人信息
     * @param sfzh 身份证号
     * @return
     */
    Sqrxx selectBySfzh(String sfzh);

    /**
     * 根据申请人编号修改信息
     * @param  record 申请人信息
     *       (部分字段可为空)
     * @return
     */
    int updateByPrimaryKeySelective(Sqrxx record);

    /**
     * 根据申请人编号修改信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Sqrxx record);
}