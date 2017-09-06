package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;
import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjbPic;
@KMapper
public interface SyRkglLdrkdjbPicMapper {


    int insertSelective(SyRkglLdrkdjbPic record);

    SyRkglLdrkdjbPic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SyRkglLdrkdjbPic record);

    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyRkglLdrkdjbPic selectPicInfoByZjhm(String gmsfhm);
	
	public SyRkglLdrkdjbPic selectNotNullPicInfoByZjhm(String gmsfhm);
	
    /**
     * 根据证件号码加载照片信息
     * @param zjhm
     * @return
     */
	@SuppressWarnings("rawtypes")
	List<Map> selectRkglPicByzjhm(String zjhm);

}