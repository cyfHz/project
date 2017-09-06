package com.kingmon.project.psam.dataQr.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import com.kingmon.base.data.KJSONMSG;

@Service
public interface IQRCodeService {

	/**
	 * 
	 * @param type
	 * @param idList
	 */
	public HSSFWorkbook exportQr(String type, List<String> idList,long count);
	
	public HSSFWorkbook exportQr(String type, String[] idList,String orl)throws Exception;

	public KJSONMSG viewCodeFromPage(String type, String id);
	public KJSONMSG viewCodeFromDevice(String content);
}
