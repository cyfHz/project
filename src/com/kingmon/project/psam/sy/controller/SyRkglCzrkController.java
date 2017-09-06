package com.kingmon.project.psam.sy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkService;

@Controller
@RequestMapping("/psam/sy/syCzrk")
public class SyRkglCzrkController extends KBaseController{
	private static final String prefix = "psam/sy/syCzrk/";
	
	@Autowired
	private ISyRkglCzrkService  syRkglCzrkService;
	
	
	@AuthWidgetRule(value="syCzrk.entersyCzrkPage",desc="常驻人口管理",operateType="R",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String entersyCzrkPage() {
		return prefix + "syCzrk";
	}
	
	
	@AuthWidgetRule(value="syCzrk.loadSyCzrkGrid",desc="常驻人口数据列表",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadSyCzrkGrid", method = RequestMethod.POST)
	public @ResponseBody DataSet loadFwGridView(@RequestParam  Map<String,String> params) {
		return syRkglCzrkService.loadSyCzrkDataSet(params);
	}
	
   @AuthWidgetRule(value="syCzrk.enterDetailSyCzrk",desc="常驻人口信息明细",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailSyCzrk",method=RequestMethod.GET)
	public String enterDetailSyCzrk(String rkid, Model model) {
		SyRkglCzrk czrk = syRkglCzrkService.loadSyCzrkDeatil(rkid);
		setDataAttribute(model, czrk, "czrk");
		return prefix + "syCzrkDetail";
	}
   @AuthWidgetRule(value="syCzrk.updateSyCzrkAccInfo",desc="进入常住人口编辑页面",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterUpdateCzrkAccInfo",method=RequestMethod.GET)
	public String enterUpdateCzrkAccInfo(String rkid, Model model) {
		SyRkglCzrk czrk = syRkglCzrkService.loadSyCzrkDeatil(rkid);
		setDataAttribute(model, czrk, "czrk");
		return prefix + "syCzrkEdit";
	}
    @AuthWidgetRule(value="syCzrk.updateSyCzrkAccInfo",desc="常驻人口信息更新",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/updateSyCzrkAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object updateSyCzrkAccInfo(@RequestParam(value = "imgfile", required = false) MultipartFile file, @ModelAttribute("czrk")SyRkglCzrk czrk){
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
		  	syRkglCzrkService.updateSyCzrkAccInfo(czrk,picbytes);
			} catch (Exception e) {
				if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
  
		return ajaxDoneSuccess("操作成功");
	}
	//人口信息  常住人口
   @AuthWidgetRule(value="syCzrk.entersyCzrkInfoPage",desc="房间常住人口信息查看",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
    @RequestMapping(value="/entersyCzrkInfoPage", method = RequestMethod.GET)
	public String entersyCzrkInfoPage(String jzwfjid, Model model) {
		List<SyRkglCzrk> czrk=syRkglCzrkService.selectSyCzrkInfoByJzwfjId(jzwfjid);
		setDataAttribute(model, czrk, "czrk");
		return prefix + "syCzrkInfo";
	} 
//	@AuthWidgetRule(value="syCzrk.loadSyCzrkInfoList",desc="根据建筑物房间加载常住人口信息",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = "/loadSyCzrkInfoList", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loadSyCzrkInfoList(String jzwfjid) {
//		DataSet dataSet = syRkglCzrkService.loadSyCzrkDataSet(jzwfjid);
//		return FastjsonUtil.convert(dataSet);
//	}
	
	
    @AuthWidgetRule(value="syCzrk.cancelCzrk",desc="注销常住人口信息",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/cancelCzrk", method = RequestMethod.POST)
	@ResponseBody
	public Object cancelCzrk(String id) {
		syRkglCzrkService.cancelCzrk(id);
		return ajaxDoneSuccess("注销常住人口成功！");
	}
    @AuthWidgetRule(value="syCzrk.activatesyCzrk",desc="启用常住人口信息",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/activatesyCzrk", method = RequestMethod.POST)
	@ResponseBody
	public Object activatesyCzrk(String id) {
		syRkglCzrkService.activatesyCzrk(id);
		return ajaxDoneSuccess("启用常住人口成功！");
	}
		
//    @RequestMapping(value = "/validateCzrk", method = RequestMethod.POST)
//		@ResponseBody
//		public SyRkglCzrk validateCzrk(String sfzh,String jzwfjid) {
//			SyRkglCzrk czrk=syRkglCzrkService.validateCzrk(sfzh,jzwfjid);
//		    return czrk;
//		} 
}
