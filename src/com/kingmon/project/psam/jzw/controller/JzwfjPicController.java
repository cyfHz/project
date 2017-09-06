package com.kingmon.project.psam.jzw.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.jzw.model.JzwfjPic;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;
import com.kingmon.project.psam.jzw.serivice.IJzwfjService;
@Controller
@RequestMapping("/psam/jzwfjPic")
public class JzwfjPicController extends KBaseController{
	private static final String prefix = "psam/jzw/";
	@Autowired
	private IJzwfjService jzwfjService;
	@Autowired
	private IJzwfjPicService jzwfjPicService;
	
//	@AuthWidgetRule(value="jzwfjPic",desc="建筑物房间照片管理",operateType="R",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_SEARCH)
//	@RequestMapping(value = {"/",""})
//	public String enterjzwfjPicManage() {
//		return prefix + "jzwfjPicDataGrid";
//	}
	@AuthWidgetRule(value="jzwfj.loadJzwFjPic",desc="房间照片加载",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwFjPic", method = RequestMethod.GET)
	public void loadJzwFjPic(HttpServletResponse response ,String jzwfjPicId){
		 byte[] data=jzwfjPicService.loadJzwfjPic(jzwfjPicId);
		 OutputStream op = null;
		 if(data!=null){
			try {
				op = response.getOutputStream();
				op.write(data);// 直接显示到网页上
				op.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					op.close();
				} catch (IOException e) {
				}
			}
		 }
	}
       
	@AuthWidgetRule(value="jzwfj.uploadJzwFjPic",desc="房间照片上传",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "uploadJzwFjPic", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadJzwFjPic(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "file", required = false) MultipartFile file) {
		if (file.isEmpty()) {  
			 return ajaxDoneError("上传文件为空");
		}
		String fileName = file.getOriginalFilename();
		String extensionName = fileName.substring(fileName.lastIndexOf('.'));
		if(KConstants.ALLOW_UPLOAD_IMAGE_TYPE.contains(extensionName)){
			return ajaxDoneError("未知图片类型");
		}
		String jzwfjid= request.getParameter("jzwfjid");
		if(!StringUtils.hasText(jzwfjid)){
			return ajaxDoneError("所属房间ID为空，请检查数据");
		}
		JzwfjPic jzwfjPic=new JzwfjPic();
		try {
			jzwfjPic.setPic(file.getBytes());
		} catch (IOException e) {
			return ajaxServerError("文件解析错误");
		}
		jzwfjPic.setCreatTime(new Date());
		jzwfjPic.setPicid(UUIDUtil.uuid());
		jzwfjPic.setUpdatedTime(new Date());
		String picid=jzwfjPicService.addJzwFjPic(jzwfjPic, jzwfjid);
//		return ajaxDoneSuccess(file.getOriginalFilename() + "上传成功");
		return ajaxDoneSuccess(picid);
	}
	@AuthWidgetRule(value="jzwfj.deleteJzwFjpic",desc="建筑物房间照片删除",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_DELETE)
	@RequestMapping(value = "/deleteJzwFjpic", method = RequestMethod.POST)
	@ResponseBody 
	public Object deleteJzwFjpic(String jzwfjPicId) {
		jzwfjPicService.deletefwpic(jzwfjPicId);
		return ajaxDoneSuccess("照片删除成功！");
	}
 
}
