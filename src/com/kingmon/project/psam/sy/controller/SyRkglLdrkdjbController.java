package com.kingmon.project.psam.sy.controller;

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
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.xzjd.model.Xzjd;


@Controller
@RequestMapping("/psam/sy/syLdrk")
public class SyRkglLdrkdjbController extends KBaseController{
	private static final String prefix = "psam/sy/syLdrk/";
	
	
	@Autowired
	private ISyRkglLdrkdjbService  syLdrkService;
	
	@AuthWidgetRule(value="syLdrk.enterSyLdrkPage",desc="流动人口管理",operateType="R",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value ={"/",""}, method = RequestMethod.GET)
	public String enterSyLdrkPage() {
		return prefix + "syLdrk";
	}	
	
	@AuthWidgetRule(value="syLdrk.loadSyLdrkGrid",desc="流动人口数据列表",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadSyLdrkGrid", method = RequestMethod.POST)
	public @ResponseBody DataSet loadSyLdrkGrid(@RequestParam  Map<String,String> params) {
		return syLdrkService.loadSyLdrkDataSet(params);
	}
	
	@AuthWidgetRule(value="syLdrk.enterDetailSyLdrk",desc="流动人口信息明细",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailSyLdrk",method=RequestMethod.GET)
	public String enterDetailSyLdrk(String rkid, Model model) {
		SyRkglLdrkdjb ldrk = syLdrkService.loadSyLdrkDeatil(rkid);
		System.out.println(ZDateUtil.utilDateToSqlDate(ldrk.getCsrq()));
		ldrk.setCsrq(ZDateUtil.utilDateToSqlDate(ldrk.getCsrq()));
		ldrk.setDdbdrq(ZDateUtil.utilDateToSqlDate(ldrk.getDdbdrq()));
		setDataAttribute(model, ldrk, "ldrk");
		return prefix + "syLdrkDetail";
	}
	@AuthWidgetRule(value="syLdrk.updateSyLdrkAccInfo",desc="流动人员更新",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateLdrkAccInfo",method=RequestMethod.GET)
	public String enterUpdateLdrkAccInfo(String rkid, Model model) {
		SyRkglLdrkdjb ldrk = syLdrkService.loadSyLdrkDeatil(rkid);
		ldrk.setCsrq(ZDateUtil.utilDateToSqlDate(ldrk.getCsrq()));
		ldrk.setDdbdrq(ZDateUtil.utilDateToSqlDate(ldrk.getDdbdrq()));
		setDataAttribute(model, ldrk, "ldrk");
		return prefix + "syLdrkEdit";
	}
	@AuthWidgetRule(value="syLdrk.updateSyLdrkAccInfo",desc="流动人员更新",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/updateSyLdrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object updateSyLdrkAccInfo(@RequestParam(value = "imgfile", required = false) MultipartFile file, @ModelAttribute("ldrk")SyRkglLdrkdjb ldrk){
//		
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
			syLdrkService.updateSyLdrkAccInfo(ldrk,picbytes);
			} catch (Exception e) {
				if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
  
		return ajaxDoneSuccess("操作成功");
	}
	//人口信息 ---流动人口
    @AuthWidgetRule(value="syLdrk.enterSyLdrkInfoPage",desc="房间流动人口信息查看",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/enterSyLdrkInfoPage", method = RequestMethod.GET)
	public String enterSyLdrkInfoPage(String jzwfjid, Model model) {
		List<SyRkglLdrkdjb> ldrk=syLdrkService.selectSyLdrkInfoByJzwfjId(jzwfjid);
		setDataAttribute(model, ldrk, "ldrk");
		return prefix + "syLdrkInfo";
	} 
//	@RequestMapping(value = "/loadSyLdrkInfoList", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loadSyLdrkInfoList(String jzwfjid) {
//		DataSet dataSet = syLdrkService.loadSyLdrkDataSet(jzwfjid);
//		return FastjsonUtil.convert(dataSet);
//	}
	 @AuthWidgetRule(value="syLdrk.cancelLdrk",desc="流动人口注销",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/cancelLdrk", method = RequestMethod.POST)
	@ResponseBody
	public Object cancelLdrk(String id) {
		syLdrkService.cancelLdrk(id);
		return ajaxDoneSuccess("注销成功！");
	}
    @AuthWidgetRule(value="syLdrk.activatesyLdrk",desc="流动人口启用",operateType="W",refTable="SY_RKGL_LDRKDJB",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/activatesyLdrk", method = RequestMethod.POST)
	@ResponseBody
	public Object activatesyLdrk(String id) {
		syLdrkService.activatesyLdrk(id);
		return ajaxDoneSuccess("启用成功！");
	}
}
