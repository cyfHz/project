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
import com.kingmon.base.util.UUIDUtil;
import com.kingmon.project.psam.jzw.mapper.JzwfjPicMapper;
import com.kingmon.project.psam.jzw.model.JzwfjPic;
import com.kingmon.project.psam.jzw.serivice.IJzwfjPicService;

@Service
public class JzwfjPicServiceImpl extends BaseService implements IJzwfjPicService{

	@Autowired
	JzwfjPicMapper jzwfjPicMapper;
	
	@Transactional(readOnly=true)
	@Override
	public  byte[] loadJzwfjPic(String jzwfjPicId) {
		List<Map> mapList=jzwfjPicMapper.selectFjPicByPicId(jzwfjPicId);
		if(mapList==null||mapList.size()==0){
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
				//e.printStackTrace();
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
	@Transactional(rollbackFor = Exception.class,readOnly=true)
	@Override
	public List<String> loadFjPicIdsByfjId(String jzwfjid) {
		if(!StringUtils.hasText(jzwfjid)){
			return null;
		}
		return jzwfjPicMapper.selectFjPicIdsByfjId(jzwfjid);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public String addJzwFjPic(JzwfjPic jzwfjPic,String jzwfjid) {
		if(!StringUtils.hasText(jzwfjid)){
			AlertSLEUtil.Error("所属建筑物房间不能为空");
		}
		String picid=UUIDUtil.uuid();
		jzwfjPic.setJzwfjid(jzwfjid);
		jzwfjPic.setPicid(picid);
		
		jzwfjPic.setUpdatedTime(new Date());
		jzwfjPic.setCreatTime(new Date());
		
		jzwfjPicMapper.insertSelective(jzwfjPic);
		return picid;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void deletefwpic(String fjpicid) {
		if(StringUtils.hasText(fjpicid)){
			jzwfjPicMapper.deleteByPrimaryKey(fjpicid);
		}
	}
	

	
}
