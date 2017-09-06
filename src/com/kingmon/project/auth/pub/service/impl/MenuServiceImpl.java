package com.kingmon.project.auth.pub.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.kingmon.base.common.KConstants;
import com.kingmon.base.data.ParamObject;
import com.kingmon.base.service.BaseService;
import com.kingmon.base.util.SpringBeanFacUtil;
import com.kingmon.common.authUtil.SecurityUtils;
import com.kingmon.project.auth.pub.service.IMenuService;
import com.kingmon.project.auth.pub.views.MenuGroupView;
import com.kingmon.project.auth.pub.views.MenuItemView;
import com.kingmon.project.auth.pub.views.MenuView;
import com.kingmon.project.auth.resource.model.Resource;
import com.kingmon.project.auth.resource.service.impl.ResourceServiceImpl;
@Service
public class MenuServiceImpl extends BaseService implements IMenuService{

	
	@Override
	public MenuView loadMenu() {
		
		List<String> group_ignorResCodeList=Arrays.asList(new String[]{"device","shpz","jzwfj"});
		List<String> item_ignorResCodeList=Arrays.asList(new String[]{"jzwfj"});
		MenuView view=new MenuView();
		ResourceServiceImpl resourceService=SpringBeanFacUtil.getBean(ResourceServiceImpl.class);
		String userId=SecurityUtils.getUserId();
		List<Resource> resourceList=resourceService.findResListByUserId(userId);
		Resource root = getRoot();
		if(root==null||resourceList==null||resourceList.isEmpty()){
			view.setStatusCode(""+300);
			view.setMessage("未加载到任何菜单数据");
			view.setData(new ArrayList<MenuGroupView>(0));
			return view;
		}
		
		List<Resource> resGroupList=getLevelByPResource(resourceList,root);
		
		if(resGroupList!=null&&resGroupList.size()>0){
			
			List<MenuGroupView> menuGroupList=new ArrayList<MenuGroupView>();
			
			for(Resource res: resGroupList){
				if(KConstants.PSAM_RESOURCE_PUBLICCODE.equals(res.getRes_code())){
					continue;
				}
				if(group_ignorResCodeList.contains(res.getRes_code())){
					continue;
				}
				MenuGroupView groupView=new MenuGroupView();
				groupView.setIcon(res.getImagesmall_value());
				groupView.setMenuid(res.getRes_id());
				groupView.setMenuname(res.getRes_name());
				
				List<Resource> resItemList=getLevelByPResource(resourceList,res);
				
				if(resItemList!=null&&resItemList.size()>0){
					
					List<MenuItemView> menus=new ArrayList<MenuItemView>();
					
					for(Resource resItem: resItemList){
						if(item_ignorResCodeList.contains(resItem.getRes_code())){
							continue;
						}
						MenuItemView itemView=new MenuItemView();
						itemView.setIcon(resItem.getImagesmall_value());
						itemView.setMenuid(resItem.getRes_id());
						itemView.setMenuname(resItem.getRes_name());
						itemView.setUrl(resItem.getRes_pvalue());
						menus.add(itemView);
					}
					groupView.setMenus(menus);
				}
				
				menuGroupList.add(groupView);
			}
			view.setData(menuGroupList);
		}
		view.setStatusCode(""+200);
		view.setMessage("");
		return view;
	}
	
	
	//@Cacheable(value="authTmpResultCache",key="MenuServiceImpl_getRoot")
	public Resource getRoot(){
		String sql=" select r.RES_ID from APP_RESOURCE r where r.RES_CODE='"+KConstants.PSAM_RESOURCE_ROOTCODE+"'";
		Object object = jdbcBaseDao.jdbcQueryObject(sql,ParamObject.new_NP_NC(),Resource.class);
		if (object==null) {
			return null;
		}
		return (Resource)object;		
	}
	private List<Resource> getLevelByPResource(List<Resource> resourceList,Resource pResource){
		if(resourceList==null||resourceList.size()==0||pResource==null){
			return null;
		}
		List<Resource> targetList=Lists.newArrayList();
		for(Resource res : resourceList){
			if(pResource.getRes_id().equals(res.getRes_pid())){
				targetList.add(res);
			}
		}
		return targetList;
	}

}
