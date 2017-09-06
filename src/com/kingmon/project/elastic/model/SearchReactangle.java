package com.kingmon.project.elastic.model;

import java.io.Serializable;

public class SearchReactangle implements Serializable{
	
	private SearchPoint leftTopPoint;
	private SearchPoint rightBottomPoint;
	
	public SearchPoint getLeftTopPoint() {
		return leftTopPoint;
	}
	public void setLeftTopPoint(SearchPoint leftTopPoint) {
		this.leftTopPoint = leftTopPoint;
	}
	public SearchPoint getRightBottomPoint() {
		return rightBottomPoint;
	}
	public void setRightBottomPoint(SearchPoint rightBottomPoint) {
		this.rightBottomPoint = rightBottomPoint;
	}
	@Override
	public String toString() {
		return "SearchReactangle [leftTopPoint=" + leftTopPoint
				+ ", rightBottomPoint=" + rightBottomPoint + "]";
	}

}
