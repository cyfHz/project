package com.kingmon.project.psam.jzw.serivice.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.project.psam.jzw.mapper.JzwfjChangeBizConfigMapper;
import com.kingmon.project.psam.jzw.mapper.JzwfjMapper;
import com.kingmon.project.psam.jzw.model.Jzwfj;
import com.kingmon.project.psam.jzw.model.JzwfjChangeBizConfig;
import com.kingmon.project.psam.jzw.serivice.IJzwfjChangeBizConfigService;

@Service
public class JzwfjChangeBizConfigServiceImpl extends BaseService implements IJzwfjChangeBizConfigService{

	@Autowired
	private JzwfjChangeBizConfigMapper jzwfjChangeBizConfigMapper;
	@Autowired
	private JzwfjMapper jzwfjMapper;
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void processJzwfjHeBing(List<String> fromFjIds, String toFjId) {
		List<JzwfjChangeBizConfig>  bizConfigList =	jzwfjChangeBizConfigMapper.selectAll();
		if(bizConfigList==null||bizConfigList.isEmpty()){
			return ;
		}
		for(String fromFjId : fromFjIds){
			
			for(JzwfjChangeBizConfig config:bizConfigList){
				
				String tableName=config.getTableName();
				
				String fieldName=config.getFieldName();
				
				//String field2Name=config.getFieldName();
				String field2Name=config.getField2Name(); 
				String refJzwFjField2Name=config.getRefJzwFjField2Name();
				String ref2replceMode =config.getRef2replceMode();
				
				
				//String field3Name=config.getFieldName();
				String field3Name=config.getField3Name();
				String refJzwFjField3Name=config.getRefJzwFjField3Name();
				String ref3replceMode =config.getRef3replceMode();
			
				
				String updateQueryFieldName=config.getUpdateQueryFieldName();
				StringBuffer strbuffer=new StringBuffer("");
				strbuffer.append(" update ").append(tableName);
				strbuffer.append(" set ");
				strbuffer.append(fieldName).append("='"+toFjId+"'");
				if(StringUtils.isNotEmpty(field2Name)){
					String oldRefJzwFjField2Value=queryFjFieldValue(fromFjId,refJzwFjField2Name);
					String newRefJzwFjField2Value=queryFjFieldValue(toFjId,refJzwFjField2Name);
					strbuffer.append(",");
					if("1".equals(ref2replceMode)){//直接替换
						strbuffer.append(field2Name).append("='"+oldRefJzwFjField2Value+"'");
					}else if("2".equals(ref2replceMode)){//字符替换
						//strbuffer.append(field2Name).append(" replace  ( "+field2Name+" ,"+oldRefJzwFjField2Value+" , "+newRefJzwFjField2Value+")");
						strbuffer.append(field2Name).append(" = replace  ( "+field2Name+",'"+oldRefJzwFjField2Value+"' , '"+newRefJzwFjField2Value+"')");
					}
				}
				if(StringUtils.isNotEmpty(field3Name)){
					String oldRefJzwFjField3Value=queryFjFieldValue(fromFjId,refJzwFjField3Name);
					String newRefJzwFjField3Value=queryFjFieldValue(toFjId,refJzwFjField3Name);
					
					strbuffer.append(",");
					if("1".equals(ref3replceMode)){//直接替换
						strbuffer.append(field3Name).append("='"+oldRefJzwFjField3Value+"'");
					}else if("2".equals(ref3replceMode)){//字符替换
					//	strbuffer.append(field3Name).append(" replace  ( "+field2Name+"  ,"+oldRefJzwFjField3Value+" , "+newRefJzwFjField3Value+")");
					//	strbuffer.append(field3Name).append(" = replace  ( (select a."+field3Name+" from " +tableName+ " a where a."+fieldName+" = '"+fromFjId+"' ),'"+oldRefJzwFjField3Value+"' , '"+newRefJzwFjField3Value+"')");
						strbuffer.append(field3Name).append(" = replace  ( "+field3Name+",'"+oldRefJzwFjField3Value+"' , '"+newRefJzwFjField3Value+"')");
					}
				}
				strbuffer.append(" where ").append(updateQueryFieldName).append(" ='"+fromFjId+"' ");
				jdbcBaseDao.jdbcUpdate(strbuffer.toString(), ParamObject.new_NP_NC());
			}
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void processJzwfjChaiFen(String fromFjId, List<String> toFjIds,String targetFjId) {

		List<JzwfjChangeBizConfig>  bizConfigList =	jzwfjChangeBizConfigMapper.selectAll();
		if(bizConfigList==null||bizConfigList.isEmpty()){
			return ;
		}
			
		for(JzwfjChangeBizConfig config:bizConfigList){
				
				String tableName=config.getTableName();
				
				String fieldName=config.getFieldName();
				//String field2Name=config.getFieldName();
				String field2Name=config.getField2Name();
				String refJzwFjField2Name=config.getRefJzwFjField2Name();
				String ref2replceMode =config.getRef2replceMode();
				
				
				//String field3Name=config.getFieldName();
				String field3Name=config.getField3Name();
				String refJzwFjField3Name=config.getRefJzwFjField3Name();
				String ref3replceMode =config.getRef3replceMode();
			
				
				String updateQueryFieldName=config.getUpdateQueryFieldName();
				StringBuffer strbuffer=new StringBuffer("");
				strbuffer.append(" update ").append(tableName);
				strbuffer.append(" set ");
				strbuffer.append(fieldName).append("='"+targetFjId+"'");
				if(StringUtils.isNotEmpty(field2Name)){
					String oldRefJzwFjField2Value=queryFjFieldValue(fromFjId,refJzwFjField2Name);
					String newRefJzwFjField2Value=queryFjFieldValue(targetFjId,refJzwFjField2Name);
					strbuffer.append(",");
					if("1".equals(ref2replceMode)){//直接替换
						strbuffer.append(field2Name).append("='"+oldRefJzwFjField2Value+"'");
					}else if("2".equals(ref2replceMode)){//字符替换
						strbuffer.append(field2Name).append(" = replace  ( "+field2Name+",'"+oldRefJzwFjField2Value+"' , '"+newRefJzwFjField2Value+"')");
//						strbuffer.append(field2Name).append(" replace  ( "+field2Name+"  ,"+oldRefJzwFjField2Value+" , "+newRefJzwFjField2Value+")");
					}
				}
				if(StringUtils.isNotEmpty(field3Name)){
					String oldRefJzwFjField3Value=queryFjFieldValue(fromFjId,refJzwFjField3Name);
					String newRefJzwFjField3Value=queryFjFieldValue(targetFjId,refJzwFjField3Name);
					
					strbuffer.append(",");
					if("1".equals(ref3replceMode)){//直接替换
						strbuffer.append(field3Name).append("='"+oldRefJzwFjField3Value+"'");
					}else if("2".equals(ref3replceMode)){//字符替换
//						strbuffer.append(field3Name).append(" replace  ( "+field2Name+"  ,'"+oldRefJzwFjField3Value+"' , '"+newRefJzwFjField3Value+"')");
						
						strbuffer.append(field3Name).append(" = replace  ("+field2Name+",'"+oldRefJzwFjField3Value+"' , '"+newRefJzwFjField3Value+"')");
					}
				}
				strbuffer.append(" where ").append(updateQueryFieldName).append(" ='"+fromFjId+"' ");
				jdbcBaseDao.jdbcUpdate(strbuffer.toString(), ParamObject.new_NP_NC());
			}
	
	}
//queryFjFieldValue(fromFjId,refJzwFjField2Name);
	private String queryFjFieldValue(String jzwfjId,String fieldName){
		String sql=" select f."+fieldName+" from dz_jzwfj f where f.JZWFJID=:jzwfjId";
		String value=jdbcBaseDao.queryUniqueOneColumn(sql, ParamObject.new_NP_C().addSQLParam("jzwfjId", jzwfjId));
		return value;
	}
	
}
