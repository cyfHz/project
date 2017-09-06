package com.kingmon.project.elastic.util;

public enum ElasticTypes {
	MLPH("mlph"),JZWJBXX("jzwjbxx"),JWQ("jwq");
	private final String typeName ;
	
	private ElasticTypes(String typeName) {
		this.typeName  = typeName;
	}
	public String getTypeName (){
		return this.typeName;
	}
}
