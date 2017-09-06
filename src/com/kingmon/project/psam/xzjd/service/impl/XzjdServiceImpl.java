package com.kingmon.project.psam.xzjd.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PinyinUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.common.authUtil.DataRuleUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.common.upload.ExcelImportUtil;
import com.kingmon.project.psam.util.QydmData;
import com.kingmon.project.psam.xzjd.mapper.XzjdMapper;
import com.kingmon.project.psam.xzjd.model.Xzjd;
import com.kingmon.project.psam.xzjd.service.IXzjdService;
import com.kingmon.project.psam.xzqh.dao.XzqhMapper;
import com.kingmon.project.psam.xzqh.model.Xzqh;
import com.kingmon.project.util.DataRangeUtil;
import com.kingmon.project.util.Pinyin4jUtil;

@Service
public class XzjdServiceImpl extends BaseService implements IXzjdService {

	@Autowired
	private XzjdMapper xzjdMapper;
	@Autowired
	private XzqhMapper xzqhMapper;

	private void validateRecord(Xzjd record) {
		KAssert.hasText(record.getXzjddm(), "乡镇（街道）代码不能为空");
		KAssert.hasText(record.getDzyslxdm(), "地址元素类型不能为空");
		KAssert.hasText(record.getXzjddm(), "乡镇（街道）代码不能为空");
		KAssert.hasText(record.getXzjdmc(), "乡镇（街道）名称不能为空");
		KAssert.hasText(record.getBmjc(), "别名简称不能为空");
		KAssert.hasText(record.getSyztdm(), "使用状态不能为空");
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addXzjd(Xzjd record) {
		// 校验数据
		//validateRecord(record);
		KAssert.hasText(record.getDzyslxdm(), "地址元素类型不能为空");
		//KAssert.hasText(record.getXzjddm(), "乡镇（街道）代码不能为空");
		KAssert.hasText(record.getXzjdmc(), "乡镇（街道）名称不能为空");
		KAssert.hasText(record.getBmjc(), "别名简称不能为空");
		KAssert.hasText(record.getSyztdm(), "使用状态不能为空");
		KAssert.hasText(record.getSjxzqy_dzbm(), "所属上级行政区划不能为空");
		
		//生成代码
		Xzqh xzqh=xzqhMapper.selectXzqhByDzbm(record.getSjxzqy_dzbm());
		KAssert.notNull(xzqh, "未查询到上级行政区划");
		String xzqhdm=xzqh.getXzqhdm();
		String xzjdbh="";
		List<String> listMap =xzjdMapper.selectSzjdbhListBySjxzqyDzbm(record.getSjxzqy_dzbm());
		if(listMap==null||listMap.size()==0){
		
			xzjdbh=xzqhdm+"001";
		}else{
			xzjdbh=getRbm(xzqhdm,listMap);
		}
		record.setXzjddm(xzjdbh);
		//
		//判断数据库中是否存在数据：  主键 <>  code =
//		long count = xzjdMapper.queryCount(KConstants.GENERATED_UUID,record.getXzjddm());
//		if (count > 0) {
//			AlertSLEUtil.Error("该乡镇街道代码已存在");
//		} 
		
		String sql = "select count(1) from DZ_XZJD x where x.XZJDMC=:mc and x.SJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
					.addSQLParam("sjdm", record.getSjxzqy_dzbm())
					.addSQLParam("mc", record.getXzjdmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该乡镇街道名称已存在");
		}
		/**
		 * 地址编码、登记人、登记单位、登记时间、撤销标记、助记符
		 */
		record.setDzbm(UUIDUtil.uuid());
		record.setDjr(SecurityUtils.getSessionUser().getUserId());
		record.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());
		record.setDjsj(new Date());
		record.setCxbj(KConstants.CXBJ_0);
		record.setZjf(PinyinUtil.toSzm(record.getBmjc()));//Pinyin4jUtil.convertToSpell(record.getBmjc())
		if (KConstants.SYTZDM_STOPUSE.equals(record.getSyztdm())) {
			record.setTyrq(new Date());
		}
		xzjdMapper.insertSelective(record);
	}

