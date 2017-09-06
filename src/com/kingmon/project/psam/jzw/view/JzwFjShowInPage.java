package com.kingmon.project.psam.jzw.view;

import java.io.Serializable;

public class JzwFjShowInPage implements Serializable{
	
    private Integer mode;
    private Integer left;
    private Integer top;
    private Integer width;
    private Integer height;
    
    
	public JzwFjShowInPage(Integer mode, Integer left, Integer top,
			Integer width, Integer height) {
		super();
		this.mode = mode;
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
	public Integer getTop() {
		return top;
	}
	public void setTop(Integer top) {
		this.top = top;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getConstructStr(){
		return ""+left+","+top+","+width+","+height;
	}
}
