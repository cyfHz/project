package com.kingmon.project.psam.mlph.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.util.GeoUtils;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.psam.jlx.mapper.JlxMapper;
import com.kingmon.project.psam.jlx.model.Jlx;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.sqrxx.mapper.SqrxxMapper;
import com.kingmon.project.psam.sqrxx.model.Sqrxx;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.psam.xzqh.service.XzqhService;

@Controller
@RequestMapping("/psam/mlph")
public class MlphController extends KBaseController{
	
	@Autowired
	private MlphService mlphService;

	@Autowired
	private XzqhService  xzqhService;
	
	@Autowired
	private SqrxxMapper sqrxxMapper;
	
	@Autowired
	private JlxMapper jlxMapper;
	@Autowired
	private IOrganizationService orgService;
	@Autowired
	private IOrganizationUserService orgUserService;
	
	@AuthWidgetRule(value="mlph",desc="门楼牌号管理",operateType="R",refTable="DZ_MLPH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String index() {
		return "psam/mlph/mlph";
	}
	@AuthWidgetRule(value="mlph.list",desc="门楼牌号数据列表",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = { "/list" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet mlphList(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return mlphService.mlphList(params);
	}
//-----------------------------add------------------------------------------------		
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="mlph.add",desc="门楼牌号添加",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/enterAddMlph",method=RequestMethod.GET)
	public String enterAddMlph(Model model) {
		Map mlph = new  HashMap();
		Sqrxx sqrxx = new Sqrxx();
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		OrganizationUser orgUser=orgUserService.findByUserId(SecurityUtils.getUserId());
		sqrxx.setSqrgmsfzhm(orgUser.getUser_sfzh());
		sqrxx.setSqrxm(orgUser.getUser_name());
		sqrxx.setSqrlxdh(orgUser.getUser_mobile());
		sqrxx.setSqdwmc(org.getOrgna_name());
		sqrxx.setSqdwlxdh(org.getOrgna_tel());
		
		String orgCodeSubSix=org.getOrgna_code().substring(0,6);//当前登录人员的行政区划
		Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
		setDataAttribute(model, xzqh, "xzqh");
		setDataAttribute(model, mlph, "mlph");
		setDataAttribute(model, sqrxx, "sqrxx");
//		setDataAttribute(model, org, "org");
//		setDataAttribute(model,orgUser,"orgUser");
		return "psam/mlph/mlph.edit";
	}
	
	@AuthWidgetRule(value="mlph.add",desc="门楼牌号添加",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/add" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG add(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		mlphService.addMlph(params);
		return ajaxDoneSuccess("添加成功");
	}
	
//-------------------------------update----------------------------------------------		
	@AuthWidgetRule(value="mlph.save",desc="门楼牌号修改",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterEditMlph",method=RequestMethod.GET)
	public String enterEditMlph(String YWLSH, Model model) {
		Map<String,Object> mlph = new  HashMap<String, Object>();
		Sqrxx sqrxx = new Sqrxx();
		if(YWLSH!=null&&!YWLSH.isEmpty()){
			Map<String,String> params = new HashMap<String,String>();
			params.put("YWLSH", YWLSH);
			DataSet mlphList = mlphService.mlphList(params);
			if(mlphList.getTotal()>0){
				mlph = mlphList.getRows().get(0);
				if(mlph.get("SQRID") !=null && !String.valueOf(mlph.get("SQRID")).isEmpty()){
					sqrxx = sqrxxMapper.selectByPrimaryKey(String.valueOf(mlph.get("SQRID")));
				}
				if(mlph.get("SSXQDM")!=null){
//					Xzqh xzqh = xzqhService.selectXzqhByDzbm((String) mlph.get("SSXQDM"));
		//20160707改,一个行政区划代码查出两个行政区划。。
//					Xzqh xzqh = xzqhService.selectXzqhBydm((String) mlph.get("SSXQDM"));
//					if(xzqh!=null){
//						mlph.put("SSXQMC", xzqh==null?"":xzqh.getXzqhmc());
//					}
					List<Xzqh> xzqh = xzqhService.selectXzqhBydmList((String) mlph.get("SSXQDM"));
					if(xzqh!=null&&xzqh.size()>0){
						mlph.put("SSXQMC", xzqh.get(0)==null?"":xzqh.get(0).getXzqhmc());
					}
				}
				String SSZDYJXZQY_DZBM=(String) mlph.get("SSZDYJXZQY_DZBM");
				if(SSZDYJXZQY_DZBM!=null){
					List<Map<String,String>> map =xzqhService.selectXzqyMapByDzbm(SSZDYJXZQY_DZBM);
					if(map!=null&&map.size()>0){
						String SJXZQYMC="";
						for(Map<String,String> a:map){
							SJXZQYMC=a.get("MC");
						}
						mlph.put("SJXZQYMC", SJXZQYMC);
					}
				}
				String ssjlxxq_dzbm = (String) mlph.get("SSJLXXQ_DZBM");
				if(ssjlxxq_dzbm!=null && !ssjlxxq_dzbm.isEmpty()){
					Jlx jlx = jlxMapper.selectByPrimaryKey(ssjlxxq_dzbm);
					mlph.put("SSJLXXQ_JLXXQMC", jlx==null?"":jlx.getJlxxqmc());
				}
			}
		}
		Organization org=orgService.findOrgByUserId(SecurityUtils.getUserId());
		String orgCodeSubSix=org.getOrgna_code().substring(0,6);//当前登录人员的行政区划
		Xzqh xzqh=xzqhService.selectXzqhBydm(orgCodeSubSix);
		setDataAttribute(model, xzqh, "xzqh");
		setDataAttribute(model, mlph, "mlph");
		setDataAttribute(model, sqrxx, "sqrxx");
		return "psam/mlph/mlph.edit";
	}
	
	@AuthWidgetRule(value="mlph.save",desc="门楼牌号修改",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG save(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
		mlphService.saveMlph(params);
		return ajaxDoneSuccess("保存成功");
	}

//---------------------------- detail---------------------------------------------	
	@AuthWidgetRule(value="mlph.enterDetailMlph",desc="门楼牌号查看详细",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailMlph",method=RequestMethod.GET)
	public String enterDetailMlph(String YWLSH, Model model) {
		Map<String,Object>  mlph = new  HashMap<String,Object> ();
		Sqrxx sqrxx = new Sqrxx();
		if(YWLSH!=null&&!YWLSH.isEmpty()){
			Map<String,String> params = new HashMap<String,String>();
			params.put("YWLSH", YWLSH);
			DataSet mlphList = mlphService.mlphList(params);
			if(mlphList.getTotal()>0){
				mlph = mlphList.getRows().get(0);
				String sqrid = (String) mlph.get("SQRID");
				if( sqrid!=null && !sqrid.isEmpty()){
					sqrxx = sqrxxMapper.selectByPrimaryKey(sqrid);
				}
				if(mlph.get("SSXQDM")!=null){
					Xzqh xzqh = xzqhService.selectXzqhByDzbm((String) mlph.get("SSXQDM"));
					mlph.put("SSXQMC", xzqh==null?"":xzqh.getXzqhmc());
				}
				String ssjlxxq_dzbm = (String) mlph.get("SSJLXXQ_DZBM");
				if(ssjlxxq_dzbm!=null && !ssjlxxq_dzbm.isEmpty()){
					Jlx jlx = jlxMapper.selectByPrimaryKey(ssjlxxq_dzbm);
					mlph.put("SSJLXXQ_JLXXQMC", jlx==null?"":jlx.getJlxxqmc());
				}
				mlph=mlphService.selectUsers(mlph);
			}
		}
		setDataAttribute(model, mlph, "mlph");
		setDataAttribute(model, sqrxx, "sqrxx");
		return "psam/mlph/mlph.detail";
	}
	

//------------------------------------Mark------------------------------------------------	
	
	@AuthWidgetRule(value="mlph.batchMark",desc="门楼牌号批量标记",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterBatchMark",method=RequestMethod.GET)
	public String enterBatchMark(Model model) {
		return "psam/mlph/batch.mark";
	}
	
	@AuthWidgetRule(value="mlph.batchMark",desc="门楼牌号批量标记",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/mark/list" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  DataSet markList(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		return mlphService.markList(params);
	}
	
	
	@AuthWidgetRule(value="mlph.mark",desc="门楼牌号标记",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterMark",method=RequestMethod.GET)
	public String enterMark(String YWLSH,Model model) {
		setDataAttribute(model, YWLSH, "YWLSH");
		return "psam/mlph/mlph.mark";
	}
	
	
	@AuthWidgetRule(value="mlph.mark",desc="门楼牌号标记",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = { "/mark/save" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	public  @ResponseBody  KJSONMSG savemark(@RequestParam  Map<String,String> params ,HttpServletRequest request) {
		String ywlsh = params.get("YWLSH");
		String location = params.get("location");
		if(ywlsh==null||ywlsh.isEmpty()){
			ajaxDoneError("数据有误（YWLSH为空）");
		}
		if(location==null||location.isEmpty()||location.indexOf(',')<0){
			ajaxDoneError("经纬度有误");
		}
		mlphService.updateMlphLocation(ywlsh,GeoUtils.pgis2Point(location));
		return ajaxDoneSuccess("操作成功");
	}
	
//-------------------------------注销----启用---------------------------------------------------------	
		@AuthWidgetRule(value="mlph.settag",desc="门楼牌号注销",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = "/enterCancleMlph",method=RequestMethod.GET)
	    public Object enterCancleMlph(String ywlsh,Model model) throws Exception{
			Mlph mlph=mlphService.findMlphByYwlsh(ywlsh);
			setDataAttribute(model, mlph, "mlph");
			return "psam/mlph/cancleMlph";
		}
		@AuthWidgetRule(value="mlph.settag",desc="门楼牌号注销",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = { "/cancel" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
		public  @ResponseBody  KJSONMSG cancel(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {	
			mlphService.cancel(params);
			return ajaxDoneSuccess("操作成功");
		}	
		
		@AuthWidgetRule(value="mlph.settag",desc="门楼牌号启用",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = { "/enable" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
		public  @ResponseBody  KJSONMSG enable(@RequestParam  Map<String,Object> params ,HttpServletRequest request) {
			mlphService.enable(params);
			return ajaxDoneSuccess("操作成功");
		}	
		
//-----------------------------applyUseJlx-reviewJlx----------------------------------------------		
	
		@AuthWidgetRule(value="mlph.applyUseMlph",desc="门楼牌号申请",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = "/applyUseMlph", method = RequestMethod.POST)
		@ResponseBody
		public Object applyUseMlph(String[] ywlshs,String pzlx) {
			mlphService.applyUseMlph(Arrays.asList(ywlshs),pzlx);
			return ajaxDoneSuccess("门楼牌号提交添加申请成功 ");
		}
		
		@AuthWidgetRule(value="mlph.reviewMlph",desc="门楼牌号审核",operateType="W",refTable="DZ_MLPH",crudType=KConstants.OPER_UPDATE)
		@RequestMapping(value = "/reviewMlph", method = RequestMethod.POST)
		@ResponseBody
		public Object reviewMlph(@RequestParam String ywlsh,String spzt) {
//			mlphService.reviewMlph(ywlsh,"yes");
			mlphService.reviewMlph(ywlsh,spzt);
			return ajaxDoneSuccess("操作成功 ");
		}
}