	private String getRbm(String xzqhdm,List<String> listMap) {
		for(int x=0;x<10;x++){
			for(int y=0;y<10;y++){
				for( int z=1;z<10;z++){
					String str=xzqhdm+x+y+z;
					if(!listMap.contains(str)){
						return str;
					}
				}
			}
		}
		return null;
	}
	
	
	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public Xzjd getXzjdById(String DZBM) {
		Xzjd xzjd = xzjdMapper.selectByPrimaryKeyL(DZBM);
		return xzjd;
	}

	@SuppressWarnings("rawtypes")
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateXzjd(Xzjd record) {
		// 校验数据
		validateRecord(record);
		Map map = xzjdMapper.selectDetailByPrimaryKey(record.getDzbm());
		//启用日期不能小于设立日期，停用日期不能小于启用日期
		Date slrq = record.getSlrq();
		Date qyrq = (Date) map.get("QYRQ");
		Date tyrq = (Date) map.get("TYRQ");
		if (qyrq!=null&&slrq!=null) {
			if(slrq.after(qyrq)){
				AlertSLEUtil.Error("设立日期不能大于启用日期");
			}
		}
		if (tyrq!=null&&slrq!=null) {
			if(slrq.after(tyrq)){
				AlertSLEUtil.Error("设立日期不能大于停用日期");
			}
		}
		long count = xzjdMapper.queryCount(record.getDzbm(), record.getXzjddm());
		if (count > 0) {
			AlertSLEUtil.Error("该乡镇街道代码或名称或别名简称已存在");
		}
		String sql = "select count(1) from DZ_XZJD x where x.DZBM!=:dzbm and x.XZJDMC=:mc and x.SJXZQY_DZBM=:sjdm";
		long count2 = jdbcBaseDao.jdbcQueryCount(sql, ParamObject.new_NP_NC()
					.addSQLParam("dzbm", record.getDzbm())
					.addSQLParam("sjdm", record.getSjxzqy_dzbm())
					.addSQLParam("mc", record.getXzjdmc()));
		if (count2 > 0) {
			AlertSLEUtil.Error("该乡镇街道名称已存在");
		}
		/**
		 * 修改人、修改单位、更新时间
		 */
		Date date = new Date();
		record.setXgr(SecurityUtils.getSessionUser().getUserId());
		record.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		record.setGxsj(date);
		record.setZjf(PinyinUtil.toSzm(record.getBmjc()));
		// 如果使用状态为停用，则设置停用日期
		if (KConstants.SYTZDM_STOPUSE.equals(record.getSyztdm())) {
			record.setTyrq(date);
		}
		xzjdMapper.updateByPrimaryKeySelective(record);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void revokeXzjd(String dzbm, String cxyy) {

		Xzjd xzjd = getXzjdById(dzbm);
		if (xzjd == null) {
			AlertSLEUtil.Error("未查询到相关数据");
		}
		/**
		 * 撤销标记、使用状态、撤销人、撤销单位、撤销原因、撤销时间、修改人、修改单位、更新时间
		 */
		// 设置使用状态代码 停用：20
		xzjd.setSyztdm(KConstants.SYTZDM_STOPUSE);
		xzjd.setCxbj(KConstants.CXBJ_1);
		xzjd.setCxr(SecurityUtils.getSessionUser().getUserId());
		xzjd.setCxdw(SecurityUtils.getSessionUser().getOrganizationId());
		xzjd.setCxyy(cxyy);
		xzjd.setCxsj(new Date());
		xzjd.setXgr(SecurityUtils.getSessionUser().getUserId());
		xzjd.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		xzjd.setGxsj(new Date());
		xzjd.setTyrq(new Date());//使用状态为 停用，设置停用日期20160712
		xzjdMapper.revokeXzjd(xzjd);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void activateXzjd(String dzbm) {
		/**
		 * 撤销标记、使用状态、启用时间、修改人、修改单位、更新时间
		 */
		Xzjd xzjd = getXzjdById(dzbm);
		if (xzjd == null) {
			AlertSLEUtil.Error("未查询到相关数据");
		}
		xzjd.setCxbj(KConstants.CXBJ_0);
		xzjd.setSyztdm(KConstants.SYTZDM_INUSE);
		xzjd.setQyrq(new Date());
		xzjd.setXgr(SecurityUtils.getSessionUser().getUserId());
		xzjd.setXgdw(SecurityUtils.getSessionUser().getOrganizationId());
		xzjd.setGxsj(new Date());
		xzjdMapper.activateXzjd(xzjd);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@Override
	public DataSet loadXzjdDataSet(ParamObject po) {
		StringBuilder sql = new StringBuilder("")
		.append(" SELECT ")
		.append(" xz.XZQHMC as SJXZQY_DZBM,")
		.append(" x.DZYSLXDM, ")
				.append(" x.SYZTDM, ")
				.append(" x.DZBM, ")
				.append(" x.XZJDDM, ")
				.append(" x.XZJDMC, ")
				.append(" x.BMJC, ")
				.append(" x.SLRQ, ")
				.append(" x.CXSJ, ")
				.append(" x.GXSJ, ")
				.append(" x.QYRQ, ")
				.append(" x.TYRQ, ")
				.append(" x.CXYY, ")
				.append(" x.ZJF, ")
				.append(" x.DJSJ, ")
				.append(" x.CXBJ ");
		sql.append(" @from DZ_XZJD x ");
		sql.append(" left join DZ_XZQH xz ");
		sql.append(" on xz.DZBM=x.SJXZQY_DZBM ");
		sql.append(" where 1=1 ");
		//使用状态代码为停用或报废是不显示
/*		sql.append(" and x.SYZTDM !='").append(KConstants.SYTZDM_STOP).append("'");
		sql.append(" and x.SYZTDM !='").append(KConstants.SYTZDM_STOPUSE).append("'");;
*/
		String xzjddm = (String) po.getWebParam("xzjddm");
		if (!SubApStrUtil.isEmptyAfterTrimE(xzjddm)) {
			sql.append(" and x.XZJDDM like:xzjddm ");
			po.addSQLParam("xzjddm", "%" + xzjddm + "%");
		}
		String xzjdmc = (String) po.getWebParam("xzjdmc");
		if (!SubApStrUtil.isEmptyAfterTrimE(xzjdmc)) {
			sql.append(" and (x.XZJDMC like :xzjdmc or lower(x.zjf) like lower(:xzjdmc))");
			po.addSQLParam("xzjdmc", "%" + xzjdmc + "%");
		}
		String syztdm = (String) po.getWebParam("syztdm");
		if (!SubApStrUtil.isEmptyAfterTrimE(syztdm)) {
			sql.append(" and x.SYZTDM=:syztdm ");
			po.addSQLParam("syztdm", syztdm);
		}
		 String sjxzqy_dzbm = (String) po.getWebParam("sjxzqy_dzbm");
		 if (!SubApStrUtil.isEmptyAfterTrimE(sjxzqy_dzbm)) {
//			 sql.append(" and x.SJXZQY_DZBM=:sjxzqy_dzbm ");
//			 po.addSQLParam("sjxzqy_dzbm", sjxzqy_dzbm);
			 List<Map<String,String>> map=xzqhMapper.selectXzqyMapByDzbm(sjxzqy_dzbm);
			 String qydm=QydmData.getQydm(map);
			 qydm+="%";
			 sql.append(" and xz.xzqhdm like :qydm ");
			 po.addSQLParam("qydm", qydm);
		 }
		 
		//String xzqh=DataRuleUtil.getXzqhStr(SecurityUtils.getUserId());
		String xzqh=SecurityUtils.getUserLevelStr();//-zht-20160219
		if(xzqh.length()>6){ xzqh = xzqh.substring(0,6); }
		
		sql.append(" and x.XZJDDM like:xzjddm_data_auth ");
		po.addSQLParam("xzjddm_data_auth", ""+xzqh+"%");
		

		if (po.hasOrder()) {
			sql.append(" order by ").append("x.").append(po.getSort()).append(" ").append(po.getOrder()).append(" ");
		}else{
			sql.append(" order by x.XZJDDM");
		}
		return getJdbcBaseDao().jdbcLoadDataSet(sql.toString(), po);
	}

	@Transactional(rollbackFor = Exception.class, readOnly = true)
	@SuppressWarnings("rawtypes")
	@Override
	public Map selectDetailByPrimaryKey(String dzbm) {
		if (dzbm == null) {
			return null;
		}
		return xzjdMapper.selectDetailByPrimaryKey(dzbm);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String importFromExcel(Workbook workbook, String type) {
		String message = "";
		Sheet sheet = null;
		Row row = null;
		ArrayList<Xzjd> xzjdList = new ArrayList<Xzjd>();
		// 遍历所有sheet工作表
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheet = workbook.getSheetAt(i);
			// 遍历所有行,从索引为1的行开始读取
			for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
				row = sheet.getRow(j);
				Xzjd xzjd = new Xzjd();
				xzjd = row2Xzjd(row);
				xzjd.setDzbm(UUIDUtil.uuid());
				// xzjd.setDjr(SecurityUtils.getSessionUser().getUserId());
				// xzjd.setDjdw(SecurityUtils.getSessionUser().getOrganizationId());
				xzjd.setSyztdm(KConstants.SYTZDM_INUSE);
				xzjd.setSjxzqy_dzbm("cbaf76ea-9706-4871-96d1-50bdcabaae99");
				xzjd.setDjsj(new Date());
				xzjd.setCxbj(KConstants.CXBJ_0);
				xzjd.setZjf(Pinyin4jUtil.convertToSpell(xzjd.getBmjc()));
				message += validateXzjd(xzjd);
				xzjdList.add(xzjd);
			}
		}
		// 添加到数据库
		if ("".equals(message)) {
			xzjdMapper.batchInsertXzjd(xzjdList);
		}
		return message;
	}

	private Xzjd row2Xzjd(Row row) {
		Xzjd xzjd = new Xzjd();
		try {
			xzjd.setXzjddm(getCellValue(row.getCell(0)));
			xzjd.setXzjdmc(getCellValue(row.getCell(1)));
			xzjd.setDzyslxdm(getCellValue(row.getCell(2)));
			xzjd.setBmjc(getCellValue(row.getCell(3)));
			xzjd.setSlrq(ExcelImportUtil.dateformat1.parse(getCellValue(row.getCell(4))));
			xzjd.setQyrq(ExcelImportUtil.dateformat1.parse(getCellValue(row.getCell(5))));
			xzjd.setTyrq(ExcelImportUtil.dateformat1.parse(getCellValue(row.getCell(6))));

		} catch (ParseException e) {
			AlertSLEUtil.Error("row2Xzjd 数据有误，请检查数据");
		}
		return xzjd;
	}

	private String validateXzjd(Xzjd xzjd) {
		String message = "";
		if ("".equals(xzjd.getXzjddm())) {
			message += "乡镇街道代码不能为空！";
		}
		// ...

		return message;
	}

	// 判断从Excel文件中解析出来数据的格式
	private String getCellValue(Cell cell) {
		String value = "";
		// 简单的查检列类型
		try {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_STRING:// 字符串
				value += cell.getRichStringCellValue().getString();
				break;
			case HSSFCell.CELL_TYPE_NUMERIC:// 数字
				long dd = (long) cell.getNumericCellValue();
				value = dd + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				value = "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA:
				value += String.valueOf(cell.getCellFormula());
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:// boolean型值
				value += String.valueOf(cell.getBooleanCellValue());
				break;
			case HSSFCell.CELL_TYPE_ERROR:
				value += String.valueOf(cell.getErrorCellValue());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			value = "";
		}
		return value;
	}

}
