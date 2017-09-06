package com.kingmon.project.elastic.model;

import java.io.Serializable;

public class SearchCircle implements Serializable{

	private SearchPoint point;
	private String radius;
	public SearchPoint getPoint() {
		return point;
	}
	public void setPoint(SearchPoint point) {
		this.point = point;
	}
	public String getRadius() {
		return radius;
	}
	public void setRadius(String radius) {
		this.radius = radius;
	}
	@Override
	public String toString() {
		return "SearchCircle [point=" + point + ", radius=" + radius + "]";
	}
	
}
