package com.kingmon.project.psam.sy.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.KAssert;
import com.kingmon.project.psam.sy.mapper.SyRkglCzrkPicMapper;
import com.kingmon.project.psam.sy.model.SyRkglCzrkPic;
import com.kingmon.project.psam.sy.service.ISyRkglCzrkPicService;

import oracle.sql.BLOB;
/**
 * 
* @ClassName :SyFwJwryServiceImpl     
* @Description :   
* @createTime :2015年12月20日  上午9:38:14   
* @author ：zhaohuatai   
* @version :1.0
 */
@Service
public class SyRkglCzrkPicServiceImpl extends BaseService implements ISyRkglCzrkPicService {
	@Autowired
	private SyRkglCzrkPicMapper syRkglCzrkPicMapper;

	
    /**
     * 根据证件号码查询照片信息
     * @param gmsfhm
     * @return
     */
	public SyRkglCzrkPic selectpicInfoByZjhm(String gmsfhm) {
		// TODO Auto-generated method stub
		if(gmsfhm==null||"".equals(gmsfhm)){
			return null;
		}
		return syRkglCzrkPicMapper.selectPicInfoByZjhm(gmsfhm);
	}
	@Override
	public byte[] loadRkglPic(String zjhm) {
		List<Map> mapList=syRkglCzrkPicMapper.selectRkglPicByzjhm(zjhm);
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
	public void addPic(SyRkglCzrkPic syRkglCzrkPic) {
		// TODO Auto-generated method stub
		KAssert.hasText(syRkglCzrkPic.getRkzjbh(), "证件编号不能为空");
		KAssert.hasText(syRkglCzrkPic.getPic(), "照片信息不能为空");
//		KAssert.hasText(syRkglPic.getRkid(), "人口信息不能为空");
		syRkglCzrkPicMapper.insertSelective(syRkglCzrkPic);
	}

}



