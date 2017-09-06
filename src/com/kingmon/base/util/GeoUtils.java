package com.kingmon.base.util;

import java.awt.geom.GeneralPath;
import java.util.List;

import com.kingmon.project.elastic.model.GeoPoint;



public class GeoUtils {

	/**
	 * 计算空间球面两点之间距离
	 * @param lat1 点1纬度
	 * @param lng1 点1经度
	 * @param lat2 点2纬度
	 * @param lng2 点2经度
	 * @return 两点之间距离
	 */
	public static double distance(double lat1, double lng1, double lat2, double lng2) {
		double dx = lng1 - lng2; // 经度差值
		double dy = lat1 - lat2; // 纬度差值
		double b = (lat1 + lat2) / 2.0; // 平均纬度
		double Lx = Math.toRadians(dx) * 6370996.81 * Math.cos(Math.toRadians(b)); // 东西距离
		double Ly = 6370996.81  * Math.toRadians(dy); // 南北距离
		return Math.sqrt(Lx * Lx + Ly * Ly); // 用平面的矩形对角距离公式计算总距离
	}
	
	public static boolean isPointInRect(double pointLat,double pointLng){
		return false;
	}
	
	public static boolean isPointInCircle (double pointLat,double pointLng,double clat,double clng,double radius){
		return radius > GeoUtils.distance(pointLat, pointLng, clat, clng);
	}
	
	
	public static boolean isPointInPolygon(double lat,double lng,List<Double> x,List<Double> y){
		if(x==null||y==null||x.size()!=y.size()||x.size()<3){
			return false;
		}
		GeneralPath g = new GeneralPath();
		g.moveTo(x.get(0), y.get(0));
		for(int i=1;i<x.size();i++){
			g.lineTo(x.get(i), y.get(i));
		}
		g.closePath();
		boolean isIn = g.contains(lat,lng);
		g = null;
		return isIn;
	}
	
	public static GeoPoint pgis2Point(String location){
		String[] arr = location.split(",");
		return new GeoPoint(Double.valueOf(arr[1]),Double.valueOf(arr[0]));
		
	}
}
