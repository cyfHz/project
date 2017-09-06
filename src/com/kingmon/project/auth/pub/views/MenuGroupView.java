package com.kingmon.project.auth.pub.views;

import java.io.Serializable;
import java.util.List;

public class MenuGroupView implements Serializable {
	private String menuid;
	private String icon ;
	private String menuname ;
	private List<MenuItemView> menus ;
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public List<MenuItemView> getMenus() {
		return menus;
	}
	public void setMenus(List<MenuItemView> menus) {
		this.menus = menus;
	}
	
}
