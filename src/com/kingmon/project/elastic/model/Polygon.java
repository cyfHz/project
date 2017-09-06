package com.kingmon.project.elastic.model;

import java.util.List;

public class Polygon {
	private List<GeoPoint> points;
	public Polygon(){
	}
	public Polygon(List<GeoPoint> points){
		this.points=points;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}

	public void setPoints(List<GeoPoint> points) {
		this.points = points;
	}
	
	
}
