package com.kingmon.project.elastic.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kingmon.base.data.FastJsonDataSet;

public class DataCovertUtil {

	public static FastJsonDataSet convertJson(JSONObject jsonObj){
		//String xx="{\"_shards\":{\"total\":10,\"failed\":0,\"successful\":10},\"hits\":{\"hits\":[{\"_index\":\"psam\",\"_type\":\"psam\",\"_source\":{\"organ\":{\"pcs\":\"37010203\",\"fj\":\"370102\",\"jwq\":\"3701020304\",\"sj\":\"3701\"},\"dzmc\":\"南辛庄西路337号\",\"jzwmc\":\"山东省济南市市中区七贤街道办事处南辛庄西路337号\",\"location\":\"36.96121674,115.35322187\",\"type\":\"JZW\",\"status\":\"1\"},\"_id\":\"6113fa14-ce35-4fe4-aaed-d807c0f8000d\",\"_score\":1.287682},{\"_index\":\"psam\",\"_type\":\"psam\",\"_source\":{\"organ\":{\"pcs\":\"37010203\",\"fj\":\"370102\",\"jwq\":\"3701020304\",\"sj\":\"3701\"},\"jzwmc\":\"山东省济南市市中区七贤街道办事处南辛庄西路336号\",\"dzmc\":\"南辛庄西路336号\",\"location\":\"36.96121674,115.35322187\",\"type\":\"JZW\"},\"_id\":\"1\",\"_score\":1.0}],\"total\":2,\"max_score\":1.287682},\"took\":3,\"timed_out\":false}";
		//jsonObj=(JSONObject) JSONObject.parse(xx);
		FastJsonDataSet ds=FastJsonDataSet.newDs();
		if(jsonObj==null){
			return ds;
		}
		JSONObject obj=jsonObj.getJSONObject("hits");
		if(obj==null){
			return ds;
		}
		long total=obj.getLong("total");
		if(total==0){
			return ds;
		}
		JSONArray dataArray=obj.getJSONArray("hits");
		JSONArray arr=new JSONArray(dataArray.size());
		for(int i=0;i<dataArray.size();i++){
			JSONObject objxx=(JSONObject) dataArray.get(i);
			arr.add(objxx.get("_source"));
	
		}
		ds=new FastJsonDataSet(total, arr);
		return ds;
	}
	
	public static void main(String[] asd){
		String xx="{\"_shards\":{\"total\":10,\"failed\":0,\"successful\":10},\"hits\":{\"hits\":[{\"_index\":\"psam\",\"_type\":\"psam\",\"_source\":{\"organ\":{\"pcs\":\"37010203\",\"fj\":\"370102\",\"jwq\":\"3701020304\",\"sj\":\"3701\"},\"dzmc\":\"南辛庄西路337号\",\"jzwmc\":\"山东省济南市市中区七贤街道办事处南辛庄西路337号\",\"location\":\"36.96121674,115.35322187\",\"type\":\"JZW\",\"status\":\"1\"},\"_id\":\"6113fa14-ce35-4fe4-aaed-d807c0f8000d\",\"_score\":1.287682},{\"_index\":\"psam\",\"_type\":\"psam\",\"_source\":{\"organ\":{\"pcs\":\"37010203\",\"fj\":\"370102\",\"jwq\":\"3701020304\",\"sj\":\"3701\"},\"jzwmc\":\"山东省济南市市中区七贤街道办事处南辛庄西路336号\",\"dzmc\":\"南辛庄西路336号\",\"location\":\"36.96121674,115.35322187\",\"type\":\"JZW\"},\"_id\":\"1\",\"_score\":1.0}],\"total\":2,\"max_score\":1.287682},\"took\":3,\"timed_out\":false}";
		JSONObject onj=(JSONObject) JSONObject.parse(xx);
		System.out.println(onj);
		FastJsonDataSet ds=	convertJson(onj);
		System.out.println(ds);
	}
}
