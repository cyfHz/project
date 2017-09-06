package com.kingmon.project.elastic.model;

public class GeoPoint {
	
	private double lat;
	private double lon;
	public GeoPoint(){
		
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public GeoPoint(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}
}
