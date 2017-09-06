package com.kingmon.project.psam.sy.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
@KMapper
public interface SyRkglLdrkdjbMapper {
    int deleteByPrimaryKey(String ldid);

    int insert(SyRkglLdrkdjb record);

    int insertSelective(SyRkglLdrkdjb record);

    SyRkglLdrkdjb selectByPrimaryKey(String ldid);

    int updateByPrimaryKeySelective(SyRkglLdrkdjb record);

    int updateByPrimaryKeyWithBLOBs(SyRkglLdrkdjb record);

    int updateByPrimaryKey(SyRkglLdrkdjb record);
    
    
   	public List<Map<String,Object>> selectSyLdrklist(Map<String,String> params);
   	
   	public Long selectSyLdrkCount(Map<String,String> params);
    /**
     * 根据建筑物房间Id查询流动人口信息
     * @param jzwfjid
     * @return
     */
	public SyRkglLdrkdjb selectLdrkByJzwfjId(String jzwfjid);
     /**
      * 根据身份证号查询流动人口信息
      * @param sfzh
      * @return
      */
	 public SyRkglLdrkdjb selectglldrkById(String sfzh);
     /**
      * 注销流动人口信息
      * @param map
      */
	 public void cancelLdrk(Map<String, Object> map);
     /**
      * 启用流动人口
      * @param map
      */
	 public void activatesyLdrk(Map<String, Object> map);
    /**
     * 根据房间Id查询流动人口信息
     * @param jzwfjid
     * @return
     */
	List<SyRkglLdrkdjb> selectSyLdrkInfoByJzwfjId(String jzwfjid);

	
	 /**
     * 根据房间Id查询流动人口全部信息
     * @param jzwfjid
     * @return
     */
	List<SyRkglLdrkdjb> selectSyLdrkInfoByJzwfjIdKey(String jzwfjid);
    /**
     * 统计当天采集流动人口个数
     * @param map
     * @return
     */
	long loadldrkWorkCount(Map<String, Object> map);
    /**
     * 统计当天采集的流动人口详细信息
     * @param map
     * @return
     */
	List<Map<String, Object>> selectSyLdrkWorkDetail(Map<String, Object> map);
	
	
	/**
     * 根据房间号查询 当前 房屋内 流动人口信息
     * 2016-08-11
     * @param jzwfjid
     * @return
     */
	public List<SyRkglLdrkdjb> selectSyLdrkInfoFwByJzwfjIdKey(String jzwfjid);
	
	
//-----------------------------------16-08-16-------------
		public List<Map<String,Object>> selectSyLdrkForFw(Map<String, String> map);
		
		public Long selectSyLdrkForFwCount(Map<String, String> map);
		
		public List<Map<String,Object>> selectSyLdrkForFwLsry(Map<String, String> map);
		
		public Long selectSyLdrkForFwLsryCount(Map<String, String> map);
}