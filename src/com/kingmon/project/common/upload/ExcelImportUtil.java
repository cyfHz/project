package com.kingmon.project.common.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.psam.xzjd.service.impl.XzjdServiceImpl;

/**
 * EXCEL导入，POI技术实现
 * @ClassName: ExcelImportUtil 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Company: UJN </p> 
 * @version: v1.0
 * @author: 蒋金敏 
 * @date: 2015年10月29日 下午2:04:26
 */
public class ExcelImportUtil {

	public static final DateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat dateformat3 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	public static final DateFormat dateformat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final XzjdServiceImpl xzjdService = SpringBeanFacUtil.getBean(XzjdServiceImpl.class);
	
	public static String importFromExcel(HttpServletRequest request, HttpServletResponse response, MultipartFile file){
		String message="";
		try {
			String uploadType = request.getParameter("utype");
			String fileName = file.getOriginalFilename();
			String extensionName = fileName.substring(fileName.lastIndexOf('.'));
			if (extensionName != null && ".xls.xlsx".contains(extensionName.toLowerCase())) {
				//创建工作簿
				Workbook workbook = WorkbookFactory.create(file.getInputStream());
				if ("xzqh".equals(uploadType)) {
					//...
				}else if ("xzjd".equals(uploadType)) {
					message = xzjdService.importFromExcel(workbook,extensionName);
				}else if("".equals(uploadType)){
					//...
				}//...
				
			}else{
				message="请上传格式为.xls或.xlsx的文件。";
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
		return message;
	}
	
}
