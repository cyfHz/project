package com.kingmon.common.authUtil;


import java.util.List;
import org.springframework.util.StringUtils;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.project.auth.organization.model.Organization;
import com.kingmon.project.auth.organization.service.impl.OrganizationServiceImpl;
import com.kingmon.project.auth.rule.service.impl.DataruleServiceImpl;
import com.kingmon.project.psam.jwq.service.impl.JwqyJygxServiceImpl;
import com.kingmon.project.psam.xzqhdw.service.impl.XzqhDwServiceImpl;
/**
 * 数据权限加载辅助类
* @ClassName :DataRuleUtil     
* @Description :   
* @createTime :2015年10月22日  下午1:48:07   
* @author ：zhaohuatai   
* @version :1.0
 */
public class DataRuleUtil {
	
	/**
	 * 根据用户ID 和 DataRoleCode，加载该用户拥有的 数据权限规则值集合
	 * 
	 * @param dataRuleCode
	 * @param userId
	 * @return
	 */
//	public List<String> loadValuesByRuleCodeAndUserId(String dataRuleCode,String userId){
//		DataruleServiceImpl dataruleService=SpringBeanFacUtil.getBean(DataruleServiceImpl.class);
//		List<String> list=dataruleService.loadValuesByRuleCodeAndUserId(dataRuleCode,userId);
//		return list;
//	}
//-------------------------------------------------------------------------------------------------
	/**
	 * 首先从警务区警员关系 读取，如果userid 存在，则是警务区，
	 * 如果不存在，则读取userlevel，获取用户级别
	 * @param userId
	 * @return  12：警务区 ； 9：派出所[20160218改为：10]-->10  ；   6:分局  ；  4:市局  ；  2: 省厅
	 */
	public static int getUserLevel(String userId){
//		int level=SecurityUtils.getUserLevel();
//		if(level>0){
//			return level;
//		}
		JwqyJygxServiceImpl jwqyJygxService =SpringBeanFacUtil.getBean(JwqyJygxServiceImpl.class);
		boolean isUserAssignedToJwq=jwqyJygxService.isUserAssignedToJwq(userId);
		if(isUserAssignedToJwq){
			return KConstants.USER_LEVEL_JWQ; 
		}
//		List<String> jwqidlist=jwqyJygxService.findJwqIdByAppuserId(userId, "1");
//		if(jwqidlist!=null&&!jwqidlist.isEmpty()){
//			return KConstants.USER_LEVEL_JWQ;
//		}
		int subLenInUserLevel=loadSubLenFromUserLevel(userId);
		return subLenInUserLevel;
	}
	/**
	 * 较为常用，此处按照新网格 提供逻辑已经处理
	 * @param userId
	 * @return
	 */
	public static String getXzqhStr(String userId){
		 OrganizationServiceImpl organizationService=SpringBeanFacUtil.getBean(OrganizationServiceImpl.class);
		 XzqhDwServiceImpl xzqhDwService=SpringBeanFacUtil.getBean(XzqhDwServiceImpl.class);
		 
		 //step1.获取用户级别中要截取的字符长度--app_datarule-用户级别
		 int subLenInUserLevel=loadSubLenFromUserLevel(userId);
		 
		 Organization organization=organizationService.findOrgByUserId(userId);
		 if(organization==null||organization.getOrgna_code()==null){
			return null;
		 }
		 String orgCode=organization.getOrgna_code();
		 //String orgId=organization.getOrgna_id();
		 
		 //step2. 要截取的长度是否和组织机构 代码长度， 比较大小
		 subLenInUserLevel=(subLenInUserLevel>(orgCode.length()))?orgCode.length():subLenInUserLevel;
		
		 if(subLenInUserLevel<0){//如果step1.没有 查询到数据，或者出现异常
//			 return null;
			 subLenInUserLevel=6;
		 }
		//step3. 要截取的长度和长度6 进行比较，获取更短的值
		 int realSubLen=(subLenInUserLevel<6)?subLenInUserLevel:6;
		 
		 //step4. 从用户所在orgCode 中截取step3. 获取的realSubLen长度值。
		 String xzqhStr=orgCode.substring(0,realSubLen);
		 
		 //step5. 从xzqhDw查询是否有对应关系,如有则替换step4.中的值
		  String fromXzqhDzDw =  xzqhDwService.selectXzqhdmByDwid(xzqhStr);//获取该用户的所在组织机构对应的映射编码
		  if(StringUtils.hasText(StringUtils.trimWhitespace(fromXzqhDzDw))){
			  xzqhStr = fromXzqhDzDw; 
		  }
		  return xzqhStr;
	}
	

	private static int loadSubLenFromUserLevel(String appuser_id){
		if(!StringUtils.hasText(appuser_id)){
			return -1;
		}
		DataruleServiceImpl dataruleService=SpringBeanFacUtil.getBean(DataruleServiceImpl.class);
		List<String> list=dataruleService.loadValuesByRuleCodeAndUserId("YHJB",appuser_id);
		if(list==null||list.size()==0){
			return -1;
		}
		List<Integer> covertedList=Tools.convertToInt(list);
		int subLen=Tools.getMin(covertedList);
		return subLen;
	}
}
