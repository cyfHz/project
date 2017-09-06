package com.kingmon.project.psam.dataQr.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.elasticsearch.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.KJSONMSG;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.psam.dataQr.service.IQRCodeService;
import com.kingmon.project.psam.dataQr.util.QRCodeUtil;
import com.kingmon.project.psam.dataQr.util.QrConfigModel;
import com.kingmon.project.psam.dataQr.util.QrConfigUtil;
import com.kingmon.project.psam.jzw.serivice.IJzwjbxxService;
import com.kingmon.project.psam.mlph.service.MlphService;
import com.kingmon.project.psam.mlph.service.impl.MlphServiceImpl;
import com.sdwangge.Des;

@Service
public class QRCodeServiceImpl extends BaseService implements IQRCodeService {

	@Override
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public KJSONMSG viewCodeFromPage(String type, String id){
		QrConfigModel qrConfig= QrConfigUtil.getConfig(type);
		if(qrConfig==null){
			return new KJSONMSG(300, "查询类型错误或者不支持当前类型,请联系管理员!");
		}
		String sql="select "+qrConfig.getQueryPrimaryField()+" as code,"+qrConfig.getQueryShowField()+" as name  from "+qrConfig.getQueryTable()+" t where t."+qrConfig.getQueryPrimaryField()+"=:id ";
        List<Map<String, Object>>  listMap = (List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sql,ParamObject.new_NP_NC().addSQLParam("id", id));
		if(listMap.isEmpty()){
			return new KJSONMSG(300, "未查询到该数据");
		}
		Map<String, Object> data=listMap.get(0);
			Map<String, Object> map = Maps.newHashMap();
			map.put("code", data.get("code"));
			map.put("type", type);
			map.put("typeName", qrConfig.getShowTitle());
			map.put("name", data.get("name"));
		return  new KJSONMSG(200,map);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public KJSONMSG viewCodeFromDevice(String content) {
		String decodeContent;
		try {
			decodeContent = Des.Decrypt(content);
		} catch (Exception e) {
			return new KJSONMSG(300, "查询类型错误或者不支持当前类型,请联系管理员!");
		}
		String type=decodeContent.substring(0,4);
		String id=decodeContent.substring(4,decodeContent.length());		
		QrConfigModel qrConfig= QrConfigUtil.getConfig(type);
		if(qrConfig==null){
			return new KJSONMSG(300, "查询类型错误或者不支持当前类型,请联系管理员!");
		}
		String sql="select "+qrConfig.getQueryPrimaryField()+" as code,"+qrConfig.getQueryShowField()+" as name  from "+qrConfig.getQueryTable()+" t where t."+qrConfig.getQueryPrimaryField()+"=:id ";
        List<Map<String, Object>>  listMap = (List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sql,ParamObject.new_NP_NC().addSQLParam("id", id));
		if(listMap.isEmpty()){
			return new KJSONMSG(300, "未查询到该数据");
		}
		List<Map<String, Object>>  listJzw=Lists.newArrayList();
		if(type.equals("_jzw")){
			String sqlJzw="select t.jzwmc,t.zxdhzb,t.zxdzzb,t.zaglssjwzrqmc from dz_jzwjbxx t where t."+qrConfig.getQueryPrimaryField()+"=:id ";
			listJzw=jdbcBaseDao.jdbcQueryMapList(sqlJzw,ParamObject.new_NP_NC().addSQLParam("id", id));
		}
		Map<String, Object> data=listMap.get(0);
			Map<String, Object> map = Maps.newHashMap();
			map.put("code", data.get("code"));
			map.put("type", type);
			map.put("typeName", qrConfig.getShowTitle());
			map.put("name", data.get("name"));
		if(listJzw!=null&&listJzw.size()>0){   //建筑物扫描二维码 得到横纵坐标，警务区名称
			Map<String, Object> jzw=listJzw.get(0);
			map.put("hzb",jzw.get("zxdhzb")+"" );
			map.put("zzb",jzw.get("zxdzzb")+"" );
			map.put("jwqmc", jzw.get("zaglssjwzrqmc"));
			map.put("jzwmc", jzw.get("jzwmc"));
		}
		return  new KJSONMSG(200,map);
	}
	
	@SuppressWarnings("all")
	public HSSFWorkbook exportQr(String type, String[] idList,String orl)throws Exception{
//		List a=Arrays.asList(idList);
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
		long num = 0;
		long count = 0;
		switch (orl) {
		case "01":
			workbook=exportQr(type, Arrays.asList(idList),count);
			break;
		case "02":
			workbook=exportQr(type, Arrays.asList(idList),count);
			break;
		case "03":
			Map<String, String> params=new HashMap<String,String>();
			List<String> as=new ArrayList<String>();
			switch (type) {
			case "mlph":
				params.put("SJGSDWMC", idList[0]);
				params.put("DZMC", idList[1]);
				params.put("MLPH", idList[2]);
				MlphService mlphService=SpringBeanFacUtil.getBean(MlphServiceImpl.class);
				DataSet mlph=mlphService.mlphList(params);
				count=mlph.getTotal();
				if(count>1000){  //导出最大数为1000
					num=1000;
//					getTotal();
				}
				DataSet mlphList=mlphService.mlphQrList(params,num);
				for(int i=0;i<mlphList.getRows().size();i++){
					as.add(i, (String) mlphList.getRows().get(i).get("YWLSH"));
				}
				workbook=exportQr(type, as,count);
				break;
			case "_jzw":
				params.put("JZWMC", idList[0]);
				params.put("DZMC", idList[1]);
				IJzwjbxxService iJzwjbxxService=SpringBeanFacUtil.getBean(IJzwjbxxService.class);
				DataSet jzwjbxx=iJzwjbxxService.loadJzwjbxxDataSet(params);//符合条件的条数
				count=jzwjbxx.getTotal();
				if(count>1000){
					num=1000;
//					getTotal();
				}
				DataSet jzwjbxxList=iJzwjbxxService.loadJzwQrDataSet(params,num);
				for(int i=0;i<jzwjbxxList.getRows().size();i++){
					as.add(i, (String) jzwjbxxList.getRows().get(i).get("DZBM"));
				}
				workbook=exportQr(type, as,count);
				break;
			}
		}
		return workbook;
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class,readOnly=true)
	public HSSFWorkbook exportQr(String type,List<String> idList,long count) {
		QrConfigModel qrConfig= QrConfigUtil.getConfig(type);
		if(qrConfig==null||type==null||type.isEmpty()){
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet();// 创建工作表
			return workbook;
		}
		if(idList==null||idList.size()<=0){
			HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
			HSSFSheet sheet = workbook.createSheet();// 创建工作表
			return workbook;
		}
		// 查数据库
		// 调用生成接口
		// 构造HSSFWorkbook
		// 返回HSSFWorkbook
		List<Map<String, Object>> listMap = Lists.newArrayList();
		//String sqlX ="";
		String sqlX="select "+qrConfig.getQueryPrimaryField()+" as id,"+qrConfig.getQueryShowField()+" as mc  from "+qrConfig.getQueryTable()+" t where t."+qrConfig.getQueryPrimaryField()+" in(:idList) ";
		listMap = (List<Map<String, Object>>) jdbcBaseDao.jdbcQueryMapList(sqlX,ParamObject.new_NP_NC().addSQLParam("idList", idList));
		for (Map<String, Object> map : listMap) {
			BufferedImage imge;
			try {
				String content=type+(""+map.get("id")).toString();
				content=Des.Encrypt(content);
				imge = QRCodeUtil.createImage(content);
				map.put("orImge", imge);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			} 
		}

		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
		HSSFSheet sheet = workbook.createSheet();// 创建工作表
		sheet.setColumnWidth(1, 256*40);
		sheet.setColumnWidth(2, 256*20);
		HSSFRow rowm = sheet.createRow(0);// 产生表格标题行//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFCellStyle style = workbook.createCellStyle(); // 创建单元格，并设置值表头 设置表头居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cellTiltle = rowm.createCell(0, HSSFCell.CELL_TYPE_STRING);// 创建列头对应个数的单元格
		   	
		cellTiltle.setCellValue("编号");
		// cellTiltle.setCellType(HSSFCell.CELL_TYPE_STRING);//设置样式
		cellTiltle = rowm.createCell(1, HSSFCell.CELL_TYPE_STRING);
		cellTiltle.setCellValue("地址名称");
		cellTiltle.setCellStyle(style);
		cellTiltle = rowm.createCell(2);
		cellTiltle.setCellValue("二维码");
		cellTiltle.setCellStyle(style);
		if(count>1000){
			 HSSFFont font = workbook.createFont();
			 HSSFCellStyle style1 = workbook.createCellStyle();
			 font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			 font.setColor(HSSFColor.RED.index);
			 font.setFontHeightInPoints((short)12);  

			 style1.setFont(font);  
			 sheet.addMergedRegion(new CellRangeAddress((short)0, (short)0, (short)3, (short)8));
			cellTiltle = rowm.createCell(3);
			cellTiltle.setCellStyle(style1);
			cellTiltle.setCellValue("当前导出数量大于1000条，请进行精确查询！");
		}
		for (int i = 1; i <= listMap.size(); i++) {
			Map<?,?> mapName=listMap.get(i-1);
			rowm = sheet.createRow(i);// 创建行
			rowm.setHeightInPoints(80);
			HSSFCell id_cell=rowm.createCell(0);  id_cell.setCellValue(i);
			HSSFCell mc_cell=rowm.createCell(1);  mc_cell.setCellValue((""+mapName.get("mc")).toString());
			BufferedImage imagex=(BufferedImage)mapName.get("orImge");
			if(imagex!=null){
				ByteArrayOutputStream bout=new ByteArrayOutputStream();
				try {
					ImageIO.write(imagex, "png", bout);
					HSSFPatriarch patri = sheet.createDrawingPatriarch();
					int dx1=0,dy1=0,dx2=0,dy2=0;
					int row1=i,row2=i+1;
					short col1=(short) (2),col2=(short) (3);
					HSSFClientAnchor anchor = new HSSFClientAnchor(dx1, dy1, dx2, dy2, col1, row1, col2, row2);
					patri.createPicture(anchor, workbook.addPicture(bout.toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}finally {
					try {
						bout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return workbook;
	}


	public HSSFWorkbook getTotal(){
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建工作簿对象
		HSSFSheet sheet = workbook.createSheet();// 创建工作表
		HSSFRow rowm = sheet.createRow(0);// 产生表格标题行//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFCellStyle style = workbook.createCellStyle(); // 创建单元格，并设置值表头 设置表头居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cellTiltle = rowm.createCell(0, HSSFCell.CELL_TYPE_STRING);// 创建列头对应个数的单元格
		cellTiltle.setCellValue("当前导出数量大于1000条，请进行精确查询！");
		return workbook;
	}
	public static void main(String[] args) throws Exception{
		String decodeContent="0000x1114x";
		String type=decodeContent.substring(0,4);
//		String id=decodeContent.substring(4,decodeContent.length());	
//		System.out.println(type);
//		System.out.println(id);
		System.out.println("=====================================");
		String content=Des.Encrypt(decodeContent);
		System.out.println(content);
		System.out.println("=====================================");
		String contenxt=Des.Decrypt(content);
		System.out.println(contenxt);
		
	}
}
