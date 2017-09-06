package com.kingmon.base.web;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.exception.ValidateException;
import com.kingmon.base.exception.auth.UnauthenticatedException;
import com.kingmon.base.exception.auth.UnauthorizedException;
import com.kingmon.base.util.RequestUtil;
import com.kingmon.project.common.log.service.ILogErrorClobService;
import com.kingmon.project.common.log.service.ILogErrorService;

public class KBaseController {
	
	protected static final String forward="forward";
	protected static final String closeCurrent="closeCurrent";
	
	protected static final String defaultAjaxSuccess="数据修改成功";
	protected static final String defaultAjaxError="数据修改失败 : ";
	protected static final String defaultValidateError="数据校验失败 : ";
	protected static final String defaultAuthError= "权限认证失败 : ";
	protected static final String defaultServerError= "服务器错误 : ";
	
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ILogErrorService errorService;
	@Autowired
	private ILogErrorClobService clobService;
	
	protected KJSONMSG ajaxDone(int statusCode, String message, String forwardUrl) {
		KJSONMSG msg = new KJSONMSG();
		msg.setStatusCode(statusCode);
		msg.setMessage(""+message);
		msg.setForwardUrl(""+forwardUrl);
		return msg;
	}

	protected KJSONMSG ajaxDoneSuccess(String message) {
		return ajaxDone(KConstants.SC_200, message, "");
	}
	protected KJSONMSG ajaxDoneError(String message) {
		return ajaxDone(KConstants.SC_300, message,"");
	}
	protected KJSONMSG ajaxServerError(String message) {
		return ajaxDone(KConstants.SC_500, message, "");
	}
	protected KJSONMSG ajaxAuthError(String message) {
		return ajaxDone(KConstants.SC_401, message, "");
	}
	
	
	//--------------------------------------------------AAAA----------------------------------------------
	/**
	 * 
	 * @param model
	 * @param data
	 * @param dataName
	 */
	protected static void setDataAttribute(Model model, Object data,String dataName) {
		if(model==null||data==null||dataName==null){
			return;
		}
		model.addAttribute(dataName,data);
	}
//-----------------------------------------------------------------------------------------------------------	
	
	protected static void setDataAttribute(ModelAndView modelView, Object data,String dataName) {
		if(modelView==null||data==null||dataName==null){
			return;
		}
		modelView.addObject(dataName,data);
	}
	
//-----------------------------------------------------------------------------------------------------------	
	
	protected void processValidateResult(BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			throw new ValidateException(ValidateHandler.getDefaultError(bindingResult));
		}
	}

	protected ModelAndView returnServiceLogicError(String errorMsg) {
		ModelAndView mav = new ModelAndView("error/serviceLogicError");
		mav.addObject("serviceLogicError", errorMsg);
		return mav;
	}
		
	//ModelAndView(String viewName, Map model)
	protected  ModelAndView returnMeaasgeMV(String viewName,String msg) {
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("message", msg);
		return mav;
	}
	
	@ExceptionHandler
	@ResponseBody
	//@Transactional
	public Object processGlobalExceptions(HttpServletRequest request,Exception e ) throws Exception {
		if(logger.isDebugEnabled()){
			e.printStackTrace();
		}
		//request.setAttribute("exceptionMessage", e);
		boolean isAcceptJson=RequestUtil.isAcceptJson(request);
		if (e instanceof ServiceLogicalException) {
			if(isAcceptJson){
				return ajaxDoneError(e.getMessage());
			}else{
				return returnMeaasgeMV("static/info",e.getMessage());
			}
		} else if (e instanceof ValidateException) {
			if(isAcceptJson){
				return ajaxDoneError(defaultValidateError + e.getMessage());
			}else{
				return returnMeaasgeMV("static/info",e.getMessage());
			}
		} else if (e instanceof UnauthorizedException) {//未授权
			 if(isAcceptJson){
				 return ajaxAuthError(defaultAuthError + e.getMessage());
			 }else{
				return returnMeaasgeMV("static/info","权限认证失败 :"+e.getMessage());
			 }
		}else if (e instanceof UnauthenticatedException) {//未认证
			 if(isAcceptJson){
				 return ajaxAuthError(defaultAuthError + e.getMessage());
			 }else{
				return returnMeaasgeMV("static/info","权限认证失败 :"+e.getMessage());
			 }
		}else if(e instanceof Exception){
			 if(isAcceptJson){
//				 return ajaxServerError(defaultServerError + e.getMessage());
				 return ajaxServerError(defaultServerError );
			 }else{
//				return returnMeaasgeMV("static/info",defaultServerError + e.getMessage());
				return returnMeaasgeMV("static/info",defaultServerError );
			 }
		}else{
			    return returnMeaasgeMV("static/info",defaultServerError + e.getMessage());
		}
	} 
}
