package com.kingmon.project.demo.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.util.FastjsonUtil;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.demo.model.User;
import com.kingmon.project.demo.service.IUserService;

//@Controller
//@RequestMapping("/demo/user")
public class UserController extends KBaseController{
	
	private static final String prefix="demo/";
	
	@Autowired
	private IUserService userService;
	
	
	@RequestMapping(value="/enterUserPage")
    public String enterUserPage(){
		return prefix+"userDataGrid";
    }
	@AuthWidgetRule(value="wrule_test_01_loadUserGridView",desc="")
    @RequestMapping(value="/loadUserDataGrid")
    @ResponseBody 
    public Object loadUserGridView(@ModelAttribute("paramObject") ParamObject paramObject){
       DataSet dataSet= userService.loadUserDataSet(paramObject);
	   return FastjsonUtil.convert(dataSet);
    }
	
	@AuthWidgetRule(value="wrule_test_02_enterAddUser",desc="")
    @RequestMapping(value="/enterAddUser")
    public String enterAddUser(){
        return prefix+"userAdd";
    }

    @RequestMapping(value="/addUser")
    @ResponseBody 
    public Object addUser( @ModelAttribute("user")User user,BindingResult bindingResult){
    	processValidateResult(bindingResult);
    	userService.addUser(user);
		return ajaxDoneSuccess("数据添加成功 ");
    }  

    @RequestMapping(value="/enterUpdateUser")
    public String enterUpdateUser(String id,Model model){
    	User user=userService.findByID(id);
    	setDataAttribute(model,user,"user");
        return prefix+"permissionEdit";
    }
	
    @RequestMapping(value="/updateUser")
    @ResponseBody 
    public Object updateUser( @ModelAttribute("user")User user,BindingResult bindingResult){
    	processValidateResult(bindingResult);
    	userService.updateUser(user);
		return ajaxDoneSuccess("数据修改成功 ");
    }

    @RequestMapping(value="/batchDeleteUser")
    @ResponseBody 
    public Object batchDeleteUser(String[] ids){
    	System.out.println(ids);
    	//userService.batchDeleteUser(ids);
		return ajaxDoneSuccess("数据删除成功 ");
    }
}
