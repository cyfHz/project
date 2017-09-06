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
import com.kingmon.base.util.date.ZDateUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.Syjwry;
import com.kingmon.project.psam.sy.service.ISyjwryService;

@Controller
@RequestMapping("/psam/sy/syJwry")
public class SyjwryController extends KBaseController{
	private static final String prefix = "psam/sy/syJwry/";
	
	@Autowired
	private ISyjwryService  syjwryService;
	
	
	@AuthWidgetRule(value="syJwry.enterSyjwryPage",desc="境外人员管理",operateType="R",refTable="SY_SYJWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value ={"/",""}, method = RequestMethod.GET)
	public String enterSyjwryPage() {
		return prefix + "syJwry";
	}
	
	@AuthWidgetRule(value="syJwry.loadSyJwryGrid",desc="境外人员数据列表",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadSyJwryGrid", method = RequestMethod.POST)
	public @ResponseBody DataSet loadSyJwryGrid(@RequestParam  Map<String,String> params) {
		return syjwryService.loadSyjwryDataSet(params);
	}
	
	@AuthWidgetRule(value="syJwry.enterDetailSyJwry",desc="境外人员信息明细",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/enterDetailSyJwry",method=RequestMethod.GET)
	public String enterDetailSyJwry(String jwryid, Model model) {
		Syjwry jwry = syjwryService.loadSyjwryDeatil(jwryid);
		jwry.setCsrq(ZDateUtil.utilDateToSqlDate(jwry.getCsrq()));
		jwry.setTlyxq(ZDateUtil.utilDateToSqlDate(jwry.getTlyxq()));
		setDataAttribute(model, jwry, "jwry");
		return prefix + "syJwryDetail";
	}
	@AuthWidgetRule(value="syJwry.updateSyJwryAccInfo",desc="进入境外人员修改页面",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value = "/enterUpdateSyJwryAccInfo",method=RequestMethod.GET)
	public String enterUpdateSyJwryAccInfo(String jwryid, Model model) {
		Syjwry jwry = syjwryService.loadSyjwryDeatil(jwryid);
		jwry.setCsrq(ZDateUtil.utilDateToSqlDate(jwry.getCsrq()));
		jwry.setTlyxq(ZDateUtil.utilDateToSqlDate(jwry.getTlyxq()));
		setDataAttribute(model, jwry, "jwry");
		return prefix + "syJwryEdit";
	}
	@AuthWidgetRule(value="syJwry.updateSyJwryAccInfo",desc="境外人员信息更新",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_UPDATE)
	@RequestMapping(value="/updateSyJwryAccInfo",method=RequestMethod.POST)
	@ResponseBody
	public Object updateSyJwryAccInfo(@RequestParam(value = "imgfile", required = false) MultipartFile file, @ModelAttribute("jwry")Syjwry jwry){
	
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
			syjwryService.updateSyJwryAccInfo(jwry,picbytes);
			} catch (Exception e) {
				if (e instanceof ServiceLogicalException) {
					return new KJSONMSG(300,e.getMessage());
				}else{
					return new KJSONMSG(400,"服务器错误");
				}
			}
		return ajaxDoneSuccess("操作成功");
	}
	@AuthWidgetRule(value="syJwry.enterSyjwryInfoPage",desc="进入境外人员信息页面",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value="/enterSyjwryInfoPage", method = RequestMethod.GET)
	public String enterSyjwryInfoPage(String jzwfjid, Model model) {
		List<Syjwry> jwry=syjwryService.selectJwryInfoByJzwfjId(jzwfjid);
		setDataAttribute(model, jwry, "jwry");
		return prefix + "syJwryInfo";
	} 
//	@AuthWidgetRule(value="syJwry.loadSyJwryInfoList",desc="根据房间加载境外人员信息",operateType="W",refTable="SY_SYJWRY",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = "/loadSyJwryInfoList", method = RequestMethod.POST)
//	@ResponseBody
//	public Object loadSyJwryInfoList(String jzwfjid) {
//		DataSet dataSet = syjwryService.loadSyJwryDataSet(jzwfjid);
//		return FastjsonUtil.convert(dataSet);
//	}
	 @AuthWidgetRule(value="syJwry.cancelsyJwry",desc="注销境外人员信息",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
		@RequestMapping(value = "/cancelsyJwry", method = RequestMethod.POST)
		@ResponseBody
		public Object cancelsyJwry(String id) {
		 syjwryService.cancelsyJwry(id);
			return ajaxDoneSuccess("注销境外人口成功！");
		}
	    @AuthWidgetRule(value="syJwry.activatesyJwry",desc="启用境外人员信息",operateType="W",refTable="SY_RKGL_CZRK",crudType=KConstants.OPER_SEARCH)
		@RequestMapping(value = "/activatesyJwry", method = RequestMethod.POST)
		@ResponseBody
		public Object activatesyJwry(String id) {
			 syjwryService.activatesyJwry(id);
			return ajaxDoneSuccess("启用常住人口成功！");
		}
}
