package com.kingmon.project.psam.jzw.mapper;

import java.util.List;
import java.util.Map;

import com.kingmon.base.common.KMapper;
import com.kingmon.project.psam.jzw.model.Jzwfj;
@KMapper
public interface JzwfjMapper {
	
    int insertSelective(Jzwfj record);

    Jzwfj selectByPrimaryKey(String jzwfjid);

    int updateByPrimaryKeySelective(Jzwfj record);
    
	public int batchUpdateJzwfj(List<Jzwfj> list);
	
	public int batchInsertJzwfj(List<Jzwfj> records);
	
	//---by  jg
	public List<Jzwfj> selectFjByJzwjgId(String jzwjgid);
	public List<Jzwfj> selectSortedFjByJzwjgId(String jzwjgid);
	/**
	 * Map：JZWFJID, FJXH, FJMC,SHOWINFO,SHOWMODE
	 * @param jzwjgid
	 * @return
	 */
	public List<Map<String, Object>> selectFjShowMapByJzwjgId(String jzwjgid);

	//---by  dy
	public List<Jzwfj> selectJzwfjByJzwDyId(String jzwdyId);
	
	public Long selectJzwfjCountByJzwDyId(String jzwdyId);
	
	
	//---by  lc
	public List<Jzwfj> selectSortedJzwfjByJzwlcId(String jzwlcid);


	public List<Jzwfj> selectJzwfjByJzwLcId(String jzwlcId);
	
	public Long selectJzwfjCountByJzwLcId(String jzwlcId);
	
//-------------------------
	public int deletejzwfjByLcid(String jzwlcid);
	
}
