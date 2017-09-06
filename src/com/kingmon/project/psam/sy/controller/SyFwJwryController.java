package com.kingmon.project.psam.sy.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.sy.model.SyFwJwry;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyFwJwryService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyjwryService;
import com.kingmon.project.psam.xzjd.model.Xzjd;

@Controller
@RequestMapping("/psam/sy/syFwJwry")
public class SyFwJwryController extends KBaseController{
	private static final String prefix = "psam/sy/syfwJwry/";
	@Autowired
	private ISyFwJwryService syFwJwryService;
	@Autowired
	private ISyjwryService syjwryService;
	
	@AuthWidgetRule(value="syfwJwry.saveSyFwJwryAccInfo",desc="境外人员信息采集",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwJwryAccInfo",method=RequestMethod.GET)
	public Object enterSyFwJwryAccInfo(String jzwfjid, Model model) {
		Syjwry jwry = syFwJwryService.loadSyjwryAccInfo(jzwfjid);
		setDataAttribute(model, jwry, "jwry");
		return prefix+"fwJwryAccquisition";
	}
	
	@AuthWidgetRule(value="syfwJwry.saveSyFwJwryAccInfo",desc="验证境外人员信息是否完整",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/valiateSyFwJwryAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object valiateSyFwJwryAccInfo(String jzwfjid, Model model){
		Syjwry jwry = syFwJwryService.loadSyjwryAccInfo(jzwfjid);
		return new KJSONMSG(200,"数据加载成功", jwry);
	}
	@AuthWidgetRule(value="syfwJwry.saveSyFwJwryAccInfo",desc="境外人员信息采集",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveSyFwJwryAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveSyFwJwryAccInfo(@RequestParam(value="imgfile",required= false )MultipartFile file, @ModelAttribute("jwry")Syjwry jwry){
		byte[] picbytes=null;
		try{
			if(file!=null && file.getSize()>0){
				String fileFullName=file.getOriginalFilename();
				int typePositon=fileFullName.lastIndexOf(".");
				if(typePositon<=0){
					return ajaxDoneError("您上传的图片格式不正确，请重新选择!");
				}
				String extenName=file.getOriginalFilename().substring(typePositon+1);
				List<String> list=Arrays.asList(new String[]{"jpg","jpeg","png","JPG","PNG","JPEG","gif","GIF"});
				if(!list.contains(extenName)){
					return ajaxDoneError("您上传的图片格式不正确，请重新选择!");
				}
				long size=file.getSize();
				if(size>200*1024){
					return ajaxDoneError("上传图片不能大于200KB");
				}
				picbytes=file.getBytes();
			  }
			syFwJwryService.saveSyFwJwryAccInfo(jwry,picbytes);
	    	}catch(Exception e){
	    		if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
		
		
		return ajaxDoneSuccess("操作成功");
	}
	
	@AuthWidgetRule(value="syfwJwry.loadJwryFj",desc="根据建筑物Id加载房间",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/loadJwryFj",method=RequestMethod.POST)
	@ResponseBody
	public Object loadJwryFj(String jzwId){
		List<String> fjidList=syFwJwryService.loadJwryFjByJzwId(jzwId);
		String resType="jwry";
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	}
	@AuthWidgetRule(value="syfwJwry.loadJwryInfo",desc="省份证号和建筑物ID加载境外人员信息",operateType="W",refTable="SY_FW_JWRY",crudType=KConstants.OPER_SEARCH)
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
			gljwry.setFjbm(jzwfjid);
			return new KJSONMSG(200,"数据加载成功", gljwry);
		}

		//API ----return new KJSONMSG(202,"数据加载成功", rkczrk);
//		Map<String,String> strReturns=RkQueryUtil.queryCzrk(sfzh);
		Map<String,String> strReturns=syjwryService.queryRkInfo(sfzh);
		if(strReturns!=null){
			return new KJSONMSG(202, "数据加载成功", strReturns);
		}
		//----------------------
		//没查询到数据，进习 手填 
		return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
	}
	
}
