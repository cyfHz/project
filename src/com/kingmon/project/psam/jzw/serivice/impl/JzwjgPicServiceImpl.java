package com.kingmon.project.psam.jzw.serivice.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.AlertSLEUtil;
import com.kingmon.base.util.KAssert;
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.psam.jzw.mapper.JzwjgPicMapper;
import com.kingmon.project.psam.jzw.model.JzwfjPic;
import com.kingmon.project.psam.jzw.model.Jzwjg;
import com.kingmon.project.psam.jzw.model.JzwjgPic;
import com.kingmon.project.psam.jzw.serivice.IJzwjgPicService;


@Service
public class JzwjgPicServiceImpl extends BaseService implements IJzwjgPicService{
    

	@Autowired
	JzwjgPicMapper jzwjgPicMapper;
	
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public List<String> loadjgPicIdsByjgId(String jzwjgid) {
		if(!StringUtils.hasText(jzwjgid)){
			return null;
			
		}
		return jzwjgPicMapper.selectJgpicIdsByjgId(jzwjgid);
	}
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public byte[] loadJzwjgPic(String jzwjgPicId) {
		List<Map> mapList=jzwjgPicMapper.selectJgpicByPicId(jzwjgPicId);
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
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deleteJzwjgPic(String jzwjgPicId) {
		KAssert.hasText(jzwjgPicId, "照片ID为空");
		jzwjgPicMapper.deleteByPrimaryKey(jzwjgPicId);
		
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public String addJzwJgPic(JzwjgPic jzwjgPic,String jzwjgid) {
		if(!StringUtils.hasText(jzwjgid)){
			AlertSLEUtil.Error("所属建筑物结构不能为空");
		}
		String picId=UUIDUtil.uuid();
		jzwjgPic.setPicid(picId);
		jzwjgPic.setJzwjgid(jzwjgid);
		jzwjgPic.setUpdatedTime(new Date());
		jzwjgPic.setCreatTime(new Date());
		
		jzwjgPicMapper.insertSelective(jzwjgPic);
		return picId;
	}
}
