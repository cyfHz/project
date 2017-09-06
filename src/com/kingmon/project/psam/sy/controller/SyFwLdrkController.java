package com.kingmon.project.psam.sy.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import org.apache.lucene.index.MultiFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dragonsoft.commons.util.RkQueryUtil;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.date.ZDateStyle;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyFwLdrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyFwCzrkService;
import com.kingmon.project.psam.sy.service.ISyFwLdrkService;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.xzjd.model.Xzjd;

@Controller
@RequestMapping("/psam/sy/syfwLdrk")
public class SyFwLdrkController extends KBaseController{
	private static final String prefix = "psam/sy/syfwLdrk/";
	@Autowired
	private ISyFwLdrkService syFwLdrkService;
	@Autowired
	private ISyRkglLdrkdjbService syRkglLdrkdjbService;
	
	
	@AuthWidgetRule(value="syfwLdrk.saveSyFwLdrkAccInfo",desc="流动人员信息采集",operateType="W",refTable="SY_FW_LDRK" ,crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwLdrkAccInfo",method=RequestMethod.GET)
	public Object enterSyFwLdrkAccInfo(String jzwfjid, Model model) {
		SyRkglLdrkdjb ldrk = syFwLdrkService.loadLdrkAccInfo(jzwfjid);
		setDataAttribute(model, ldrk, "ldrk");
		return prefix+"fwLdrkAccquisition";
	}
	
	@AuthWidgetRule(value="syfwLdrk.saveSyFwLdrkAccInfo",desc="验证流动人口信息是否完整",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/valiateSyFwLdrkAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object valiateSyFwLdrkAccInfo(String jzwfjid, Model model){
		SyRkglLdrkdjb ldrk = syFwLdrkService.loadLdrkAccInfo(jzwfjid);
		return new KJSONMSG(200,"数据加载成功", ldrk);
	}
	
	
	@AuthWidgetRule(value="syfwLdrk.saveSyFwLdrkAccInfo",desc="流动人口信息采集",operateType="W",refTable="SY_FW_LDRK",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/saveSyFwLdrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object saveSyFwLdrkAccInfo(
			@RequestParam(value="imgfile",required= false )MultipartFile file, 
			@ModelAttribute("ldrk")SyRkglLdrkdjb ldrk){
		byte[] picbytes = null;
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
			syFwLdrkService.saveSyFwLdrkAccInfo(ldrk,picbytes);
	    	}catch(Exception e){
	    		if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
		
		
		return ajaxDoneSuccess("操作成功");
	}
	
	@AuthWidgetRule(value="syfwLdrk.loadLdrkFj",desc="根据建筑物Id加载流动人口信息",operateType="W",refTable="SY_FW_LDRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/loadLdrkFj",method=RequestMethod.POST)
	@ResponseBody
	public Object loadLdrkFj(String jzwId){
		List<String> fjidList=syFwLdrkService.loadLdrkFjByJzwId(jzwId);
		String resType="ldrk";
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
//		fjidList.add("443E79BE-D21A-413E-901B-8B01D7D01519");
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	} 
//	@RequestMapping(value = "/validateLdrk", method = RequestMethod.POST)
//	@ResponseBody
//	public SyFwLdrk validateLdrk(String sfzh,String jzwfjid) {
//		SyFwLdrk ldrk=syFwLdrkService.validateLdrk(sfzh,jzwfjid);
//	    return ldrk;
//	} 
	@AuthWidgetRule(value="syfwLdrk.loadLdrkInfo",desc="根据房间和身份证号加载流动人口信息",operateType="W",refTable="SY_FW_LDRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadLdrkInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object loadLdrkInfo(String sfzh,String jzwfjid) {
		SyFwLdrk ldrk=syFwLdrkService.validateLdrk(sfzh,jzwfjid);
		if(ldrk!=null){
			return new  KJSONMSG(300,"该采集信息已经存在");
		}
		SyRkglLdrkdjb glldrk=syRkglLdrkdjbService.selectLdrkInfoByZjbh(sfzh);
		if(glldrk!=null){
			glldrk.setFjbm(jzwfjid);
			return new KJSONMSG(200,"数据加载成功", glldrk);
		}

		//API ----return new KJSONMSG(202,"数据加载成功", rkczrk);
		Map<String,String> strReturns=syRkglLdrkdjbService.queryRkInfo(sfzh);
		if(strReturns!=null){
			return new KJSONMSG(202, "数据加载成功", strReturns);
		}
		
		//----------------------
		//没查询到数据，进习 手填 
		return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
	}
}
