package com.kingmon.project.common.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kingmon.base.web.KBaseController;
@Controller 
@RequestMapping("/common/sys/upload")
public class AjaxUploadController extends KBaseController {

    /**
     * @param request
     * @param files
     * @return
     */
    @RequestMapping(value = "ajaxUpload", method = RequestMethod.POST)
    @ResponseBody
    public Object ajaxUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file) {
    	    //导入excel
    		String message = ExcelImportUtil.importFromExcel(request,response,file);
    		if (!("".equals(message))) {
    			return ajaxDoneError(message);
			}
            return ajaxDoneSuccess(file.getOriginalFilename()+"上传成功");
    }
  
}
