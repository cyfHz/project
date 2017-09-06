package com.kingmon.project.device;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BaseFilterBuilder;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.GeoPolygonFilterBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.FastJsonDataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.common.session.SessionUser;
import com.kingmon.project.auth.organization.service.IOrganizationService;
import com.kingmon.project.auth.organizationuser.model.OrganizationUser;
import com.kingmon.project.auth.organizationuser.service.IOrganizationUserService;
import com.kingmon.project.common.log.service.ILogLoginService;
import com.kingmon.project.elastic.model.GeoPoint;
import com.kingmon.project.elastic.model.Polygon;
import com.kingmon.project.elastic.service.ElasticService;
import com.kingmon.project.elastic.util.ElasticUtil;
import com.kingmon.project.psam.appException.service.IAppExceptionLogService;
import com.kingmon.project.psam.appMapUrl.model.mapurl;
import com.kingmon.project.psam.appMapUrl.service.ImapUrlService;
import com.kingmon.project.psam.appVersion.service.IAppVersionService;
import com.kingmon.project.psam.appVersonUser.model.AppVersonUser;
import com.kingmon.project.psam.appVersonUser.service.IAppVersonUserService;
import com.kingmon.project.psam.apponoff.model.AppOnOff;
import com.kingmon.project.psam.apponoff.service.IAppOnOffService;
import com.kingmon.project.psam.jlx.service.IJlxService;
import com.kingmon.project.psam.jzw.JzwJgNewUtil;
import com.kingmon.project.psam.jzw.mapper.JzwdyMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.mapper.JzwjgMapper;
import com.kingmon.project.psam.jzw.mapper.JzwlcMapper;
import com.kingmon.project.psam.jzw.model.Jzwdy;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.JzwfjPic;
import com.kingmon.project.psam.jzw.model.Jzwjbxx;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.JzwjgPic;
import com.kingmon.project.psam.jzw.model.Jzwlc;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.jzw.serivice.IJzwfjService;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
import com.kingmon.project.psam.jzw.serivice.IJzwjgService;
import com.kingmon.project.psam.jzw.view.JzwfjView;
import com.kingmon.project.psam.mlph.model.Mlph;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyFwJwry;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyFwCzrkService;
import com.kingmon.project.psam.sy.service.ISyFwJwryService;
import com.kingmon.project.psam.sy.service.ISyFwLdrkService;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkPicService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbPicService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.sy.service.ISyjwryPicService;
import com.kingmon.project.psam.sy.service.ISyjwryService;
import com.kingmon.project.psam.userAppInfo.service.IUserAppInfoService;
import com.sdwangge.policecloud.utils.MD5Util;

/**
 * 移动端调用接口
 * @author wwb
 *
 */
@Controller
@RequestMapping("/device")
public class DeviceDataController extends KBaseController{
	@Autowired
	private IJzwjbxxService jzwjbxxService;
	@Autowired
	private IJzwjgService jzwjgService;
	@Autowired
	private MlphService mlphService;
	@Autowired
	private IJzwfjService jzwfjService;
	
	@Autowired
	private JzwjgMapper jzwjgMapper;
	@Autowired
	private JzwfjMapper jzwfjMapper;
	@Autowired
	private JzwlcMapper jzwlcMapper;
	@Autowired
	private JzwdyMapper jzwdyMapper;
	
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IOrganizationUserService organizationUserService;
	@Autowired
	private ILogLoginService logLoginService;
	
	@Autowired
	private ISyFwCzrkService fwCzrkService;
	@Autowired
	private ISyFwLdrkService syFwLdrkService;
	@Autowired
	private ISyRkglCzrkService  syRkglCzrkService;
	@Autowired
	private ISyRkglLdrkdjbService syRkglLdrkdjbService;
	@Autowired
	private ISyjwryService syjwryService;
	@Autowired
	private ISyFwJwryService syFwJwryService;
	@Autowired
	private ISyFwjbxxService fwjbxxService;
	@Autowired
	private ISyRkglCzrkPicService  syRkglCzrkPicService;
	@Autowired
	private ISyRkglLdrkdjbPicService  syRkglLdrkdjbPicService;
	@Autowired
	private ISyjwryPicService  syjwryPicService;
	@Autowired
	private IJzwfjPicService jzwfjPicService;
	@Autowired
	private IJzwjgPicService jzwjgPicService;
	
	@Autowired
	private IAppVersionService appVersionService;
	@Autowired
	private IJlxService jlxService;
	@Autowired
	private IAppExceptionLogService appExceptionLogService;
	
	@Autowired
	private ImapUrlService urlService;
	@Autowired
	private IAppOnOffService appOnOffService;
	@Autowired
	private IAppVersonUserService appVersonUserService;  
	
	@Autowired
	private IUserAppInfoService userAppInfoService;
	
	/**
	 * 登陆接口
	 * @param request 
	 * @param response
	 * @param model
	 * @param username 用户名
	 * @param password 密码
	 * @param areaCode
	 * @param imei imei号
	 * @return 返回用户信息
	 */
	@RequestMapping(value="login")
    @ResponseBody
    public Object login(HttpServletRequest request,HttpServletResponse response,Model model,
    		String username,String password,String areaCode,String imeiNum) {
		password = MD5Util.EncryptMD5(password);//新网格提供数据库加密算法
    	OrganizationUser organizationUser =organizationUserService.findByLoginnameAndPws(username, password);
    	if(organizationUser==null){
    		return ajaxDoneError("用户名或密码错误");
    	}
    	if(organizationUser.getEnabled()==null||organizationUser.getEnabled().equals("0")){
    		return ajaxDoneError("该用户已禁用，请联系管理人员");
    	}
    	if(areaCode==null||"".equals(areaCode)){
    		return ajaxDoneError("地域区划代码不能为空");
    	}
    	//登录 查询设备Id 2016-0708
    	long countImei=appOnOffService.countImei(imeiNum, organizationUser.getUser_sfzh());
    	if(countImei<=0){
    		 return new KJSONMSG(300,"当前设备没有注册");
    	}
    	SessionUser user=organizationUserService.loadUserInfor(organizationUser.getAppuser_id());
    	  if(user==null || "".equals(user)){
    		  return new KJSONMSG(300,"登录失败 ");
    	  }
    	  AppVersonUser appversionUser=appVersonUserService.selectAppVersion(organizationUser.getAppuser_id());
      	String isValid="";
      	if(appversionUser==null || appversionUser.getIsvalid()==null || "".equals(appversionUser.getIsvalid())){
      		isValid="0";
      	}else{
      		isValid=appversionUser.getIsvalid();
      	}
    	HttpSession session = request.getSession();
    	session.setAttribute(KConstants.SESSIONUSER, user);
    	//添加登陆日志
        logLoginService.addLogLogin(user,request,true,null,"1",imeiNum);
    	List<mapurl> mapurl=urlService.findMapUrlByAreaCode(areaCode);
    	AppOnOff appOnOff=appOnOffService.findStatusByAreaCode(areaCode);
    	String status="";
    	if(appOnOff!=null && !("".equals(appOnOff))){
    		 status=appOnOff.getOnoff();
    	}else{
    		status="off";
    	}
    	if("".equals(mapurl)||mapurl.size()==0||mapurl==null){
    		return ajaxDoneError("地图信息不能为空");
    	}
        Map<Object, Object> map = Maps.newHashMap();
        map.put("authToken", user);
        map.put("mapUrlList", mapurl);
        map.put("status", status);
        map.put("isValid", isValid);//是否是最新版本
        return new KJSONMSG(200,"登录成功 ",map);
    }
//=============================================================================
	
