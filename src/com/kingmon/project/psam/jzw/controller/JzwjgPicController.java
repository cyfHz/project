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
import com.kingmon.project.psam.jzw.model.JzwjgPic;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;
@Controller
@RequestMapping("/psam/jzwjgPic")
public class JzwjgPicController extends KBaseController{
	@Autowired
	private IJzwjgPicService jzwjgPicService;
	
	@AuthWidgetRule(value="jzwjbxx.loadJzwJgPic",desc="建筑物照片加载",operateType="W",refTable="DZ_JZWJG_PIC",crudType=KConstants.OPER_SEARCH)
	@RequestMapping(value = "/loadJzwJgPic", method = RequestMethod.GET)
	public void loadJzwJgPic(HttpServletResponse response ,String jzwjgPicId){
		 byte[] data=jzwjgPicService.loadJzwjgPic(jzwjgPicId);
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
	@AuthWidgetRule(value="jzwjbxx.deleteJzwJgPic",desc="删除建筑物照片",operateType="W",refTable="DZ_JZWJG_PIC",crudType=KConstants.OPER_DELETE)
	@ResponseBody
	@RequestMapping(value = "/deleteJzwJgPic", method = RequestMethod.POST)
	public Object deleteJzwJgPic(String jzwjgPicId){
		jzwjgPicService.deleteJzwjgPic(jzwjgPicId);
		return ajaxDoneSuccess("照片删除成功！");
	}
	
	@AuthWidgetRule(value="jzwjbxx.uploadJzwJgPic",desc="建筑物照片上传",operateType="W",refTable="DZ_JZWFJ_PIC",crudType=KConstants.OPER_ADD)
	@RequestMapping(value = "/uploadJzwJgPic", method = RequestMethod.POST)
	@ResponseBody
	public Object uploadJzwJgPic(
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
		String jzwjgid= request.getParameter("jzwjgid");
		if(!StringUtils.hasText(jzwjgid)){
			return ajaxDoneError("所属建筑物结构 ID为空，请检查数据");
		}
		JzwjgPic jzwjgPic=new JzwjgPic();
		try {
			jzwjgPic.setPic(file.getBytes());
		} catch (IOException e) {
			return ajaxServerError("文件解析错误");
		}
		jzwjgPic.setCreatTime(new Date());
		jzwjgPic.setPicid(UUIDUtil.uuid());
		jzwjgPic.setUpdatedTime(new Date());
		String picid=jzwjgPicService.addJzwJgPic(jzwjgPic, jzwjgid);
		return ajaxDoneSuccess(picid);
	}
}
