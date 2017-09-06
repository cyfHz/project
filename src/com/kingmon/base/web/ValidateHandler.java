package com.kingmon.base.web;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
public class ValidateHandler {
        
        public static String getDefaultError(BindingResult result){
            if(result.hasErrors()){
            	List<FieldError> fieldEist =result.getFieldErrors();
            	if(fieldEist!=null&&fieldEist.size()>0){
            		FieldError fieldError=fieldEist.get(0);
            		fieldError.getCode();
            		String prefix="\r\n"+"所提交数据：";
            		String value="{"+(fieldError.getRejectedValue()==null?" ":fieldError.getRejectedValue())+"}";
            		String message=""+fieldError.getDefaultMessage();
            		return prefix+""+value+"\r\n"+message;
            	}else{
            		 List<ObjectError> allErrorlist = result.getAllErrors();
                     ObjectError oe = allErrorlist.get(0);
                     return ""+oe.getDefaultMessage();
            	}
            }else{
            	return null;
            }
        }

}
