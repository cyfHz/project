package com.kingmon.base.data;

import java.sql.Timestamp;
import java.util.HashMap;

import com.kingmon.base.exception.ServiceLogicalException;
import com.kingmon.base.util.KAssert;

@SuppressWarnings("rawtypes")
public class DataObject extends HashMap {
	
	public DataObject() {
	}

	@SuppressWarnings("unchecked")
	public DataObject(HashMap map) {
		super(map);
	}

	public boolean containsKey(String key) throws ServiceLogicalException {
		KAssert.hasText(key,"关键字为空");
		key = key.toLowerCase();
		return super.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public Object put(String key, Object value) throws ServiceLogicalException {
		KAssert.hasText(key,"关键字为空");
		if ((value instanceof java.sql.Date)) {
			value = new java.util.Date(((java.sql.Date) value).getTime());
		}
		if ((value instanceof Timestamp)) {
			value = new java.util.Date(((Timestamp) value).getTime());
		}
		return super.put(key, value);
	}

	public Object get(String key, Object defaultValue) throws ServiceLogicalException {
		KAssert.hasText(key,"关键字为空");
		if (!super.containsKey(key)) {
			return defaultValue;
		}
		return super.get(key);
	}
}
