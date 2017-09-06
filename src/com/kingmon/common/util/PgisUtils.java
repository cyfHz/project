package com.kingmon.common.util;

public class PgisUtils {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PgisUtils t=new PgisUtils();
		String sds="115.977599,36.427575,115.993224,36.427423,115.993010,36.427209,115.991517,36.422250,115.987333,36.419580,115.990966,36.407830,115.991270,36.405877,115.984862,36.403863,115.980681,36.409906,115.980376,36.411554,115.980986,36.413446,115.980803,36.413995,115.980193,36.414361,115.978972,36.413995,115.978606,36.413385,115.977141,36.413324,115.975005,36.415611,115.974272,36.417381,115.975310,36.418785,115.978423,36.419944,115.978911,36.420616,115.978728,36.421653,115.976958,36.423301,115.977599,36.427575";
		String p="115.99107,36.42693";
		
		boolean flag=PtInPolygon(p,sds);
		System.out.println(flag);
	}
	public static boolean PtInPolygon(Point p,Point pt[],int nCount){
		int nCross=0;
		for(int i=0;i<nCount;i++){
			Point p1=pt[i];
			Point p2=pt[(i+1)%nCount];
			if(p1.getY()==p2.getY()){
				if(p.getY()==p1.getY()&&p.getX()>=min(p1.getX(),p2.getX())&&p.getX()<=max(p1.getX(),p2.getX()))
					return true;
				continue;
			}
			if(p.getY()<min(p1.getY(),p2.getY())||p.getY()>max(p1.getY(),p2.getY()))
				continue;
			double x=(double)(p.getY()-p1.getY())*(double)(p2.getX()-p1.getX())/(double)(p2.getY()-p1.getY())+p1.getX();
			if(x>p.getX())nCross++;
			else if(x==p.getX())
				return true;
		}
		if(nCross%2==1)
			return true;
		
		
		return false;
	}
	public static boolean PtInPolygon(String point,String points){
		String[] temPoint=point.split("\\,");
		if(temPoint.length==0)
		{
			return false;
		}
		String[] temPoints=points.split("\\,"); 
		if(temPoints.length<6)
		{
			return false;
		}
		Point[] pt=new Point[temPoints.length/2];
		Point p=new Point(Double.valueOf(temPoint[0]),Double.valueOf(temPoint[1]));
		int j=0;
		for(int i=0;i<temPoints.length-1;i+=2)
		{
			pt[j]=new Point(Double.valueOf(temPoints[i]),Double.valueOf(temPoints[i+1]));
			j++;
		}
		int nCross=0;
		if(pt==null || pt.length==0 || pt.length<3)
		{
			return false;
		}
		int nCount=pt.length;
		for(int i=0;i<nCount;i++){
			Point p1=pt[i];
			Point p2=pt[(i+1)%nCount];
			if(p1.getY()==p2.getY()){
				if(p.getY()==p1.getY()&&p.getX()>=min(p1.getX(),p2.getX())&&p.getX()<=max(p1.getX(),p2.getX()))
					return true;
				continue;
			}
			if(p.getY()<min(p1.getY(),p2.getY())||p.getY()>max(p1.getY(),p2.getY()))
				continue;
			double x=(double)(p.getY()-p1.getY())*(double)(p2.getX()-p1.getX())/(double)(p2.getY()-p1.getY())+p1.getX();
			if(x>p.getX())nCross++;
			else if(x==p.getX())
				return true;
		}
		if(nCross%2==1)
			return true;
		
		
		return false;
	}
	
	public static double min(double f1,double f2){
		if(f1<f2)return f1;
		return f2;
	}
	public static double max(double f1,double f2){
		if(f1<f2)return f2;
		return f1;
	}
	
}