	@AuthWidgetRule(value="device",desc="终端信息采集",operateType="R",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = { "/","" }, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public Object device() {
		return new KJSONMSG(200,"终端采集测试 ");
	}
	
	/**
	 * 终端信息采集
	 * @param view 为 map<String,Object>, 用的到key 参数为 isJZW
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="device.acquisition",desc="终端信息采集",operateType="W",refTable="DZ_JZWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/addJzwInfoAcquisition",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwInfoAcquisition(@RequestBody Map view){
		String jzwjbxxId = jzwjbxxService.addInfoAcquisition(view);
		
		//0618 采集建筑物同时 保存建筑物照片
//		Map<String,Object> mapPic=Maps.newHashMap();
		view.put("jzwid", jzwjbxxId);
		int a=AddJzwPic(view);
		
		Map<String ,Object> map=Maps.newHashMap();
		Boolean isJZW = (Boolean) view.get("isJZW");
		if(isJZW){
			Jzwjbxx jzw=jzwjbxxService.getJzwJbxxById(jzwjbxxId);
			map.put("jzwid", jzwjbxxId);
			map.put("lat", jzw.getZxdzzb());
			map.put("lon", jzw.getZxdhzb());
			map.put("jzwmc", jzw.getJzwmc());
			map.put("jzwlx","jzw");
		}else{
			Mlph mlphx=mlphService.findMlphByYwlsh(jzwjbxxId);
			map.put("jzwid", jzwjbxxId);
			map.put("lat", mlphx.getZxdzzb());
			map.put("lon", mlphx.getZxdhzb());
			map.put("jzwmc",mlphx.getDzmc());
			map.put("jzwlx","mlph");
		}
		KJSONMSG msg=new KJSONMSG(200,"信息采集添加成功 ",map);
		return msg;
	}
	//根据建筑物Id判断建筑物是否存在
	@AuthWidgetRule(value="device.jzwjg",desc="建筑物结构信息是否存在",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value ="/checkIsHaveJg",method=RequestMethod.POST)
	@ResponseBody
	public Object checkIsHaveJg(String jzwId) {
		KJSONMSG msg=jzwjgService.validateJzwjgByJzwId(jzwId);
		return msg;
	}
	
	/**
	 * 建筑物结构添加
	 * @param view 为map<String,Object>
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@AuthWidgetRule(value="device.addjzwjg",desc="建筑物结构添加",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/addJzwjg",method=RequestMethod.POST)
	@ResponseBody
	public Object addJzwjg(@RequestBody Map view) {
		jzwjgService.addAnddBuildJzwjg(view);
		return new KJSONMSG(200,"建筑物结构采集成功", null);
	}
//	public Object 
	/**
	 * 建筑物结构数据加载
	 * @param jzwId 建筑物id
	 * @return
	 */
	@AuthWidgetRule(value="device.showjzwjg",desc="建筑物结构加载",operateType="W",refTable="DZ_JZWJG")
	@RequestMapping(value = "/loadJzwjg", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwjg(String jzwId) {
		Jzwjg jzwjg1=jzwjgService.findJustJzwjgByJzwId(jzwId);
		if(jzwjg1==null || "".equals(jzwjg1)){
			return new KJSONMSG(300,"结构信息不存在");
		}
		String jzwjgId = jzwjg1.getJzwjgid();
		Jzwjg jzwjg = jzwjgMapper.selectByPrimaryKey(jzwjgId);
		if(jzwjg==null || "".equals(jzwjg)){
			return new KJSONMSG(300,"未查询到建筑物结构");
		}
		List<Jzwdy> sortedJzwdyListx=jzwdyMapper.selectSortedJzwdyByJzwJgid(jzwjgId);
		List<Jzwlc> sortedJzwlcListx=jzwlcMapper.selectSortedJzwlcByJzwJgid(jzwjgId);
		List<Jzwfj> sortedJzwfjListx=jzwfjMapper.selectSortedFjByJzwjgId(jzwjgId);
		KJSONMSG mgs=JzwJgNewUtil.validateJzwjg(jzwjg, sortedJzwdyListx, sortedJzwlcListx, sortedJzwfjListx);
		if(mgs.getStatusCode()!=200){
			return new KJSONMSG(300,mgs.getMessage());
		}
		//校验通过，但是此时还没有更新Isvalid字段，则进行更新  Isvalid=1，
		if(jzwjg.getIsvalid()==null||!(KConstants.ISVALID_JZWJG_YES.equals(jzwjg.getIsvalid()))){
			jzwjg.setIsvalid(KConstants.ISVALID_JZWJG_YES);
			jzwjgMapper.updateByPrimaryKeySelective(jzwjg);
		}
		
		//校验通过，但是此时还没有生成 房间坐标，此时要进行坐标生成
		if (jzwjg.getIsbuild()==null||!(KConstants.ISBUILD_JZWJG.equals(jzwjg.getIsbuild()))) {
			JzwJgNewUtil.initBuildJzwfjZB( jzwjg,sortedJzwdyListx,sortedJzwlcListx,sortedJzwfjListx, jzwfjMapper,jzwlcMapper,jzwdyMapper,jzwjgMapper);
			jzwjg.setIsbuild(KConstants.ISBUILD_JZWJG);
		}
		List<Map<String,Object>> list = jzwfjService.findFjMapByJzwjgId(jzwjgId);
		List<String> czrkFjList=fwCzrkService.loadCzrkFjByJzwId(jzwId);
		List<String> ldrkFjList=syFwLdrkService.loadLdrkFjByJzwId(jzwId);
		List<String> jwryFjList=syFwJwryService.loadJwryFjByJzwId(jzwId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("jzwjg", jzwjg);
		map.put("jzwfj", list);
		map.put("czrk", czrkFjList);
		map.put("ldrk", ldrkFjList);
		map.put("jwry", jwryFjList);
		Map<String, String> widthAndHeight = jzwfjService.calculateJzwWidthAndHeight(list);
		map.put("widthAndHeight", widthAndHeight);
		
		KJSONMSG kjsonmsg = new KJSONMSG(200,"数据加载成功", map);
		return kjsonmsg;
	}
	/**
	 * 建筑物房间拆分
	 * @param view 为 map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.chaiFen",desc="建筑物房间拆分",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/chaiFen", method = RequestMethod.POST)
	@ResponseBody
	public Object chaiFen(@RequestBody JzwfjView view){
		Map<String, Object> map = jzwfjService.chaiFen(view);
		return new KJSONMSG(200,"房间拆分成功", map);
	}
	/**
	 * 建筑物房间合并
	 * @param view 为 map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.heBing",desc="建筑物房间合并",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/heBing", method = RequestMethod.POST)
	@ResponseBody
	public Object heBing(@RequestBody JzwfjView view){
		Map<String, Object> map = jzwfjService.heBing(view);
		return new KJSONMSG(200,"房间合并成功", map);
	}

//=========================人口采集====================================================
	/**
	 * 进入常住人口采集页面
	 * @param jzwfjid 建筑物房间id
	 * @return
	 */
	@AuthWidgetRule(value="device.savefwCzrkAccInfo",desc="进入常住人口采集页面",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwCzrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object enterSyFwCzrkAccInfo(String jzwfjid) {
		KJSONMSG czrk = fwCzrkService.loadFwCzrkAccInfoApp(jzwfjid);
		return czrk;
	}
	
	/**
	 * 根据房间和身份证号加载常住人口信息
	 * @param sfzh 身份证号
	 * @param jzwfjid 建筑物房屋id
	 * @return
	 */
	@AuthWidgetRule(value="device.loadCzrkInfo",desc="根据房间和身份证号加载常住人口信息",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadCzrkInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object loadCzrkInfo(String sfzh,String jzwfjid) {
		SyFwCzrk czrk=fwCzrkService.validateCzrk(sfzh,jzwfjid);
		if(czrk!=null){
			return new KJSONMSG(300,"该采集信息已经存在");
		}
		SyRkglCzrk rkczrk=syRkglCzrkService.selectCzrkInfoByZjbh(sfzh);
		if(rkczrk!=null){
			Map<String,Object> strczrk=new HashMap<String,Object>();
			strczrk.put("gmsfhm", rkczrk.getGmsfhm()==null ?"":rkczrk.getGmsfhm());
			strczrk.put("xm", rkczrk.getXm()==null ?"":rkczrk.getXm());
			strczrk.put("zym", rkczrk.getZym()==null ?"":rkczrk.getZym());
			strczrk.put("xb", rkczrk.getXb()==null ?"":rkczrk.getXb());
			strczrk.put("csrq", rkczrk.getCsrq()==null ?"":rkczrk.getCsrq());
			strczrk.put("mz", rkczrk.getMz()==null ?"":rkczrk.getMz());
			strczrk.put("lxfssj", rkczrk.getLxfssj()==null ?"":rkczrk.getLxfssj());
			strczrk.put("byzk", rkczrk.getByzk()==null ?"":rkczrk.getByzk());
			strczrk.put("zzmm", rkczrk.getZzmm()==null ?"":rkczrk.getZzmm());
			strczrk.put("hyzk", rkczrk.getHyzk()==null ?"":rkczrk.getHyzk());
			strczrk.put("xl", rkczrk.getXl()==null ?"":rkczrk.getXl());
			strczrk.put("xxbcQq", rkczrk.getXxbcQq()==null ?"":rkczrk.getXxbcQq());
			strczrk.put("xxbcWx", rkczrk.getXxbcWx()==null ?"":rkczrk.getXxbcWx());
			strczrk.put("xxbcWb", rkczrk.getXxbcWb()==null ?"":rkczrk.getXxbcWb());
			strczrk.put("xxbcDzyx", rkczrk.getXxbcDzyx()==null ?"":rkczrk.getXxbcDzyx());
			strczrk.put("yhzgx", rkczrk.getYhzgx()==null ?"":rkczrk.getYhzgx());
			strczrk.put("zy", rkczrk.getZy()==null ?"":rkczrk.getZy());
			strczrk.put("xjzdzz", rkczrk.getXjzdzz()==null ?"":rkczrk.getXjzdzz());
			strczrk.put("xjzdzzfjh", rkczrk.getXjzdzzfjh()==null ?"":rkczrk.getXjzdzzfjh());
			strczrk.put("dzmc", rkczrk.getDzmc()==null ?"":rkczrk.getDzmc());
			strczrk.put("jzwfjid",jzwfjid);
			byte[] data=syRkglCzrkPicService.loadRkglPic(sfzh);
			String str1="";
			if(data!=null){
				str1=java.util.Base64.getEncoder().encodeToString(data);
			}
			strczrk.put("xp",str1);
			return new KJSONMSG(200,"数据加载成功", strczrk);
		}
		//API ----查询身份证库;
	   // Map<String,String> strReturns=RkQueryUtil.queryCzrk(sfzh);
	
		Map<String,String> strReturn=syRkglCzrkService.queryRkInfo(sfzh);
		if(strReturn!=null){
			Map<String,String> strReturns=new HashMap<String,String>();
			strReturns.put("xb", strReturn.get("XB")==null ?"":strReturn.get("XB"));
			strReturns.put("zym", strReturn.get("CYM")==null ?"":strReturn.get("CYM"));
			strReturns.put("byzk", strReturn.get("BYZK")==null ?"":strReturn.get("BYZK"));
			strReturns.put("hyzk", strReturn.get("HYZK")==null ?"":strReturn.get("HYZK"));
			strReturns.put("xl", strReturn.get("WHCD")==null ?"":strReturn.get("WHCD"));
			strReturns.put("mz", strReturn.get("MZ")==null ?"":strReturn.get("MZ"));
			strReturns.put("xm", strReturn.get("XM")==null ?"":strReturn.get("XM"));
			strReturns.put("csrq", strReturn.get("CSRQ")==null ?"":strReturn.get("CSRQ"));
			strReturns.put("xp", strReturn.get("XP")==null ?"":strReturn.get("XP"));
			return new KJSONMSG(202,"数据加载成功", strReturns);
		}
//		
		//----------------------
		//没查询到数据，进习 手填 
		return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
	}
	/**
	 * 常住人口信息采集
	 * @param moveczrk 为Map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.savefwCzrkAccInfo",desc="常住人口信息采集",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/savefwCzrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object savefwCzrkAccInfo(@RequestBody Map moveczrk){
		SyRkglCzrk czrk=new SyRkglCzrk();
		czrk.setGmsfhm((String)moveczrk.get("gmsfhm"));
		czrk.setXm((String)moveczrk.get("xm"));
		czrk.setZym((String)moveczrk.get("zym"));
		czrk.setXb((String)moveczrk.get("xb"));
		czrk.setCsrq((String)moveczrk.get("csrq"));
		czrk.setMz((String)moveczrk.get("mz"));
		czrk.setLxfssj((String)moveczrk.get("lxfssj"));
		czrk.setByzk((String)moveczrk.get("byzk"));
		czrk.setZzmm((String)moveczrk.get("zzmm"));
		czrk.setHyzk((String)moveczrk.get("hyzk"));
		czrk.setXl((String)moveczrk.get("xl"));
		czrk.setXxbcQq((String)moveczrk.get("xxbcQq"));
		czrk.setXxbcWx((String)moveczrk.get("xxbcWx"));
		czrk.setXxbcWb((String)moveczrk.get("xxbcWb"));
		czrk.setXxbcDzyx((String)moveczrk.get("xxbcDzyx"));
		czrk.setYhzgx((String)moveczrk.get("yhzgx"));
		czrk.setZy((String)moveczrk.get("zy"));
		czrk.setXjzdzz((String)moveczrk.get("xjzdzz"));
		czrk.setXjzdzzfjh((String)moveczrk.get("xjzdzzfjh"));
		czrk.setDzmc((String)moveczrk.get("dzmc"));
		czrk.setSsxq((String)moveczrk.get("ssxq"));
		czrk.setJlxc((String)moveczrk.get("jlxc"));
		czrk.setMlph((String)moveczrk.get("mlph"));
		czrk.setMlxz((String)moveczrk.get("mlxz"));
		czrk.setSspcs((String)moveczrk.get("sspcs"));
		czrk.setLrfs((String)moveczrk.get("lrfs"));
		czrk.setJwzrqdm((String)moveczrk.get("jwzrqdm"));
		czrk.setJzwfjid((String)moveczrk.get("jzwfjid"));
		czrk.setDzbm((String)moveczrk.get("dzbm"));
		czrk.setJzwid((String)moveczrk.get("jzwid"));
		
		czrk.setMacAddress((String)moveczrk.get("macAddress"));
		czrk.setTfCardNum((String)moveczrk.get("tfCardNum"));
		czrk.setImeiNum((String)moveczrk.get("imeiNum"));
		czrk.setSimNum((String)moveczrk.get("simNum"));
		
		try {
			byte[] pic = null;
			if(!"".equals(moveczrk.get("xp"))&& moveczrk.get("xp")!=null){
				Decoder decoder=java.util.Base64.getDecoder();
				 
				pic = decoder.decode((String) moveczrk.get("xp"));
			}
			 
	//		xp="/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAClANwDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD3+iiigAooooAKKKKACiiigAooooAKKKKAMfxA2rx6aZ9EWKS8hcP9nl4Fwg+9GG/hJHQ+oFJ4e8SWHiKzaa0dkmiPl3FtMNstu46o69Qf0PareqR302nTpptxFBekZhkmj3oGBzhh3B6cc15xqFxpWpaxF/bb3Hg/xci7Ir2NwI7gDoFkPySp/stgjpQB6pkGlrgF8ReLfDq/8TzRl1qxHTUdGGXx6vCef++Sa3dD8c+G/EJ2afqsDXA+9bSt5cyn0KNg0AdFWD4m8Mx+IYrV1uZbS9spfOtLmMBjG+MHKtwwI4wfwIrdByaWgDhF8V6x4Xu4bbxlbQGzlYRxazZAiHceAJUPMf1yRXcB1kQMrBlIyCDwaivLK3v7Sa1u4kmt5kKSRuMhlPUEVx/wxlkXw/facZnng0zUriytpWOS0SN8oz3wDj8KAOgstdiuvEOo6K8TQ3Noscq7j/ronHDr7Bsqfce9bFcNq8yyfFjw3DZHNzDZ3T3u3+G3YKEDfVwCPpXcdqAFoqva31tfCU208coikaJyjZ2upwyn3FWKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooozigDA13Qr3UZo7rTddvdLuo12jygskLjOfniYYP1GDXPahY+Ons2tL+w8MeIbQ8MsyvblvqpDLXQ+KbLSrrS/N1fUJ7C2gJczxXr223jHLKRn6GvLpoPD2rsU0PQdf8SoOPtV/qc0Vqf8AgTt8w+gpNpbjSb2Kd3YPo8haLQLnQ2HUaX4sijUH2SQ4/QVyev6laXqf8TPUNRnkQ/K1xPY3bL/wJSG/I13lv4Ev5CGXTfCWkL6QaabyT8WlOP0rQTwDNtxJ4inB7i3061iH4AIaxdeC6mqoyfQ8t8PfE3W/DN0os9TmvLRD/wAet0xaN19BklkPuCRX0L4f8faDr3h0ayl7Fawqds6XMioYXxna2f09a4a7+GNrewmK413UpFbqGig/ogrz/wAbfDF/DlnbXemXE19DJKIXjkQB1c/cxjggnj1zRGvBuyCVGS1Z6X4v+M2lW9pNYeGZTqGpygxxzINsMRPG7ccbiPQce9Zvh3XtY0/w7aaPpcnhzRYI1+e81LVI55mc8u5jQ43EknBNeQ6LpcGouIJJ7a2uTgxm6Z4lcE4Hz4ZRk8fMAPeu3h0zXPD+P7R0WySBes82hw3sYHu8WHx+FacyI5X0PRdF1/wP4QiuZZ/FVvqeq3jB7u7D+dNOw6AKmcKOyjgVfk13xD4vQ2ugafdaRp8nyy6tfx+XIF7+TEeS3ozYA61geGdS16+jDeG/+EBuCBz9mSWF1/3kxuFdGLD4jXpHna5oWnIeptLJ5m/N2xVEnT6LpNnoWlwadYR7LeBcKCcknOSzHuxPJPcmtDIzjPPpXFp4DvbnnVvGXiC7JPKQzLaofbEYz+tbei+FtI8PGVtNtPLklAEkryNJI+PVmJJpiNqijtRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAE4rmfFfi6Lw8IbS3t2vtYu8i0sYmAZ8dWY/woO7GrvijxBbeGNBn1O4BfZhYoV+9NI3CovuTx+tcj4c0W4tTPq+ruJ9d1D5rqXtEv8ADCnoq9Pc1lVqqmrmlOm5sr23haXUrxNV8WXC6rqAO6O35+y2vtHH3P8AtNXT4wAOw6D0oorzZzlPVnbGKjsFFFI7BEZ2zhQScDNSWLXN+MmFzZWejxHN3f3kIiUHkKjh3f6KF6+pAqaXVtXvv3ej6PJGp4+16l+5jA9RGPnb6cVWeK28J2l1r2qS3Oo3zrtnutg+VeoRVyFjTPbPJ9TWkY2d+pDdznte0i3ttV1/yI4g1vbDWLYSKCmGLLPCwPWN9oOOgJyK27KSTwxPY7Hlfw/flEhWViz2EjjKJu6mM52jPKnjoao2Oial4q1GfWdXDWOm3kcSLpuMyyRISVWVuykksVHJ4B4roPGCofB+qKwyWg2xgdfMJATHvuxitJS2TJS6i6t4T0bWZfPuLQRXi8peWxMM6H1Drz+earwat4p8InN4ZPEejr96VUC3sC+pA4lA9sGuhAYKA5ywADH37/rS1MK0oaIcqUZI19G13TfEGmx6hpd3Hc20nRkPIPcEdQfY81oA5FeX6rpd5oWov4m8NRgXYGb6wB2x38Y68dpAOjd+9d/oWt2XiHRrbVNPl8y2uE3Ke6nupHYg5BHtXfTqKaujinBweppUUUVoQFFFFABRRRQAUUUUAFFFFABSE0E4Fea/EL4iyaRMdD0LY2qlQZ53G5LRT0yO7nsO3BNKUlFXZdOnKrJQgrtnaaz4l0Xw9EJNW1O2tAfuiR/mb6L1P4CuZHxg8FmTZ/aUwX/nobSUL+e2vEjF5t093cySXV5IcyXE7b3Y/U9PoKkZ9iMxJwoz+VcTxnvWSPoYcPSUHKpOzPVLjVLXxx46tnsrlLrRdEhW4V0OUlupB8v/AHwvPsTXVVxnwu0/7H4JguWXEt/K9059icL+g/WuzrGtNylqeZCCgrIKKKKyLCiiorm5gs7aS5uZkhgiXdJI7YVR6k0ASHODjr71jR+H1uLxLzWLk6jPG26FGTZBAfVI+ct/tMSfpUmneJdJ1W5FtaXLGZlLIssLxeYo6lNwG4D2rWqruItGFIyq4AZQwBBwRnkdKWmSzRQIXmkSNBxudgo/M1ID6KQEMoYEFSMgg8YpaBoP51zugSf8Iv4/l0ofJpevK9zbLnCxXSj94o9Aww2PUV0Vcv49SSLw4NXgH+k6PcR38R9kYbx+Kk1tQnyz9TKtHmieoDpRUVvPHc20U8R3RyIHUjuCMipa9M4AooooAKKKQkDr3oAWjNcD4y+J2n+HLl9NsYTqerL96CN9qQ/9dH7fQc/SvLtU8ZeLdcZjd609lCelvpw8oD2L/eP51lOtCGkmduGy/EYn+HHTufRjypGMu6qD/eOKPMXbu3Lt65zxXylJp8E7b7jzbhz1eaZnP6mkGmWgXb5bbf7vmNj8s1h9dgekuH8R/Mj3jxj8RtJ0Cxmt7K7gu9YddtvbRNvwx6F8cKB1564rxCNHBd5pGmnlYyTTNy0jnksT70kUEVumyGJI19EXFJNcwW4zNNHH/vMBXLWryq6R2PZy/LaeBTnN+93Jaqam5j0u6YdfKNCapYO2Fu4c+7YpmrkPot0ykEeX1HSsoQakro7q9enKjPll0Z714ftxaeG9LtlGBHaRLj/gArRqrprB9KsmHRreMj/vgVaq5bs+NWwUUUUgCop7eC6jEdxCkqBg4V1yMg5Bx7GpaKAYH5iC3zEdCecUUYI6gj6ijtQLQKqXul6fqRj+3WUF0Is7BMgcLng4B4q3RRcLGbpWjQaKZorJ3SzkIeO1JykLfxbM8hT/AHegPT0rSoopt31BIKp6tbLe6NfWrjKzW0kZB91Iq5TJSBE5PQKf5GiO6B7HF+EPi7oVl4W0ix1FdRSa3tY4ZZhal48qMdRyenpXpGh+JtG8RwmbSdSt7tVHzLG3zL9VPI/GvmLRv+QVDjuWx+dbWg6HqmveIUj0DNvqFviSS+Vygtx23EfeJ/u9/pXbDENz5bHTXymNPDKvz2ufTVFU9LivIdNgj1CeK4vFQCWWJNiu3qF5xn0q5XWeGITiuC+JnjR/D2mpp2mSL/bN6CIj18iP+KU/ToPU/Sul8SeI9P8AC+iT6nqMwSKMYRM/NK/ZFHcn/wCvXzbfeIE1bVrrV9Svbc3t2wLKJAViQfdjX2A/M5NZVZuEdNzuwGGjXqpTdorcWC3S3QqpZmYlnkY5Z2PVie5NS1FFdW85xDPFIfRXBqWvHnzXvI++oqmo2p2t5BRRRUmotrZXmsarb6RpwAuZwWaRhlYYx1c/0r1nQ/A2haFEvl2Udzdfx3V0gkkc+vPAHsK534U2SNDq+rMoMstz9mQ+iIBx+JP6V6G4do3WNtjFSA23OD2OO/0rp+FcqPjcdiJV6rb2WxXn0nT7uIx3GnWs0eMFXt1Yfyrh/FHwv0qfTbybRxJp1yInYxRktFLgE7Sp6Z9RWld+DNLFrNf+INV1O+aJDJNPLeNEiADJKohAUe1c9oFt4s/eap4b85NEI3W9hrNyZHul9VJGYwR0yfzrSC6pnA5NbHaeC7wah4K0a5zktaIp+qjaf5Vu1wHwqv2bSdS0iaCS2m0+8fFvL96NHJYKfociu/rOorSZUHdBRRRUFBVa+tXvLOS3S5mtjJgGWEgOBnnBPQkcZ7dqs0U7gY9j4eg0u7Wexu72KPBEtvJcNNHJ/tHeSVYHByCM96u6hb3V1aeTaXps5GYbpljDOq99oPAb0JBx6VaZPMRk/vAr+fFeQ+HfF+q6VZfZLq9kmji/dxia3ExTaWUqWDK2BgY61Su9SoUpTvyK9j03SYNStBPb392LyJWBt7lgFlZD1WQAY3D+8OoPrUF9b+Iri7laz1CysoI8CFGtjMZjjkyHI2jPGF54zXIweLvEGua7b6XpUlpEkhLSXBtGBSNT8zAMx7YAz3YV6P8A5zQ7rUiUHF8rViG1eeS1ia6iSK4KjzEjfcqt3we4qaiioBBWfr12th4e1K7Y4ENrK3/jp/ritCuO+Jd1s8LLpiyKkmqXMdoGZgAFJ3OTnsAP1qoK8kglojxzT7+2t9Ogg3mSVYxuSNSxz1xxXuPwhu9Bj8NpZ2moW82sTE3N9FnbIHbsVOCQowOOODWEfHXhPQLJ7Lw9CLiVP3UK2lsRFJKeFBkx1J781Hq3gvxXqrW2oXHiSwi1O3cSRNHZCMQN6LIPmx25yDXVTcacnJ6XHisTUxFOMOkT2ocinVwOneL9b0vU7Ww8VWtmsF24hg1KyZvK809EkVuULdj0zxXfDpXZGSkro8xxcdGcuvgLRJZ1uNTim1e5T7supymfYPRVOFX8BWzHomlRJsj0yyRPRbdAP5VfopiOa1bwF4W1qMpeaHZk44kijEbj6MuDXnXiD4RajpqtceG7tr2Acmyu3AkA/wBiTv8ARvzr2nFBXIxUShGatJG9HE1aMuanKx8sCQrcyWs8UltdxHElvMhR0PuD/On19BeKPBWjeLLdV1G3P2iMfubuE7Jovo3p7HivFvEvg7XPCBeW6Q3+lg8X0CcoP+mqDlfqOK4K2EcdYH1GBz2FT3K+j/A2vhZqccM2q6HKwWUzfbIAT99GADY+hA/OvSq+fVdzJBf6fdGG5hO+C4jOce3uOxFegaL8UbQolv4jhawuRwbhELwSe+Ryv0NZ/FqtzixuElSm5x1i9mdH4y0y61nw3Jp9qhczzwLKoIGYvMUv+grfCqg2IAqrwoHYdqy4vEugzxebFrWnsmOv2lP8azNQ+IXhfTgQ2rRXMo6RWmZmJ/4Dx+tFpWtY89LW5R11v+Ed8eaZr33bLUl/s69bsr9YnP8AKu16V4l4p8Taj4yjNq0bafpQO5YM5klYdGcjoAecCu/+HviZte0P7Ldv/wATOwxDcA9XA+7J9CP1BqpK680ayw9SnFSkrJnXUUUVkRcKKKKACvO9c8G26alfz/Y79o7xjNDc2GZGtpT95XhzhkJ5BA7kHHWvRKKqMrDTlF3i7HH+AvDdzpFnNf6jGI9QuwFMfXyo16Ln1JJJ/Adq7Ciik3dhKTk7yeoUUUUhB0rz7UtNtfHPjua3vd0mjaHEI5Bv2pJcvyVJ9ABz9K6bxXr6+HdClu1Aa7c+VaRn+OUjj8B1PsK8Xi06FUInL3DOxklMjkh3PViM4ya9PAYCpiLyjoeZj8wp4VpS1bNjwpaS3vjnSbE3Mk2gWM13LpqS4+YRtjJx1wx4J5+Wu/8AGFx9vutK8MwndPf3CTT46x28RDsx9MkAfnXmljooOifbre7umOm3u66toQfOtkdh+/gZeQCuMqQQdpqfSPGA0HWtUlj8nU7m7uPIh1XU7oxMIwuV8xMZVO+RgHFZ1aTU7dUb0qqlBSWzPT9entdYvx4WkidzeW0k88sbANbIpGxxkfeL4x9K5K/+MereFb6bQ9U0+HULyzby3u0k8vzh1VivYlSM++ags9fi06z1CbTr+PU9avTv1DWpFKWdoOw3MOQoztQck/lSL8O/EviSOPVoPIWGeNfLOoL+/kVQFDv6Fgu7HbdiilGUQqyiz36iiiuo5wooooAQ01kDAggEEYNPooA838SfCTStSmkvNFmOkXrnLCJd0Eh/2o+31XFedap4L8WaKXF1ozXsA/5b6efNBHqU+8Pyr6NpCM1lOjCWrR24fMK9BWg9Oz2Pkmb+zEkxdWghlHVZ7Zkb8iKlgurTIS0idz/dt7dmP6Cvq9kR+GUN9RmuU8XeNbDwrAsMca3OpSg+VaowGAOrOR91R+Z7VmsGpOybOx57OmubkivOx4ZLY6zHYyXr6Hew2qDLT3aiBPYDdySewAJNVdIutY0zV4tat0ihniXabfJPnp3Rz24HHocVt6zrureIrtZ9WvPNEZ3QwRrsiiPqo6k+5JP0qh7DpXt4TJKcY3qLVnzGZ8U4jEWhB6I9m0PXLLxBpcd/YuSjHa8bfficdUYdiP8A69aNeHadqF/oWonUNMkUSsAs0D/6u4Udm9D6MOR9K9T8O+LdN8RxlIHMF8gzLZzECRPp/eX3FeLjsvqYae3u9z0cBmNPFQ397sb1FFFecejcKKKKACiiigLhUN1dQWVpNdXUyQwRKXkkc4VQO5qtq+s6fodi13qNykEXRc8s59FXqx9hXlHiLxDeeKrhfPjNtpkTbobMnJdh0eTHBPovQfWuzB4KpiZpRWnc4sZjqeFg5SevbqQ63rU3ibWP7RkVo7WIFLKF+qoersP7zfoMCqdL260yWWODBkcLk4APUn0A719tQo08NTUFofDV61TFVXJ6tkkbSw3KXVrNLbXEYwk0L7GX29x7HitjTrHWvHurx6ZcSW9zGoH2u+ksIvNgi7gSAcO3Qd+c9queHvAWt+IXSWaOTTNPPJnnTErj/YQ9P95vyNez6HoWn+HtPSx063EUK8k5yzt3Zj1Zj6mvJx9ahN+6ry7nt5bQxFNXnK0exi6f8NvCGnzxT2+h2/mRNuTzGaRVb1CsSM++K6zaPSlorzLHrhRRRQAUUUUAFFFFABRRRQBwfju+vTrmjaJHqj6ZZX8c7zzwMElYxgEIrn7gIJ5HPFeQ6tpcfh7xFqFo6NCks3mW8krFvOiIG0h2+8RyDz1r3vxf4XtPFuhSadclUfcskMpQN5bqcg47jsR3BrkrT4VKXFx4h8RX2pqZPOubeQhbeRlOV4OSqj0B578VeHqyo1VUWpliaMa9L2bdjyuBZLuOaS1hkuI4EMk0kQBSNR1LNnaPzqCGdrmJZYlCxuMhm5JH0H9a6H4ieNv7aRNL0ZY4fDkNwiSGNQouyG6gDpGCBj1PPTFc3px223kH78DGMj8ePzFe3hsZOrV5J6XV0eJisvhQw/tqfvWdmWhx15pYdOm1fUILOytnnvvvxlH2GID+Mv8Awj379s0nPYZ9B611llcafp3gKGz0y4Ka9rFqJ965Ekz7sMu/op4ZVHHoOa5c+zCWEoxhTjeU3Zdl5kZPg/rNa7drEJ1/xx4SGzWLQ3tqnAmuFLDHtPH/AOzLWxY/FLS5o1a8sL62z1eJRcR/99Ic/pXmr3+oaRestr9stpgMlVmkikY+hVv6g1Yg1m3vt8mo6PHM0Z/eTxobSdc+rx/Kfqy8+tfNxkpK9WN/Nafgz7Opg69P+FNS/ruesQ+P/Ckw41u3jPpMrxn9RUreN/CyDJ8Qaf8AhLn+VcFpXh6y8ReYui65Kk8ah2tdRtwzKvqHQgMM8ZqhrOiXnh+/gtL42bvOjSI1s5JwCASykAgc8H2rXDRwGIrewjUan2a1PMr4zFYeLlOmrLzO+uPiT4ahyILm4vGHa1tnb9SAK57UfiPqt0pj0vTorBP+e12wkkH0ReB+JNcsM9OfpXU+DPBDeLRLd3U1xbaZEdkckBAaaTvjII2r0J7ngdK9uWVYbDLnqNs8yGa4rFy5KSS8zlZjNc3TX19czXd3/wA952yVHoo6KPYVZ0zTdQ1ubytJsZr0g4LxjEan/ac/KPzz7V7Jp3ww8LWDrJJZPfSKchr2Uyj/AL5Py/pXXQwRQQrFDEkcajCoigAfQCreYKEeWjGyEssc5c9ed2eaaJ8I4TD5uvX00kzY/wBHtJCkaexbG5j78V2ekeDtA0JxJp+l28U3/PYrvk/76bJrcApa4J1Zz+JnpU6NOmrRQm3ilxRRWZqFFFFABRVea68p9gTccZxnGaibUMKCIs5BP3vSgC7RVNr/AGlR5RJP+10qb7QgRWdguexNAE1FQ/a4O8i/nS/aYsZ3rjOM570AS0VX+2wbCwcYFP8AtMQUsXXAOM5oAlzXg3xB+It7q97L4dsIjaad9re0uZ9+ZJ9pOVGPuoSMep9q9j13VxpmhX99AjTzW8DyRxRqWZ2A+UAD1OK8C8M+Adb8Y2sTBTaWRfzJNRnUgu+csYk4Lc55OB9aXO4yi7XNYUoTpz5pWdtPUzLi1juLN7YrtRl2jaPu+mPpWZBJN5wQsseoQrtYN92dR0Of69RXS+PPByaJfi0so9Ru4ra0aea6nkJEz4J2gLgKFCkn61l6b4dbUfD8ckMBuoYlLGSJwzhgAzYAOcjPQV6NWrHFy5qfuyj1PNw8J5dDlrL2kKm6/XyCC9jlfynzDN3jk4P1B6H8KkaONgtvdFxbbmZJEj8wwljlgU6shIBOOVPIzkinN4N8VSAQ2+mT38XJCzxDjBwRuzwfrgmtXTfhh45uo9yW1vpw7Lc3m/8AIANj86iriVXh7LEx17oqGChh6n1jAzt/dkjLm1e6gg+zf2rFe23aKUmZP++Jkyv4Gp/CmoWWneIrfUL258m3iDtshiaQtlceWAufl5ycnHArq7b4QeLHx9p1/TIv+ucDuR+eK3LX4NcD7d4lvJPa3gSIfruNefLA4aUXHmevlY7Xj8Y1blijIm8caPYpM3h3Q2+1Tfelktxbx/Vv4m+griHu5tR1SSaWaS/1O4I3CJC7nHRQq52qOwr2uz+FXha2wZ7We+Yd7u4dx/3yCB+ldVY6VYaZD5NhZ29rHjG2CIID+VGX4bC5c3OhG839qWrOKvRrYnStLTsjybw18MNQ1J0uvECtZ2fX7GrfvpR6Ow4Qew59xXr1paw2dvHb28KQwxqESNBhVUdABU2KWt6tWdV3kzajQhRjywVgoopC2OuAKzNRaKb5i+o/OmmVFOCwH1NAElFM81fUfnR5qbtu5c+maAH0U3zF9R+dKCCMigCCeFWJbcwOO3tVZrVAqDLY256+tFFADzaowzucEDgg471I1tHIibs5AoooAb9ghz/F+dKLGL+8/wBM0UUARJZxmeSMltq4I59alNjGRjfJjpjdRRQA2XS7a4iaOZWdG4Kk8GrCoqIFA4A4oooA8s1OP+3/AIzXXhm+ZzpTaakskUbbDIQwfDMOcZ7DHHFek6dpdjp1qsNjaw2sWS2yBAgyep470UUkNssiMbidxOakVQoxRRTEOooooAKKKKACiiigAqtd2MN7GEmDFQc4BxRRQBXfSLdwNzSHHTkdT1PShtHtnfzcyB+eQR369qKKAA6PbEjc0h+jY9fQe/6Uv9j2uDkOc474/HiiigBP7Itju+aTng8jkce3tV2GJYYUiUkqigDJ5oooA//Z";
			fwCzrkService.savefwCzrkAccInfo(czrk,pic);
		} catch (Exception e) {
			if (e instanceof ServiceLogicalException) {
				return new KJSONMSG(300,e.getMessage());
			}else{
				return new KJSONMSG(400,"服务器错误");
			}
		}
		return new KJSONMSG(200,"操作成功");
	}
	/**
	 * 流动人口采集
	 * @param jzwfjid 建筑物房间id
	 * @return
	 */
	@AuthWidgetRule(value="device.saveSyFwLdrkAccInfo",desc="流动人员信息采集",operateType="W",refTable="SY_FW_LDRK" ,crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwLdrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object enterSyFwLdrkAccInfo(String jzwfjid) {
		KJSONMSG ldrk = syFwLdrkService.loadLdrkAccInfoApp(jzwfjid);
		return ldrk;
	}
	/**
	 * 根据房间和身份证号加载流动人口信息
	 * @param sfzh 身份证号
	 * @param jzwfjid 建筑物房屋Id
	 * @return
	 */
	@AuthWidgetRule(value="device.loadLdrkInfo",desc="根据房间和身份证号加载流动人口信息",operateType="W",refTable="SY_FW_LDRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadLdrkInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object loadLdrkInfo(String sfzh,String jzwfjid) {
		SyFwLdrk ldrk=syFwLdrkService.validateLdrk(sfzh,jzwfjid);
		if(ldrk!=null){
			return new  KJSONMSG(300,"该采集信息已经存在");
		}
		SyRkglLdrkdjb glldrk=syRkglLdrkdjbService.selectLdrkInfoByZjbh(sfzh);
		if(glldrk!=null){
			Map<String,String> strldrk=new HashMap<String,String>();
			strldrk.put("sfzh", glldrk.getSfzh()==null ?"":glldrk.getSfzh());
			strldrk.put("xm", glldrk.getXm()==null ?"":glldrk.getXm());
			strldrk.put("bm", glldrk.getBm()==null ?"":glldrk.getBm());
			strldrk.put("xb", glldrk.getXb()==null ?"":glldrk.getXb());
			strldrk.put("mz", glldrk.getMz()==null ?"":glldrk.getMz());
			strldrk.put("zzmm", glldrk.getZzmm()==null ?"":glldrk.getZzmm());
			strldrk.put("csrq", glldrk.getCsrq()==null ?"":ZDateUtil.getdefaultFormatDate(glldrk.getCsrq()));
			strldrk.put("lxfsSj", glldrk.getLxfsSj()==null ?"":glldrk.getLxfsSj());
			strldrk.put("ddbdrq", glldrk.getDdbdrq()==null ?"":ZDateUtil.getdefaultFormatDate(glldrk.getDdbdrq()));
			strldrk.put("sfsljzz", glldrk.getSfsljzz()==null ?"":glldrk.getSfsljzz());
			strldrk.put("hyzk", glldrk.getHyzk()==null ?"":glldrk.getHyzk());
			strldrk.put("xl", glldrk.getXl()==null ?"":glldrk.getXl());
			strldrk.put("fbyqk", glldrk.getFbyqk()==null ?"":glldrk.getFbyqk());
			strldrk.put("jzsy", glldrk.getJzsy()==null ?"":glldrk.getJzsy());
			strldrk.put("njzqx", glldrk.getNjzqx()==null ?"":glldrk.getNjzqx());
			strldrk.put("fjbm", jzwfjid);
			byte[] data=syRkglLdrkdjbPicService.loadRkglPic(sfzh);
			String str1="";
			if(data!=null){
				str1=java.util.Base64.getEncoder().encodeToString(data);
			}
			strldrk.put("xp",str1);
//			glldrk.setFjbm(jzwfjid);
			return new KJSONMSG(200,"数据加载成功", strldrk);
		}

		//API ----return new KJSONMSG(202,"数据加载成功", rkczrk);
//		Map<String,String> strReturns=syRkglLdrkdjbService.queryRkInfo(sfzh);
//		if(strReturns!=null){
//			return new KJSONMSG(202, "数据加载成功", strReturns);
//		}
		Map<String,String> strReturn=syRkglLdrkdjbService.queryRkInfo(sfzh);
		if(strReturn!=null && !strReturn.isEmpty()){
			Map<String,String> strReturns=new HashMap<String,String>();
			strReturns.put("xb", strReturn.get("XB")==null ?"":strReturn.get("XB"));
			strReturns.put("bm", strReturn.get("CYM")==null ?"":strReturn.get("CYM"));
			strReturns.put("byzk", strReturn.get("BYZK")==null ?"":strReturn.get("BYZK"));
			strReturns.put("hyzk", strReturn.get("HYZK")==null ?"":strReturn.get("HYZK"));
			strReturns.put("xl", strReturn.get("WHCD")==null ?"":strReturn.get("WHCD"));
			strReturns.put("mz", strReturn.get("MZ")==null ?"":strReturn.get("MZ"));
			strReturns.put("xm", strReturn.get("XM")==null ?"":strReturn.get("XM"));
			strReturns.put("csrq", strReturn.get("CSRQ")==null ?"":strReturn.get("CSRQ"));
			strReturns.put("xp", strReturn.get("XP")==null ?"":strReturn.get("XP"));
			return new KJSONMSG(202,"数据加载成功", strReturns);
		}else{
			//----------------------
			//没查询到数据，进习 手填 
			return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
		}
	}
	/**
	 * 流动人口信息采集
	 * @param moveldrk 为Map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.saveSyFwLdrkAccInfo",desc="流动人口信息采集",operateType="W",refTable="SY_FW_LDRK",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveSyFwLdrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveSyFwLdrkAccInfo(@RequestBody Map moveldrk){
		SyRkglLdrkdjb ldrk=new SyRkglLdrkdjb();
		ldrk.setSfzh((String)moveldrk.get("sfzh"));
		ldrk.setXm((String)moveldrk.get("xm"));
		ldrk.setBm((String)moveldrk.get("bm"));
		ldrk.setXb((String)moveldrk.get("xb"));
		ldrk.setMz((String)moveldrk.get("mz"));
		ldrk.setZzmm((String)moveldrk.get("zzmm"));
		String csrq=(String) moveldrk.get("csrq");
//		ldrk.setCsrq(DataRangeUtil.StringToData(csrq));
		ldrk.setCsrq(ZDateUtil.stringToDate(csrq, ZDateStyle.YYYY_MM_DD));
		ldrk.setLxfsSj((String)moveldrk.get("lxfsSj"));
		String ddbdrq =  (String) moveldrk.get("ddbdrq");
	//	ldrk.setDdbdrq(DataRangeUtil.StringToData(ddbdrq));
		ldrk.setDdbdrq(ZDateUtil.stringToDate(ddbdrq, ZDateStyle.YYYY_MM_DD));
		ldrk.setSfsljzz((String)moveldrk.get("sfsljzz"));
		ldrk.setHyzk((String)moveldrk.get("hyzk"));
		ldrk.setXl((String)moveldrk.get("xl"));
		ldrk.setFbyqk((String)moveldrk.get("fbyqk"));
		ldrk.setJzsy((String)moveldrk.get("jzsy"));
		ldrk.setNjzqx((String)moveldrk.get("njzqx"));
		ldrk.setFh((String) moveldrk.get("fh"));
		ldrk.setXxdz((String) moveldrk.get("xxdz"));
		ldrk.setFjbm((String) moveldrk.get("fjbm"));
		ldrk.setJzwid((String)moveldrk.get("jzwid"));
		
		ldrk.setMacAddress((String)moveldrk.get("macAddress"));
		ldrk.setImeiNum((String)moveldrk.get("imeiNum"));
		ldrk.setTfCardNum((String)moveldrk.get("tfCardNum"));
		ldrk.setSimNum((String)moveldrk.get("simNum"));
		
		try{
			byte[] pic = null;
			if(!"".equals(moveldrk.get("xp"))&& moveldrk.get("xp")!=null){
				Decoder decoder=java.util.Base64.getDecoder();
				pic = decoder.decode((String) moveldrk.get("xp"));
			}
			    
			     syFwLdrkService.saveSyFwLdrkAccInfo(ldrk,pic);
	    	}catch(Exception e){
	    		if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
		
	
		return new KJSONMSG(200,"操作成功");
	}
	
	/**
	 * 境外人口信息采集
	 * @param jzwfjid 建筑物房间id
	 * @return
	 */
	@AuthWidgetRule(value="device.saveSyFwJwryAccInfo",desc="境外人员信息采集",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwJwryAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object enterSyFwJwryAccInfo(String jzwfjid) {
		KJSONMSG jwry = syFwJwryService.loadSyjwryAccInfoApp(jzwfjid);
		return jwry;
	}
	/**
	 * 根据身份证号和建筑物房间id加载境外人员信息
	 * @param sfzh 身份证
	 * @param jzwfjid 建筑物房间id
	 * @return
	 */
	@AuthWidgetRule(value="device.loadJwryInfo",desc="省份证号和建筑物ID加载境外人员信息",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJwryInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJwryInfo(String sfzh,String jzwfjid) {
		SyFwJwry jwry=syFwJwryService.validateJwry(sfzh,jzwfjid);
		if(jwry!=null){
			return new  KJSONMSG(300,"该采集信息已经存在");
		}
		Syjwry gljwry=syjwryService.selectJwryInfoByZjbh(sfzh);
		if(gljwry!=null){
//			glldrk.setJzwfjid(jzwfjid);
			Map<String,String> strjwry=new HashMap<String,String>();
			System.out.println(gljwry.getQzzl());
			strjwry.put("zjhm", gljwry.getZjhm()==null ? "":gljwry.getZjhm());
			strjwry.put("ywm", gljwry.getYwm()==null ? "":gljwry.getYwm());
			strjwry.put("ywx", gljwry.getYwx()==null ? "":gljwry.getYwx());
			strjwry.put("zwm", gljwry.getZwm()==null ? "":gljwry.getZwm());
			strjwry.put("csrq", gljwry.getCsrq()==null ? "":ZDateUtil.getdefaultFormatDate(gljwry.getCsrq()));
			strjwry.put("zjzl", gljwry.getZjzl()==null ? "":gljwry.getZjzl());
			strjwry.put("qzzl", gljwry.getQzzl()==null ? "":gljwry.getQzzl());
			strjwry.put("qzbh", gljwry.getQzbh()==null ? "":gljwry.getQzbh());
			strjwry.put("gj", gljwry.getGj()==null ? "":gljwry.getGj());
			strjwry.put("jwrylxdh", gljwry.getJwrylxdh()==null ? "":gljwry.getJwrylxdh());
			strjwry.put("tlyxq", gljwry.getTlyxq()==null ? "":ZDateUtil.getdefaultFormatDate(gljwry.getTlyxq()));
			strjwry.put("fwfzxm", gljwry.getFwfzxm()==null ? "":gljwry.getFwfzxm());
			strjwry.put("fzsfzh", gljwry.getFzsfzh()==null ? "":gljwry.getFzsfzh());
			strjwry.put("fjbm", jzwfjid);
			
			byte[] data=syjwryPicService.loadRkglPic(sfzh);
			String str1="";
			if(data!=null){
				 str1=java.util.Base64.getEncoder().encodeToString(data);
			}
			strjwry.put("xp", str1);
			return new KJSONMSG(200,"数据加载成功", strjwry);
		}

		//API ----return new KJSONMSG(202,"数据加载成功", rkczrk);
//		Map<String,String> strReturns=RkQueryUtil.queryCzrk(sfzh);
		Map<String,String> strReturn=syjwryService.queryRkInfo(sfzh);
		if(strReturn!=null){
			Map<String,String> strReturns=new HashMap<String,String>();
			strReturns.put("csrq", strReturn.get("CSRQ")==null ?"":strReturn.get("CSRQ"));
			strReturns.put("xp", strReturn.get("XP")==null ?"":strReturn.get("XP"));
			return new KJSONMSG(202, "数据加载成功", strReturns);
		}
		//----------------------
		//没查询到数据，进习 手填 
		return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
	}
	/**
	 * 境外人员信息采集
	 * @param movejwry 为Map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.saveSyFwJwryAccInfo",desc="境外人员信息采集",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveSyFwJwryAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveSyFwJwryAccInfo(@RequestBody Map movejwry){
		Syjwry jwry=new Syjwry();
		jwry.setZjhm((String)movejwry.get("zjhm"));
		jwry.setYwm((String)movejwry.get("ywm"));
		jwry.setYwx((String)movejwry.get("ywx"));
		jwry.setZwm((String)movejwry.get("zwm"));
		String csrq = (String) movejwry.get("csrq");
		
//		jwry.setCsrq(DataRangeUtil.StringToData(csrq));
		jwry.setCsrq(ZDateUtil.stringToDate(csrq, ZDateStyle.YYYY_MM_DD));
		jwry.setZjzl((String)movejwry.get("zjzl"));
		jwry.setFjh((String)movejwry.get("fjh"));
		jwry.setQzzl((String)movejwry.get("czzl"));
		jwry.setQzbh((String)movejwry.get("qzbh"));
		jwry.setGj((String)movejwry.get("gj"));
		jwry.setJwrylxdh((String)movejwry.get("jwrylxdh"));
		String tlyxq =(String) movejwry.get("tlyxq"); 
		//jwry.setTlyxq(DataRangeUtil.StringToData(csrq));
		jwry.setTlyxq(ZDateUtil.stringToDate(tlyxq, ZDateStyle.YYYY_MM_DD));
		jwry.setFwfzxm((String)movejwry.get("fwfzxm"));
		jwry.setFzsfzh((String)movejwry.get("fzsfzh"));
		jwry.setFjbm((String)movejwry.get("fjbm"));
		jwry.setXxdz((String)movejwry.get("xxdz"));
		jwry.setJzwid((String)movejwry.get("jzwid"));
		
		//存储四卡号
		jwry.setMacAddress((String)movejwry.get("macAddress"));//mac地址
		jwry.setTfCardNum((String)movejwry.get("tfCardNum"));//tf卡号
		jwry.setImeiNum((String)movejwry.get("imeiNum"));//imei卡号
		jwry.setSimNum((String)movejwry.get("simNum"));//sim卡号
		
		try{
//			 Decoder decoder=java.util.Base64.getDecoder();
//			 byte[] pic = decoder.decode((String) movejwry.get("xp"));
			 byte[] bytes=null;
			 byte[] pic = null;
				if(!"".equals(movejwry.get("xp"))&& movejwry.get("xp")!=null){
					Decoder decoder=java.util.Base64.getMimeDecoder();
					 String xp= movejwry.get("xp").toString();
					 bytes = xp.getBytes("UTF-8");
					 String encoded = java.util.Base64.getEncoder().encodeToString(bytes);
					 byte[] decoded = java.util.Base64.getDecoder().decode(encoded);
					 pic = decoder.decode(decoded);
				}
			 
			syFwJwryService.saveSyFwJwryAccInfo(jwry,pic);
	    	}catch(Exception e){
	    		if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
		return new KJSONMSG(200,"操作成功");
	}
	
	/*---------------------------房屋信息采集-------------------------------------------------*/
	/**
	 * 房屋信息采集
	 * @param jzwfjid 建筑物房间id
	 * @param model
	 * @return
	 */
	@AuthWidgetRule(value="device.saveFwjbxxAccInfo",desc="房屋信息采集",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/loadFwjbxxAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object loadFwjbxxAccInfo(String jzwfjid, Model model) {
		SyFwjbxx fwjbxxs = fwjbxxService.loadFwjbxxAccInfo(jzwfjid);
		if(!("".equals(fwjbxxs.getFwcqzh())||fwjbxxs.getFwcqzh()==null)){
			Map<String,String> map=new HashMap<String,String>();
			map.put("ssxqdm", fwjbxxs.getSsxqdm()==null ?"":fwjbxxs.getSsxqdm());
			map.put("ssjlxxq_mc", fwjbxxs.getSsjlxxq_mc()==null ?"":fwjbxxs.getSsjlxxq_mc());
			map.put("fjbm", fwjbxxs.getFjbm()==null ?"":fwjbxxs.getFjbm());
			map.put("fjh", fwjbxxs.getFjh()==null ?"":fwjbxxs.getFjh());
			map.put("fwdz", fwjbxxs.getFwdz()==null ?"":fwjbxxs.getFwdz());
			map.put("sdpcs", fwjbxxs.getSdpcs()==null ?"":fwjbxxs.getSdpcs());
			map.put("sdpcs_mc", fwjbxxs.getSdpcs_mc()==null ?"":fwjbxxs.getSdpcs_mc());
			map.put("jwzrq", fwjbxxs.getJwzrq()==null ?"":fwjbxxs.getJwzrq());
			map.put("jwzrq_mc", fwjbxxs.getJwzrq_mc()==null ?"":fwjbxxs.getJwzrq_mc());
			map.put("fwlb", fwjbxxs.getFwlb()==null ?"":fwjbxxs.getFwlb());
			map.put("fwxz", fwjbxxs.getFwxz()==null ?"":fwjbxxs.getFwxz());
			map.put("fwyt", fwjbxxs.getFwyt()==null ?"":fwjbxxs.getFwyt());
			map.put("fwlx", fwjbxxs.getFwlx()==null ?"":fwjbxxs.getFwlx());
			map.put("fwcqzh", fwjbxxs.getFwcqzh()==null ?"":fwjbxxs.getFwcqzh());
			map.put("sfczfw", fwjbxxs.getSfczfw()==null ?"":fwjbxxs.getSfczfw());
			map.put("hx", fwjbxxs.getHx()==null ?"":fwjbxxs.getHx());
		 	map.put("fwjs", fwjbxxs.getFwjs()==null ?"": String.valueOf(fwjbxxs.getFwjs()));
			map.put("fwmj", fwjbxxs.getFwmj()==null ?"": fwjbxxs.getFwmj().toString());
			map.put("fzxm", fwjbxxs.getFzxm()==null ?"":fwjbxxs.getFzxm());
			map.put("fzsfzhm", fwjbxxs.getFzsfzhm()==null ?"":fwjbxxs.getFzsfzhm());
			map.put("fzlxdh", fwjbxxs.getFzlxdh()==null ?"":fwjbxxs.getFzlxdh());
			map.put("tgrxm", fwjbxxs.getTgrxm()==null ?"":fwjbxxs.getTgrxm());
			map.put("tgrsfzhm", fwjbxxs.getTgrsfzhm()==null ?"":fwjbxxs.getTgrsfzhm());
			map.put("tgrlxdh", fwjbxxs.getTgrlxdh()==null ?"":fwjbxxs.getTgrlxdh());
			map.put("yfzgx", fwjbxxs.getYfzgx()==null ?"":fwjbxxs.getYfzgx());
			map.put("fwssdwbm", fwjbxxs.getFwssdwbm()==null ?"":fwjbxxs.getFwssdwbm());
			map.put("fwssdwmc", fwjbxxs.getFwssdwmc()==null ?"":fwjbxxs.getFwssdwmc());
			map.put("jzwfjid", fwjbxxs.getFwid()==null ?"":fwjbxxs.getFwid());
			map.put("ssjlxxqdm", fwjbxxs.getSsjlxxq_dzbm()==null?"":fwjbxxs.getSsjlxxq_dzbm());
			map.put("lrfs", "1");
			KJSONMSG fwjbxx = new KJSONMSG(202,"该房间已经采集完成", map);
			return fwjbxx;
		}else{
			Map<String,String> map=new HashMap<String,String>();
			map.put("ssxqdm", fwjbxxs.getSsxqdm()==null ?"":fwjbxxs.getSsxqdm());
			map.put("ssjlxxq_mc", fwjbxxs.getSsjlxxq_mc()==null ?"":fwjbxxs.getSsjlxxq_mc());
			map.put("fjbm", fwjbxxs.getFjbm()==null ?"":fwjbxxs.getFjbm());
			map.put("fjh", fwjbxxs.getFjh()==null ?"":fwjbxxs.getFjh());
			map.put("fwdz", fwjbxxs.getFwdz()==null ?"":fwjbxxs.getFwdz());
			map.put("sdpcs", fwjbxxs.getSdpcs()==null ?"":fwjbxxs.getSdpcs());
			map.put("sdpcs_mc", fwjbxxs.getSdpcs_mc()==null ?"":fwjbxxs.getSdpcs_mc());
			map.put("jwzrq", fwjbxxs.getJwzrq()==null ?"":fwjbxxs.getJwzrq());
			map.put("lrfs", "1");
			map.put("jwzrq_mc", fwjbxxs.getJwzrq_mc()==null ?"":fwjbxxs.getJwzrq_mc());
//			map.put("fwlb", fwjbxxs.getFwlb()==null ?"":fwjbxxs.getFwlb());
//			map.put("fwxz", fwjbxxs.getFwxz()==null ?"":fwjbxxs.getFwxz());
//			map.put("fwyt", fwjbxxs.getFwyt()==null ?"":fwjbxxs.getFwyt());
//			map.put("fwlx", fwjbxxs.getFwlx()==null ?"":fwjbxxs.getFwlx());
//			map.put("fwcqzh", fwjbxxs.getFwcqzh()==null ?"":fwjbxxs.getFwcqzh());
//			map.put("sfczfw", fwjbxxs.getSfczfw()==null ?"":fwjbxxs.getSfczfw());
//			map.put("hx", fwjbxxs.getHx()==null ?"":fwjbxxs.getHx());
//			map.put("fwjs", fwjbxxs.getFwjs()==null ?"": String.valueOf(fwjbxxs.getFwjs()));
//			map.put("fwmj", fwjbxxs.getFwmj()==null ?"": fwjbxxs.getFwmj().toString());
//			map.put("fzxm", fwjbxxs.getFzxm()==null ?"":fwjbxxs.getFzxm());
//			map.put("fzsfzhm", fwjbxxs.getFzsfzhm()==null ?"":fwjbxxs.getFzsfzhm());
//			map.put("fzlxdh", fwjbxxs.getFzlxdh()==null ?"":fwjbxxs.getFzlxdh());
//			map.put("tgrxm", fwjbxxs.getTgrxm()==null ?"":fwjbxxs.getTgrxm());
//			map.put("tgrsfzhm", fwjbxxs.getTgrsfzhm()==null ?"":fwjbxxs.getTgrsfzhm());
//			map.put("tgrlxdh", fwjbxxs.getTgrlxdh()==null ?"":fwjbxxs.getTgrlxdh());
//			map.put("yfzgx", fwjbxxs.getYfzgx()==null ?"":fwjbxxs.getYfzgx());
//			map.put("fwssdwbm", fwjbxxs.getFwssdwbm()==null ?"":fwjbxxs.getFwssdwbm());
//			map.put("fwssdwmc", fwjbxxs.getFwssdwmc()==null ?"":fwjbxxs.getFwssdwmc());
//			map.put("jzwfjid", fwjbxxs.getFwid()==null ?"":fwjbxxs.getFwid());
			map.put("ssjlxxqdm", fwjbxxs.getSsjlxxq_dzbm()==null?"":fwjbxxs.getSsjlxxq_dzbm());
			KJSONMSG fwjbxx = new KJSONMSG(200,"数据加载成功", map);
			return fwjbxx;
			
		}
		
	
	}
	/**
	 * 房屋信息采集
	 * @param ssxqdm Map<String,Object>
	 * @return
	 */
	@AuthWidgetRule(value="device.saveFwjbxxAccInfo",desc="房屋信息采集",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveFwjbxxAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveFwjbxxAccInfo( @RequestBody Map ssxqdm){
		 SyFwjbxx syFwjbxx=new SyFwjbxx();
		 syFwjbxx.setSsxqdm((String)ssxqdm.get("ssxqdm"));
		 syFwjbxx.setFjbm((String)ssxqdm.get("fjbm"));
		 syFwjbxx.setFjh((String)ssxqdm.get("fjh"));
		 syFwjbxx.setFwdz((String)ssxqdm.get("fwdz"));
		 syFwjbxx.setSdpcs((String)ssxqdm.get("sdpcs"));
		 syFwjbxx.setJwzrq((String)ssxqdm.get("jwzrq"));
		 syFwjbxx.setFwlb((String)ssxqdm.get("fwlb"));
		 syFwjbxx.setFwxz((String)ssxqdm.get("fwxz"));
		 syFwjbxx.setFwyt((String)ssxqdm.get("fwyt"));
		 syFwjbxx.setFwlx((String)ssxqdm.get("fwlx"));
		 syFwjbxx.setFwcqzh((String)ssxqdm.get("fwcqzh"));
		 syFwjbxx.setSfczfw((String)ssxqdm.get("sfczfw"));
		 syFwjbxx.setHx((String)ssxqdm.get("hx"));
		 syFwjbxx.setFwjs(Integer.parseInt((String) ssxqdm.get("fwjs")));
		 String fwmj = (String) ssxqdm.get("fwmj");
		 if(fwmj != null && !"".equals(fwmj)){
			 BigDecimal bd=new BigDecimal((String)ssxqdm.get("fwmj"));
			 syFwjbxx.setFwmj(bd);
		 }
		 syFwjbxx.setFzxm((String)ssxqdm.get("fzxm"));
		 syFwjbxx.setFzsfzhm((String)ssxqdm.get("fzsfzhm"));
		 syFwjbxx.setFzlxdh((String)ssxqdm.get("fzlxdh"));
		 syFwjbxx.setTgrxm((String)ssxqdm.get("tgrxm"));
		 syFwjbxx.setTgrsfzhm((String)ssxqdm.get("tgrsfzhm"));
		 syFwjbxx.setTgrlxdh((String)ssxqdm.get("tgrlxdh"));
		 syFwjbxx.setYfzgx((String)ssxqdm.get("yfzgx"));
		 syFwjbxx.setFwssdwbm((String)ssxqdm.get("fwssdwbm"));
		 syFwjbxx.setFwssdwmc((String)ssxqdm.get("fwssdwmc"));
		 syFwjbxx.setFwid((String)ssxqdm.get("jzwfjid"));	
		 syFwjbxx.setSsjlxxq_dzbm((String)ssxqdm.get("ssjlxxq_dm"));
		 syFwjbxx.setLrfs("1");
		 
		 syFwjbxx.setMacAddress((String)ssxqdm.get("macAddress"));
		 syFwjbxx.setTfCardNum((String)ssxqdm.get("tfCardNum"));
		 syFwjbxx.setImeiNum((String)ssxqdm.get("imeiNum"));
		 syFwjbxx.setSimNum((String)ssxqdm.get("simNum"));
		 
		 fwjbxxService.saveFwjbxxAccInfo(syFwjbxx);
		 return new KJSONMSG(200,"操作成功");
	}
 /*------------------------------------根据房屋Id加载房屋照片集合------------------------------------*/
	@AuthWidgetRule(value="device.findSYFwjbxxByFjbm",desc="房屋信息查询",operateType="W",refTable="SY_FWJBXX",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/findSYFwjbxxByFjbm", method = RequestMethod.POST)
	@ResponseBody 
	public  Object findSYFwjbxxByFjbm(@RequestBody Map jzwFw) {
		//SyFwjbxx fw= fwjbxxService.loadSYFwjbxxByFjbm(fjbm);
		String fjbm=(String) jzwFw.get("jzwfjid");
		if(!StringUtils.hasText(fjbm)){
			return new KJSONMSG(300,"所属房间ID为空，请检查数据");
		}
		List<String> fjpicList=jzwfjPicService.loadFjPicIdsByfjId(fjbm);
		Map<String,Object> data=Maps.newHashMap();
		//data.put("fwxx", fw);
		data.put("fjpicIds", fjpicList);
		KJSONMSG msg=new KJSONMSG(200, "数据加载成功", data);
		return msg;
	}
	//-----------------建筑物房间信息加载----------------------------------------------------------
	@AuthWidgetRule(value="jzwfj.loadJzwFjPic",desc="房间照片加载",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwFjPic", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwFjPic(@RequestBody Map jzwFj){
		String jzwfjPicId=(String) jzwFj.get("picid");
		String type=(String) jzwFj.get("type");
		if("loadPic".equals(type)){
			byte[] data=jzwfjPicService.loadJzwfjPic(jzwfjPicId);
			 String str1="";
			 if(data!=null){
				 str1=java.util.Base64.getEncoder().encodeToString(data); 
			 }
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("xp",str1==null? "":str1);
			 return new KJSONMSG(200, "照片加载成功！", map);
		}else if("deletePic".equals(type)){
			 jzwfjPicService.deletefwpic(jzwfjPicId);
			return new KJSONMSG(202, "照片删除成功！");
		}
		return new KJSONMSG(300, "参数错误！");
	}
	//----------------------------建筑物房间上传---------------------------------------------------
	@AuthWidgetRule(value="jzwfj.uploadJzwFjPic",desc="房间照片上传",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/uploadJzwFjPic", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadJzwFjPic(@RequestBody Map JzwFj) {
		String jzwfjid=(String) JzwFj.get("jzwfjid");
		if(!StringUtils.hasText(jzwfjid)){
			return new KJSONMSG(300,"所属房间ID为空，请检查数据");
		}
		JzwfjPic jzwfjPic=new JzwfjPic();
		String xp= JzwFj.get("xp").toString();
		
		//判断照片是否为空
		if(xp==null||xp.isEmpty()){
			return "";
		}
		
		 byte[] bytes=null;
		 Decoder decoder=java.util.Base64.getMimeDecoder();
		 try {
			bytes = xp.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String encoded = java.util.Base64.getEncoder().encodeToString(bytes);
		 byte[] decoded = java.util.Base64.getDecoder().decode(encoded);
		 byte[] pic = decoder.decode(decoded);
		jzwfjPic.setCreatTime(new Date());
		jzwfjPic.setPicid(UUIDUtil.uuid());
		jzwfjPic.setUpdatedTime(new Date());
		jzwfjPic.setPic(pic);
		String picid=jzwfjPicService.addJzwFjPic(jzwfjPic, jzwfjid);
		Map<String,Object> data=Maps.newHashMap();
		//data.put("jgxx", jgxx);
		data.put("picid", picid);
		
		return new KJSONMSG(200,"操作成功",data);
	}
	
	public int AddJzwPic( Map Jzwjg) {
		int i;//0失败 1成功
		String jzwjgid=(String) Jzwjg.get("jzwid");
		if(!StringUtils.hasText(jzwjgid)){
			return 0;
		}
		JzwjgPic jzwjgPic=new JzwjgPic();
		jzwjgPic.setCreatTime(new Date());
		jzwjgPic.setPicid(UUIDUtil.uuid());
		jzwjgPic.setUpdatedTime(new Date());
		String xp= Jzwjg.get("xp").toString();
		if(xp==null||xp.isEmpty()||"null".equals(xp)){
			return 0;
		}
		 byte[] bytes=null;
		 Decoder decoder=java.util.Base64.getMimeDecoder();
		 try {
			bytes = xp.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String encoded = java.util.Base64.getEncoder().encodeToString(bytes);
		 byte[] decoded = java.util.Base64.getDecoder().decode(encoded);
		 byte[] pic = decoder.decode(decoded);
		jzwjgPic.setPic(pic);
		String picid=jzwjgPicService.addJzwJgPic(jzwjgPic, jzwjgid);
		Map<String,Object> data=Maps.newHashMap();
		//data.put("jgxx", jgxx);
		data.put("picid", picid);
		
		return 1;
	}
	/*------------------------------------建筑物建构采集 ------------------------------------------*/
	@AuthWidgetRule(value="device.showjzwjg",desc="建筑物信息",operateType="W",refTable="SY_FWJBXX")
	@RequestMapping(value = "/findJzwjgjbxxByJzwjgId", method = RequestMethod.POST)
	@ResponseBody 
	public Object findJzwjgjbxxByJzwjgId(@RequestBody Map jzw){
		//Jzwjg jgxx=jzwjgService.findValidateAndBuildJzwjgById(jzwjgid);
		String jzwjgid=(String) jzw.get("jzwid");
		if(!StringUtils.hasText(jzwjgid)){
			return new KJSONMSG(300,"请选择建筑物");
		}
		List <String> jgpicList=jzwjgPicService.loadjgPicIdsByjgId(jzwjgid);
		Map<String,Object> data=Maps.newHashMap();
		//data.put("jgxx", jgxx);
		data.put("jgpicIds", jgpicList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", data);
		return msg;
	}
	
	@AuthWidgetRule(value="device.loadJzwJgPic",desc="建筑物照片加载",operateType="W",refTable="DZ_JZWJG_PIC",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwJgPic", method = RequestMethod.POST)
	@ResponseBody
	public Object loadJzwJgPic(@RequestBody Map jzw){
		String type=(String) jzw.get("type");
		String jzwjgPicId=(String) jzw.get("picid");
		if("loadPic".equals(type)){
			 byte[] data=jzwjgPicService.loadJzwjgPic(jzwjgPicId);
			 String str1="";
			 if(data!=null){
				 str1=java.util.Base64.getEncoder().encodeToString(data); 
			 }
			 Map<String,String> map=new HashMap<String,String>();
			 map.put("xp",str1==null? "":str1);
			 return new KJSONMSG(200, "照片加载成功！",map);
		}else if("deletePic".equals(type)){
			jzwjgPicService.deleteJzwjgPic(jzwjgPicId);
			return new KJSONMSG(202, "照片删除成功！");
		}
		return new KJSONMSG(300, "参数错误！");
	}
	//建筑物结构图片上传
	@AuthWidgetRule(value="device.uploadJzwjgPic",desc="房间照片上传",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/uploadJzwjgPic", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadJzwjgPic(@RequestBody Map Jzwjg){
 		String jzwjgid=(String) Jzwjg.get("jzwid");
		if(!StringUtils.hasText(jzwjgid)){
			return new KJSONMSG(300,"所属建筑物结构ID为空，请检查数据");
		}
		JzwjgPic jzwjgPic=new JzwjgPic();
		jzwjgPic.setCreatTime(new Date());
		jzwjgPic.setPicid(UUIDUtil.uuid());
		jzwjgPic.setUpdatedTime(new Date());
		String xp= Jzwjg.get("xp").toString();
		
		 byte[] bytes=null;
		 Decoder decoder=java.util.Base64.getMimeDecoder();
		 try {
			bytes = xp.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String encoded = java.util.Base64.getEncoder().encodeToString(bytes);
		 byte[] decoded = java.util.Base64.getDecoder().decode(encoded);
		 byte[] pic = decoder.decode(decoded);
		jzwjgPic.setPic(pic);
		String picid=jzwjgPicService.addJzwJgPic(jzwjgPic, jzwjgid);
		Map<String,Object> data=Maps.newHashMap();
		//data.put("jgxx", jgxx);
		data.put("picid", picid);
		return new KJSONMSG(200,"操作成功",data);
	}
	
	/*----------------------------------------------------------------信息检索-------------------------------------------- */
	@Autowired
	private ElasticService elasticService;
	
	private final List<String> searchFromList=Arrays.asList(new String[]{"mlph","jzwjbxx"});
	String searchFiled="DZMC";
	String searchFindex="psam";
	@AuthWidgetRule(value="device.search",desc="信息检索",operateType="W",refTable="",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/search" }, method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public  Object search(@RequestParam Map<String,Object> param) {
		String type=(String) param.get("type");
		if("".equals(type)||type==null){
			type="react";
		}
		String val=(String) param.get("val");
		String searchFrom=(String) param.get("searchFrom");
		String matchPhrase=(String) param.get("matchPhrase");
		
		if(!searchFromList.contains(""+searchFrom)){
			return  DataSet.newDs();
		}
		int page=Integer.valueOf((String)param.get("page")==null? "1":(String)param.get("page"));
		int size=Integer.valueOf((String)param.get("rows")==null?"100":(String)param.get("rows"));
		int from =(page-1)*size;
		BaseFilterBuilder filterBuilder=null;
		//type="polygon";
		if("circle".equals(type)){
			Double radius=Double.valueOf((String)param.get("radius"));
			Double lat=Double.valueOf((String)param.get("lat"));
			Double lon=Double.valueOf((String)param.get("lon"));
			filterBuilder=FilterBuilders.geoDistanceFilter("LOCATION").point(lat,lon).distance(""+radius);
			
		}else if("react".equals(type)){
			
			Double ltlat=Double.valueOf((String)param.get("ltlat"));
			Double ltlon=Double.valueOf((String)param.get("ltlon"));
			
			Double rblat=Double.valueOf((String)param.get("rblat"));
			Double rblon=Double.valueOf((String)param.get("rblon"));
			filterBuilder=FilterBuilders.geoBoundingBoxFilter("LOCATION").topLeft(ltlat, ltlon).bottomRight(rblat, rblon);
			
		}else if("polygon".equals(type)){
		//-----------------one geoPolygon------------------------------------------------------------
//			List<GeoPoint> points=genPoints();
//			List<GeoPoint> points=Lists.newArrayList();
//			filterBuilder=FilterBuilders.geoPolygonFilter("LOCATION");
//			for(GeoPoint point:points){
//				((GeoPolygonFilterBuilder) filterBuilder).addPoint(point.getLat(), point.getLon());
//			}
		//-----------------mulit geoPolygon--------------------------------------------------------
			
		 //List<Polygon> polygons=genPolygon();
			String pp="116.950038,36.604882,116.950009,36.604487,116.949887,36.604487,116.950499,36.603818,116.951766,36.603669,116.951698,36.603288,116.950997,36.601778,116.950723,36.600779,116.951037,36.600165,116.953393,36.600308,116.955177,36.599701,116.956203,36.59828,116.956389,36.59752,116.956917,36.596435,116.957374,36.596006,116.958588,36.595278,116.958795,36.594989,116.958559,36.594138,116.958666,36.593924,116.960544,36.593189,116.960144,36.592639,116.959809,36.591768,116.959787,36.59149,116.960144,36.590653,116.960158,36.590139,116.959222,36.589583,116.959129,36.589226,116.957973,36.589126,116.957337,36.588755,116.956945,36.588327,116.956953,36.587527,116.955467,36.586314,116.954939,36.586085,116.954496,36.585536,116.954139,36.585314,116.953347,36.584965,116.952448,36.58475,116.947816,36.583983,116.946705,36.584048,116.945924,36.584355,116.94594,36.584567,116.945148,36.584834,116.945342,36.585715,116.943993,36.585655,116.943326,36.585861,116.943769,36.590639,116.943423,36.590611,116.943239,36.591847,116.941723,36.591933,116.941368,36.592095,116.940898,36.592588,116.938846,36.592874,116.93818,36.592767,116.937644,36.592446,116.93715,36.592321,116.93655,36.592321,116.935788,36.592588,116.935351,36.593031,116.937426,36.595698,116.939889,36.597661,116.940412,36.599137,116.940662,36.600588,116.945302,36.602444,116.946733,36.603434,116.947295,36.604167,116.948972,36.603945,116.948994,36.604823,116.949643,36.604757,116.949672,36.604906,116.950038,36.604882;";
			pp+="116.95525,36.60259,116.96905,36.6065,116.95782,36.61626,116.94695,36.61028,116.96355,36.59246,116.96941,36.60662,116.96844,36.60686,116.95525,36.60259;";
			List<Polygon> polygons=ElasticUtil.splitStrToPolygon(pp);
			
			//List<Polygon> polygons=Lists.newArrayList();
		 filterBuilder = FilterBuilders.boolFilter();
			for(Polygon p : polygons){
				BaseFilterBuilder filterBuilderx=FilterBuilders.geoPolygonFilter("LOCATION");
					for(GeoPoint point:p.getPoints()){
						((GeoPolygonFilterBuilder) filterBuilderx).addPoint(point.getLat(), point.getLon());
					}
				((BoolFilterBuilder)filterBuilder).should(filterBuilderx);
			}
		}else{
			return  FastJsonDataSet.newDs(); 
		}
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(matchAllQuery());
		if(val!=null&&!val.isEmpty()){
			if(matchPhrase!=null&&"1".equals(matchPhrase)){
				boolQueryBuilder.must(matchPhraseQuery(searchFiled, val));
			}else{
				boolQueryBuilder.must(matchQuery(searchFiled, val));
			}
		}
		SearchResponse response = elasticService.getClient()
				.prepareSearch(searchFindex)
				.setTypes(searchFrom)
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
				.setQuery(boolQueryBuilder)
				.setPostFilter(filterBuilder)
				.setFrom(from).setSize(size).execute()
				.actionGet();
		DataSet ds=ElasticUtil.searchResponse2Dataset(response);
		return new KJSONMSG(200,"数据加载成功", ds);
	} 
	/*---------------------加载移动端版本信息--------------------------------------*/
	@AuthWidgetRule(value="device.loadSyCzrkGrid",desc="常驻人口数据列表",operateType="W",refTable="APP_VERSION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadSyCzrkGrid", method = RequestMethod.POST)
	public @ResponseBody Object loadAPPGridView() {
		DataSet dataSet = appVersionService.loadAPPGridView();
		return new KJSONMSG(200,"数据加载成功", dataSet);
	}
	/*--------------------移动端工作量统计-----------------------------------------*/
	@AuthWidgetRule(value="device.loadTodayworkCount",desc="常驻人口数据列表",operateType="W",refTable="APP_VERSION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadTodayworkCount", method = RequestMethod.POST)
	
	public @ResponseBody Object loadTodayworkCount(@RequestBody Map person) {
		KAssert.hasText(person.get("personId"), "人员信息不能为空");
		Map<String ,Object> map=Maps.newHashMap();
		map.put("personId", person.get("personId"));
		map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD)));
		long czrkCount=fwCzrkService.loadTodayworkCount(map);
		List<Map<String, Object>> czrkResult=fwCzrkService.loadTodayWorkDetail(map);
		long ldrkCount=syRkglLdrkdjbService.loadldrkWorkCount(map);
		List<Map<String, Object>> ldrkesult=syRkglLdrkdjbService.loadTodayWorkDetail(map);
		long jwrycount=syjwryService.loadTodayWorkCount(map);
		List<Map<String, Object>> jwryresult=syjwryService.loadTodayWorkDetail(map);
		long jzwCount=jzwjbxxService.loadWorkJzwCount(map);
		List<Map<String, Object>> jzwJbxxresult=jzwjbxxService.loadTodayWorkDetail(map);
		Map<String ,Object> mapCount=Maps.newHashMap();
		mapCount.put("czrkCount", czrkCount);
		mapCount.put("ldrkCount", ldrkCount);
		mapCount.put("jwrycount", jwrycount);
		mapCount.put("czrkResult", czrkResult);
		mapCount.put("ldrkesult", ldrkesult);
		mapCount.put("jwryresult", jwryresult);
		mapCount.put("jzwCount", jzwCount);
		mapCount.put("jzwJbxxresult", jzwJbxxresult);
		return new KJSONMSG(200,"数据加载成功", mapCount);
	}
	
	@AuthWidgetRule(value="device.loadWordCount",desc="常驻人口数据列表",operateType="W",refTable="APP_VERSION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadWordCount", method = RequestMethod.POST)
	public @ResponseBody Object loadWordCount(@RequestBody Map workLoad) {
	//	DataSet dataSet = appVersionService.loadAPPGridView();
		String personId=(String) workLoad.get("personId");
		KAssert.hasText(personId, "人员信息不能为空");
		Map<String,Object> workInfo=new HashMap<String,Object>();
		workInfo.put("djr", personId);
		workInfo.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD)));
		long czrkcount=fwCzrkService.loadDayCZRKWorkCount(workInfo);
		List<Map<String,Object>> czrkFwList=fwCzrkService.loadFwCzrkList(workInfo);
		List<Map<String,Object>>  carkCount= new ArrayList<Map<String, Object>>();
		if(czrkFwList==null||czrkFwList.size()<1){
			carkCount=null;
		}else {
			for (int j = 0; j < czrkFwList.size(); j++) {
				Map<String, Object> map = czrkFwList.get(j);
				String ryid=(String) map.get("RYID");
				SyRkglCzrk czrk=syRkglCzrkService.loadSyCzrkDeatil(ryid);
				byte[] data=syRkglCzrkPicService.loadRkglPic(czrk.getGmsfhm());
				String str="";
				if(data!=null){
					str=java.util.Base64.getEncoder().encodeToString(data);
				}
				Map<String,Object> czrkMap=new HashMap<String,Object>();
				czrkMap.put("xm", czrk.getXm());
				czrkMap.put("csrq", czrk.getCsrq());
				czrkMap.put("sfzh", czrk.getGmsfhm());
				czrkMap.put("mz", czrk.getMz());
				czrkMap.put("zzmm", czrk.getZzmm());
				czrkMap.put("dz", czrk.getMlxz());
				czrkMap.put("xp", str);
				carkCount.add(czrkMap);
			}
		}
		long ldrkcount=syFwLdrkService.loadDayLdrkWorkCount(workInfo);
		List<Map<String,Object>> ldrkFwList=syFwLdrkService.loadFwLdrkList(workInfo);
		List<Map<String,Object>>  ldrkCount= new ArrayList<Map<String, Object>>();
		if(ldrkFwList==null||ldrkFwList.size()<1){
			ldrkCount=null;
		}else {
			for (int j = 0; j < ldrkFwList.size(); j++) {
				Map<String, Object> map = ldrkFwList.get(j);
				String ryid=(String) map.get("RYID");
				SyRkglLdrkdjb ldrk=syRkglLdrkdjbService.loadSyLdrkDeatil(ryid);
				byte[] data=syRkglLdrkdjbPicService.loadRkglPic(ldrk.getSfzh());
				String str1="";
				if(data!=null){
					str1=java.util.Base64.getEncoder().encodeToString(data);
				}
				Map<String,Object> ldrkMap=new HashMap<String,Object>();
				ldrkMap.put("xm", ldrk.getXm());
				ldrkMap.put("csrq", ldrk.getCsrq());
				ldrkMap.put("sfzh", ldrk.getSfzh());
				ldrkMap.put("mz", ldrk.getMz());
				ldrkMap.put("zzmm", ldrk.getZzmm());
				ldrkMap.put("dz", ldrk.getXxdz());
				ldrkMap.put("xp", str1);
				ldrkCount.add(ldrkMap);
			}
		}
			long jwrycount=syFwJwryService.loadDayJwryWorkCount(workInfo);
			List<Map<String,Object>> jwryFwList=syFwJwryService.loadFwJwryList(workInfo);
			List<Map<String,Object>>  jwryCount = new ArrayList<Map<String, Object>>();
			if(jwryFwList==null||jwryFwList.size()<1){
				jwryCount=null;
			}else {
				for (int j = 0; j < jwryFwList.size(); j++) {
					Map<String, Object> map = jwryFwList.get(j);
					String ryid=(String) map.get("RYID");
					Syjwry jwry=syjwryService.loadSyjwryDeatil(ryid);
					byte[] data=syjwryPicService.loadRkglPic(jwry.getZjhm());
					String str1="";
					if(data!=null){
						 str1=java.util.Base64.getEncoder().encodeToString(data);
					}
					Map<String,Object> jwryMap=new HashMap<String,Object>();
					jwryMap.put("xm", jwry.getYwm());
					jwryMap.put("csrq", jwry.getCsrq());
					jwryMap.put("gj", jwry.getGj());
					jwryMap.put("zjzl", jwry.getZjzl());
					jwryMap.put("xxdz", jwry.getXxdz());
					jwryMap.put("zjhm", jwry.getZjhm());
					jwryMap.put("xp", str1);
					jwryCount.add(jwryMap);
				}
		}
			long jzwCount=jzwjbxxService.loadWorkJzwCount(workInfo);
			List<Map<String, Object>> jzwJbxxresult=jzwjbxxService.loadTodayWorkDetail(workInfo);
			Map<String ,Object> map=Maps.newHashMap();
			map.put("czrkcount", czrkcount);
			map.put("carkCount", carkCount);
			map.put("ldrkcount", ldrkcount);
			map.put("ldrkCount", ldrkCount);
			map.put("jwrycount", jwrycount);
			map.put("jwryCount", jwryCount);
			map.put("jzwCount", jzwCount);
			map.put("jzwJbxxresult", jzwJbxxresult);
			return new KJSONMSG(200,"数据加载成功", map);
		//return new KJSONMSG(200,"数据加载成功", dataSet);
		
	}
	
		
	
	
	@AuthWidgetRule(value="device.loadworkCount",desc="常驻人口数据列表",operateType="W",refTable="APP_VERSION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadworkCount", method = RequestMethod.POST)
	public @ResponseBody Object loadworkCount(@RequestBody Map person) {
		KAssert.hasText(person.get("personId"), "人员信息不能为空");
		Map<String ,Object> map=Maps.newHashMap();
		map.put("personId", person.get("personId"));
		String day=(String) person.get("day");
			List<String> rglist=Arrays.asList(new String[]{"1","3","7","15","30"});
			if(!rglist.contains(day)){
				return ajaxDoneError("时间区间有误!");
			}
		if("1".equals(day)){
			map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getCurrentDateStr(ZDateStyle.YYYY_MM_DD)));
		}else if("7".equals(day)){
			map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getdefaultFormatDate(ZDateUtil.addDay(new Date(), -7))));
		}else if("15".equals(day)){
			map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getdefaultFormatDate(ZDateUtil.addDay(new Date(), -15))));
		}else if("3".equals(day)){
			map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getdefaultFormatDate(ZDateUtil.addDay(new Date(), -3))));
		}else if("30".equals(day)){
			map.put("djsj", ZDateUtil.getdefaultDateStart(ZDateUtil.getdefaultFormatDate(ZDateUtil.addMonth(new Date(), -1))));
		}
		//long czrkCount=fwCzrkService.loadTodayworkCount(map);
		long czrkcount=fwCzrkService.loadDayCZRKWorkCount(map);
		long ldrkcount=syFwLdrkService.loadDayLdrkWorkCount(map);
		//long ldrkCount=syRkglLdrkdjbService.loadldrkWorkCount(map);
		long jwrycount=syFwJwryService.loadDayJwryWorkCount(map);
		//long jwrycount=syjwryService.loadTodayWorkCount(map);
		long jzwCount=jzwjbxxService.loadWorkJzwCount(map);
		Map<String ,Object> mapCount=Maps.newHashMap();
		mapCount.put("czrkCount", czrkcount);
		mapCount.put("ldrkCount", ldrkcount);
		mapCount.put("jwrycount", jwrycount);
		mapCount.put("jzwCount", jzwCount);
		return new KJSONMSG(200,"数据加载成功", mapCount);
	}
	@AuthWidgetRule(value="device.enterDetailFwRk",desc="房间人口列表",operateType="W",refTable="APP_VERSION",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailFwRk",method=RequestMethod.POST)
	public @ResponseBody Object enterDetailFwRk(@RequestBody Map FW) {
		String fwid=(String) FW.get("jzwfjid");
		SyFwjbxx fwjbxx = fwjbxxService.loadSYFwjbxxByFjbm(fwid);//房屋
//		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjIdKey(fwid);//常住
		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoFwByJzwfjIdKey(fwid);//常住  2016-08-11加载 当前所住人口
		
		List<Map<String,Object>>  xpMap = new ArrayList<Map<String, Object>>();
		
		if(czrk!=null && czrk.size()>0){
			Map<String ,Object> czrkXp=Maps.newHashMap();
			for(SyRkglCzrk sy:czrk){
		    	byte[] data=syRkglCzrkPicService.loadRkglPic(sy.getGmsfhm());
				String str="";
				if(data!=null){
					str=java.util.Base64.getEncoder().encodeToString(data);
				}
				czrkXp.put("xp", str);
				czrkXp.put("id", sy.getRkid());
				xpMap.add(czrkXp);
		    }
		}
		
//		List<SyRkglLdrkdjb> ldrk=syRkglLdrkdjbService.selectSyLdrkInfoByJzwfjIdKey(fwid);//流动
		List<SyRkglLdrkdjb> ldrk=syRkglLdrkdjbService.selectSyLdrkInfoFwByJzwfjIdKey(fwid);//流动 2016-08-11 加载 当前所住人口
		if(ldrk!=null && ldrk.size()>0){
			Map<String ,Object> ldrkXp=Maps.newHashMap();
			for(SyRkglLdrkdjb ld :ldrk){
				byte[] data=syRkglLdrkdjbPicService.loadRkglPic(ld.getSfzh());
				String str1="";
				if(data!=null){
					str1=java.util.Base64.getEncoder().encodeToString(data);
				}
				ldrkXp.put("xp", str1);
				ldrkXp.put("id", ld.getLdid());
				xpMap.add(ldrkXp);
		    }
		}
		
//		List<Syjwry> jwry=syjwryService.selectJwryInfoByJzwfjIdKey(fwid);//境外
		List<Syjwry> jwry=syjwryService.selectJwryInfoFwByJzwfjIdKey(fwid);//境外 2016-08-11 加载 当前所住人口
		if(jwry!=null && jwry.size()>0){
			Map<String ,Object> jwryXp=Maps.newHashMap();
			for(Syjwry jw : jwry){
				byte[] data=syjwryPicService.loadRkglPic(jw.getZjhm());
				String str1="";
				if(data!=null){
					 str1=java.util.Base64.getEncoder().encodeToString(data);
				}
				jwryXp.put("xp", str1);
				jwryXp.put("id", jw.getJwryid());
				xpMap.add(jwryXp);
		    }
		}
		
		Map<String ,Object> map=Maps.newHashMap();
		map.put("fwjbxx", fwjbxx);
		map.put("czrk", czrk);
		map.put("ldrk", ldrk);
		map.put("jwry", jwry);
		map.put("xpMap", xpMap);
		return new KJSONMSG(200,"数据加载成功", map);
		
	}
	
	
	/**
	 * 根据门楼牌号编号 查询是否存在建筑物
	 * @param Mlph
	 * @return
	 */
	@RequestMapping(value="/selectJzwForMlph",method=RequestMethod.POST)
	@ResponseBody
	public KJSONMSG selectJzwForMlph(@RequestBody Map Mlph){
		String mlph=(String)Mlph.get("mlphid");
		List<Map<String,Object>> list=jzwjbxxService.selectJzwForMlph(mlph);
		KJSONMSG json=new KJSONMSG();
		if(list.size()>0&&list!=null){
			json=new KJSONMSG(200,"");
		}else{
			json=new KJSONMSG(201,"");
		}
		return json;
	}
	
	@RequestMapping(value="/insertAppException",method=RequestMethod.POST)
	@ResponseBody
	public void insertAppException(@RequestBody Map<String,Object> params){
		appExceptionLogService.insertAppException(params);
	}
	
	//移动端下载APP记录
	@RequestMapping(value="/saveUserAppInfo",method=RequestMethod.POST)
	@ResponseBody
	public KJSONMSG saveUserAppInfo(@RequestBody  Map userAppInfo){
		userAppInfoService.saveUserAppINfo(userAppInfo);
		return new KJSONMSG(200, "操作成功");
	}
	
	//--------------------保存移动端版本信息---------------------------------------
	@RequestMapping(value="/saveAppVersionUser",method=RequestMethod.POST)
	@ResponseBody
	public Object saveAppVersionUser(@RequestBody Map appVersionUser){
		String userId=(String) appVersionUser.get("logId");
		AppVersonUser appversionUser=appVersonUserService.selectAppVersion(userId);
		if(appversionUser==null||"".equals(appversionUser)){
			AppVersonUser appVersonUser=new AppVersonUser();
			appVersonUser.setId(UUIDUtil.uuid());
			appVersonUser.setUserid((String) appVersionUser.get("logId")==null?"":(String) appVersionUser.get("logId"));
			appVersonUser.setVersoninfo((String) appVersionUser.get("bbCode")==null?"":(String) appVersionUser.get("bbCode"));
			appVersonUser.setIsvalid((String) appVersionUser.get("isBbCode")==null?"":(String) appVersionUser.get("isBbCode"));
			appVersonUserService.insertAppUser(appVersonUser);
		}else{
			appversionUser.setIsvalid((String) appVersionUser.get("isBbCode")==null?"":(String) appVersionUser.get("isBbCode"));
			appversionUser.setVersoninfo((String) appVersionUser.get("bbCode")==null?"":(String) appVersionUser.get("bbCode"));
			appVersonUserService.updateAppUser(appversionUser);
		}
		return new KJSONMSG(200,"操作成功");
		
		
		
	}
	
	
	@RequestMapping(value="/deleteFwRyxx",method=RequestMethod.POST)
	@ResponseBody
	public Object deleteFwRyxx(@RequestBody Map map){
		try {
			fwjbxxService.deleteFwRyxx(map);
		} catch (Exception e) {
			return new KJSONMSG(300,"操作失败");
		}
		return new KJSONMSG(200,"操作成功");
	}
	//--------------------移动端动态加载地图---------------------------------------
//	@RequestMapping(value="/selectMcBySszdyjxzqy",method=RequestMethod.POST)
//	@ResponseBody
//	public Object selectXzqhmcBySszdyjxzqyDzbm(String dzbm){
//		String mc=jlxService.selectXzqhmcBySszdyjxzqyDzbm(dzbm);
//		Map<String,Object> map=Maps.newHashMap();
//		map.put("MC", mc);
//		KJSONMSG json=new KJSONMSG(200,"加载成功",map);
//		return json;
//	} 
	
}
