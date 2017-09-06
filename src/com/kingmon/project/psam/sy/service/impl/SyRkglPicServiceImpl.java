package com.kingmon.project.psam.sy.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.data.DataSet;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.PaginationUtil;
import com.kingmon.base.util.SubApStrUtil;
import com.kingmon.project.psam.sy.mapper.SyRkglLdrkdjbMapper;
import com.kingmon.project.psam.sy.mapper.SyRkglPicMapper;
import com.kingmon.project.psam.sy.model.SyRkglCzrk;
import com.kingmon.project.psam.sy.model.SyRkglLdrkdjb;
import com.kingmon.project.psam.sy.model.SyRkglPic;
import com.kingmon.project.psam.sy.service.ISyRkglLdrkdjbService;
import com.kingmon.project.psam.sy.service.ISyRkglPicService;
/**
 * 
* @ClassName :SyFwJwryServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:38:14   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyRkglPicServiceImpl extends BaseService implements ISyRkglPicService {
	@Autowired
	private SyRkglPicMapper syRkglPicMapper;

	

    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyRkglPic selectpicInfoByZjhm(String gmsfhm) {
		// TODO Auto-generated method stub
		if(gmsfhm==null||"".equals(gmsfhm)){
			return null;
		}
		return syRkglPicMapper.selectPicInfoByZjhm(gmsfhm);
	}
	@Override
	public byte[] loadRkglPic(String zjhm) {
		List<Map> mapList=syRkglPicMapper.selectRkglPicByzjhm(zjhm);
		if(mapList==null || mapList.size()==0){
			return null;
		}
		oracle.sql.BLOB blob=(BLOB) mapList.get(0).get("PIC");
		InputStream inStream=null;
		 byte[] data=null;
		 long nLen=0;
			try {
				inStream = blob.getBinaryStream();
				nLen = blob.length();
				int nSize = (int) nLen;
	            data = new byte[nSize];
	            inStream.read(data);//将输入流中的数据读到数组中
			} catch (Exception e) {
			}finally{
				if(inStream!=null){
					try {
						inStream.close();
					} catch (IOException e) {
					}
				}
			}
		return data;
	}
	@Override
	public void addPic(SyRkglPic syRkglPic) {
		// TODO Auto-generated method stub
		KAssert.hasText(syRkglPic.getRkzjbh(), "证件编号不能为空");
		KAssert.hasText(syRkglPic.getPic(), "照片信息不能为空");
//		KAssert.hasText(syRkglPic.getRkid(), "人口信息不能为空");
		syRkglPicMapper.insertSelective(syRkglPic);
	}

}



