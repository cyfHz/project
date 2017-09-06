package com.kingmon.project.psam.jzw.serivice;

import java.util.Map;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.project.psam.jzw.model.Jzwjg;

public interface IJzwjgService {
	/**
	 * 根据id查询Jzwjg
	 * @param jzwjgId
	 * @return
	 */
	public Jzwjg findValidateAndBuildJzwjgById(String jzwjgId);
	/**
	 * 根据JzwId查询jzwjg
	 * @param jzwid
	 * @return
	 */
	public Jzwjg findJustJzwjgByJzwId(String jzwid);
	/**
	 * 查询所属建筑物id
	 * @param jzwjgId
	 * @return
	 */
	public String  findJustJzwIdByJgid(String jzwjgId);
	
	/**
	 * 
	 type:地下室 : 0：无地下室 1：规则分单元地下室    2：不规则地下室<br/>
	 0：无地下室:     view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"0"};<br/>
	 1：规则分单元地下室:view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"type":"1"};<br/>
	 2：地下房间不规则排列:view={"jzwjg":jzwjg,"jzwId":jzwId,"isRegular":isRegular,"dylcfjRules":"","prefixRules":prefixRules,"postfixAndWidthRule":postfixAndWidthRule,"dxlc":dxlc,"type":"2"};<br/>
	<br/>
	{"jzwjg":{"dys":"4","lcs":"10","mdyms":"2","dxcs":"3","dxmcms":"2"},<br/>
	 "jzwId":"ed28d485-a5f4-4b17-9efb-eae1e6ba243c",<br/>
	 "prefixRules":[{"from":"1","to":"3","value":"A"},<br/>
				   {"from":"4","to":"6","value":"B"}],<br/>
	 "postfixAndWidthRule":{"postfix_dy":"单元","postfix_lc":"层","postfix_fj":"室",<br/>
						   "width_dy":"2","width_lc":"3","width_fj":"2"},<br/>
	 "type":"1"}<br/>
	*/
	public void addAnddBuildJzwjg(Map<?,?> view);
	
	public KJSONMSG canAdd(String refFjId, String jzwId,boolean isUpDown);
	
	public KJSONMSG  validateJzwjg(String jzwjgId);
	
	public KJSONMSG validateJzwjgByJzwId(String jzwId);
	
	//------------------------------------------
	public DataSet loadJzwjgDyLcInfo(Map<String,String> params) ;
	
	@Deprecated
	public  void updateJzwjgDyLcXh (Map<?,?> view);
	/**
	 * 建筑物结构房间坐标未生成之前，此处只能修改序号，----校验不成功，修改序号<br>
	 * 
	 * 建筑物结构生成之后，不允许在修改序号，只能添加，删除单元，楼层
	 * 
	 * @param view {xh=001, jgtype=dy, mc=1单元, id=a2021891-a4f0-4949-b962-ebfa60f49d77}
	 */
	void updateJzwjgDyLcXh_20160521(Map<?,?>  view);
	
	/**
	 * 添加建筑物结构单元
	 * @param view  {"jzwjgid":jzwjgid,"dy_xh":dy_xh,"dy_mc":dy_mc,"dy_lcs":dy_lcs,"dy_ms":dy_ms};
	 */
//	public  void addJzwjgDy(Map<?,?> view);
	public  void addJzwjgDy_20160521(Map<?,?> view);
	
//	public void delJzwjgDy(String jzwjgid,String dyid);
	public void delJzwjgDy_20160521(String jzwjgid,String dyid);
	/**
	 * 
	 * @param view {"jzwjgid":jzwjgid,"jzwjgDyid":jzwjgDyid,"lc_xh":lc_xh,"lc_mc":lc_mc};
	 */
	public  void addJzwjgLc(Map<?,?> view);
	
	public void delJzwjgLc(String jzwjgid,String lcid);
	
	public void delJzwjgfj(String jzwjgid,String fjid);
	
	/**
	 * 
	 * @param view {"jzwjgid":jzwjgid,"jzwjgDyid":ss_dyid,"max_lc_xh":max_lc_xh,"default_lc_mc":default_lc_mc};
	 */
	public void batchAddJzwjgLc(Map<String, Object> view);
	/**
	 * params={"jg_dys":jg_dys,"jg_lcs":jg_lcs,"jg_mdyms":jg_mdyms,"jg_dxcs":jg_dxmcms};
	 * @param view
	 */
	void updateJzwjg(Map<?, ?> view);

}
