package com.kingmon.project.psam.sy.controller;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
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

import com.dragonsoft.adapter.service.QueryAdapterSend;
import com.dragonsoft.commons.util.RkQueryUtil;
import com.dragonsoft.commons.util.XmlParser;
import com.dragonsoft.pci.exception.InvokeServiceException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jwq.model.Jwq;
import com.kingmon.project.psam.sy.model.SyFwCzrk;
import com.kingmon.project.psam.sy.model.SyFwjbxx;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.service.ISyFwCzrkService;
import com.kingmon.project.psam.sy.service.ISyFwjbxxService;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;


@Controller
@RequestMapping("/psam/sy/syfwCzrk")
public class SyFwCzrkController extends KBaseController{
	private static final String prefix = "psam/sy/syfwCzrk/";
	@Autowired
	private ISyFwCzrkService fwCzrkService;
	@Autowired
	private ISyFwjbxxService fwjbxxService;
	@Autowired
	private ISyRkglCzrkService  syRkglCzrkService;
	
	
	@AuthWidgetRule(value="syfwCzrk.savefwCzrkAccInfo",desc="进入常住人口采集页面",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterSyFwCzrkAccInfo",method=RequestMethod.GET)
	public Object enterSyFwCzrkAccInfo(String jzwfjid, Model model) {
		SyRkglCzrk czrk = fwCzrkService.loadFwCzrkAccInfo(jzwfjid);
		setDataAttribute(model, czrk, "czrk");
		return prefix+"fwCzrkAccquisition";
	}
	@AuthWidgetRule(value="syfwCzrk.savefwCzrkAccInfo",desc="验证常住人口信息是否完整",operateType="W",refTable="DZ_JZWFJ",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/valiateSyFwCzrkAccInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object valiateSyFwCzrkAccInfo(String jzwfjid, Model model){
		SyRkglCzrk czrk = fwCzrkService.loadFwCzrkAccInfo(jzwfjid);
		return new KJSONMSG(200,"数据加载成功", czrk);
	}
	@AuthWidgetRule(value="syfwCzrk.savefwCzrkAccInfo",desc="常住人口信息采集",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_ADD)
	@RequestMapping(value="/savefwCzrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object savefwCzrkAccInfo(@RequestParam(value = "imgfile", required = false) MultipartFile file, @ModelAttribute("czrk")SyRkglCzrk czrk){
		byte[] picbytes = null;
		try {
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
			fwCzrkService.savefwCzrkAccInfo(czrk,picbytes);
		
		} catch (Exception e) {
			if (e instanceof ServiceLogicalException) {
				return new KJSONMSG(300,e.getMessage());
			}else{
				return new KJSONMSG(400,"服务器错误");
			}
		}
		return ajaxDoneSuccess("操作成功");
	}	
	@AuthWidgetRule(value="syfwCzrk.loadCzrkFj",desc="根据建筑物Id加载常住人口信息",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/loadCzrkFj",method=RequestMethod.POST)
	@ResponseBody
	public Object loadCzrkFj(String jzwId){
		List<String> fjidList=fwCzrkService.loadCzrkFjByJzwId(jzwId);
		String resType="czrk";
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	}
	@AuthWidgetRule(value="syfwCzrk.loadCzrkInfo",desc="根据房间和身份证号加载常住人口信息",operateType="W",refTable="SY_FW_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadCzrkInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object loadCzrkInfo(String sfzh,String jzwfjid) {
		SyFwCzrk czrk=fwCzrkService.validateCzrk(sfzh,jzwfjid);
		if(czrk!=null){
			return new  KJSONMSG(300,"该采集信息已经存在");
		}
		SyRkglCzrk rkczrk=syRkglCzrkService.selectCzrkInfoByZjbh(sfzh);
		if(rkczrk!=null){
			rkczrk.setJzwfjid(jzwfjid);
			return new KJSONMSG(200,"数据加载成功", rkczrk);
		}
		//API ----查询身份证库;
	   // Map<String,String> strReturns=RkQueryUtil.queryCzrk(sfzh);
	
		Map<String,String> strReturns=syRkglCzrkService.queryRkInfo(sfzh);
		if(strReturns!=null){

			return new KJSONMSG(202,"数据加载成功", strReturns);
		}
//		
		//----------------------
		//没查询到数据，进习 手填 
		return new KJSONMSG(201,"未询到数据");//为查询到人员信息，
	}
	
	@RequestMapping(value="/loadZdrkFj",method=RequestMethod.POST)
	@ResponseBody
	public Object loadZdrkFj(String jzwId){
		List<String> fjidList=fwCzrkService.loadCzrkFjByJzwId(jzwId);
		String resType="zdrk";
		Map<String,Object> reMap=Maps.newHashMap();
		reMap.put("resType", resType);
		reMap.put("fjidList", fjidList);
		KJSONMSG msg=new KJSONMSG(200,"数据加载成功", reMap);
		return msg;
	}
	

}
