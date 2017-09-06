package com.kingmon.project.elastic.model;

import java.io.Serializable;

public class SearchPoint implements Serializable{

	private String x;
	private String y;
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	@Override
	public String toString() {
		return "SearchPoint [x=" + x + ", y=" + y + "]";
	}
	
}
