package com.kingmon.zay;

import java.util.Map;


public class JavaAuthBaseBean {


	private Map<String,Object> map;
	
	 public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	
	/**
	  * 状态枚举
	  * @author lu
	  *
	  */
	 public static enum STATE {
		 	ENABLE("1", "可用"), DISABLE("0","不可用");
			public String key;
			public String value;
			private STATE(String key, String value) {
				this.key = key;
				this.value = value;
			}
			public static STATE get(int key) {
				STATE[] values = STATE.values();
				for (STATE object : values) {
					if (object.key.equals(key)) {
						return object;
					}
				}
				return null;
			}
		}
	 /**
	 	 * 删除枚举
	 	 * @author lu
	 	 *
	 	 */
	 	public static enum DELETED {
			NO(0, "未删除"), YES(1,"已删除");
			public int key;
			public String value;
			private DELETED(int key, String value) {
				this.key = key;
				this.value = value;
			}
			public static DELETED get(int key) {
				DELETED[] values = DELETED.values();
				for (DELETED object : values) {
					if (object.key == key) {
						return object;
					}
				}
				return null;
			}
		}


}
