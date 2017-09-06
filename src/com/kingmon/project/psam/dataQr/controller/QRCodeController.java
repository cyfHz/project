package com.kingmon.project.psam.dataQr.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.web.KBaseController;
import com.kingmon.common.annon.AuthWidgetRule;
import com.kingmon.project.psam.dataQr.service.IQRCodeService;
import com.kingmon.project.psam.dataQr.util.QRCodeUtil;
import com.kingmon.project.psam.dataQr.util.QrConfigModel;
import com.kingmon.project.psam.dataQr.util.QrConfigUtil;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.sdwangge.Des;

@Controller
@RequestMapping("/psam/qrcode")
public class QRCodeController extends KBaseController {
	private static final String prefix = "psam/qrcode/";
	
	@Autowired
	private IQRCodeService qrCodeService;
	@Autowired
	private MlphService mlphService;
	
	@AuthWidgetRule(value = "qrcode.enterQRCodePage", desc = "二维码", operateType = "W", refTable = "DZ_JZWJBXX", crudType = KConstants.VIEW_QRCODE)
	@RequestMapping(value = "/enterQRCodePage", method = RequestMethod.GET)
	public String enterQRCodePage(String code, String type, Model model) {
		QrConfigModel qrConfig= QrConfigUtil.getConfig(type);
		if(qrConfig==null){
			String msg = "查询类型错误或者不支持当前类型,请联系管理员!";
			setDataAttribute(model, msg, "msg");
			setDataAttribute(model, 300, "statusCode");
			return prefix + "qrCodePage";
		}
		KJSONMSG msg=qrCodeService.viewCodeFromPage(type,code);
		if(200!=msg.getStatusCode()){
			setDataAttribute(model, msg.getMessage(), "msg");
			setDataAttribute(model, 300, "statusCode");
		}else{
			Map<?,?> map=(Map<?,?>) msg.getData();
			setDataAttribute(model, 200, "statusCode");
			setDataAttribute(model, map.get("code"), "code");
			setDataAttribute(model, map.get("type"), "type");
			setDataAttribute(model, map.get("name"), "name");
			setDataAttribute(model, map.get("typeName"), "typeName");
		}
		return prefix + "qrCodePage";
	}

	@RequestMapping(value = "/showQRCode")
	public void showQRCode(String code, String type, Model model, HttpServletResponse response) {
		QrConfigModel qrConfig= QrConfigUtil.getConfig(type);
		if(qrConfig==null){
			return;
		}
		KJSONMSG msg= qrCodeService.viewCodeFromPage(type, code);
		if(200!=msg.getStatusCode()){
			return;  
		}else{
			Map<?,?> map= (Map<?,?>) msg.getData();
			String id=(String) map.get("code");
			String typex=(String) map.get("type");
			String content = typex+id;
			OutputStream ot = null;
			try {
				content=Des.Encrypt(content);
				ot = new BufferedOutputStream(response.getOutputStream());
				QRCodeUtil.encode(content, ot);
			} catch (Exception e) {
				//e.printStackTrace();
			} finally {
				try {ot.close();} catch (IOException e) {}
			}
		}
	}
	
	@RequestMapping(value = "/viewCode")
	@ResponseBody
	public Object viewCode(String content){
		KJSONMSG msg=qrCodeService.viewCodeFromDevice(content);
		return msg;
	}
	
	/*@AuthWidgetRule(value = "qrcode.exportExcel", desc = "导出Excel", operateType = "W", refTable = "DZ_JZWJBXX", crudType = KConstants.VIEW_QRCODE)
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(String type, String[] idList,HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook=qrCodeService.exportQr(type, Arrays.asList(idList));
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");
		 OutputStream ouputStream = response.getOutputStream();
		try {
			workbook.write(ouputStream);
			ouputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			ouputStream.close();
		}
	}*/
	
	@AuthWidgetRule(value = "qrcode.exportExcel", desc = "导出Excel", operateType = "W", refTable = "DZ_JZWJBXX", crudType = KConstants.VIEW_QRCODE)
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(String type, String[] idList ,String orl,HttpServletResponse response) throws Exception {
		HSSFWorkbook workbook=qrCodeService.exportQr(type, idList,orl);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xls");
		 OutputStream ouputStream = response.getOutputStream();
		try {
			workbook.write(ouputStream);
			ouputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			ouputStream.close();
		}
	}
}
