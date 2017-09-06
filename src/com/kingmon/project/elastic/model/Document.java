package com.kingmon.project.elastic.model;

import com.alibaba.fastjson.annotation.JSONField;
/*
 * 		Document doc = new Document();
		doc.setLocation("36.96121674,115.35322187");
		doc.setJzwmc("山东省济南市市中区七贤街道办事处南辛庄西路337号");
		doc.setDzmc("南辛庄西路337号");
		doc.setType("JZW");
		doc.setStatus("1");
		doc.setOrgan(new Organ("3701", "370102", "37010203", "3701020304"));
		doc.setId("6113fa14-ce35-4fe4-aaed-d807c0f8000d");
 */
public class Document {
	
	private String id;
	private String location;
	private String jzwmc;
	private String dzmc;
	private String type;
	private String status;
	
	private String sj;//市局
	private String fj;//分局
	private String pcs;//派出所
	private String jwq;//jingwuqu 

	
	

	public String getLocation() {
		return location;
	}

	public Document setLocation(String location) {
		this.location = location;
		return this;
	}

	public String getJzwmc() {
		return jzwmc;
	}

	public Document setJzwmc(String jzwmc) {
		this.jzwmc = jzwmc;
		return this;
	}

	public String getDzmc() {
		return dzmc;
	}

	public Document setDzmc(String dzmc) {
		this.dzmc = dzmc;
		return this;
	}

	public String getType() {
		return type;
	}

	public Document setType(String type) {
		this.type = type;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public Document setStatus(String status) {
		this.status = status;
		return this;
	}

	@JSONField(deserialize=false,serialize=false)
	public String getId() {
		return id;
	}

	public Document setId(String id) {
		this.id = id;
		return this;
	}

	public String getSj() {
		return sj;
	}

	public Document setSj(String sj) {
		this.sj = sj;
		return this;
	}
	public String getFj() {
		return fj;
	}

	public Document setFj(String fj) {
		this.fj = fj;
		return this;
	}

	public String getPcs() {
		return pcs;
	}

	public Document setPcs(String pcs) {
		this.pcs = pcs;
		return this;
	}

	public String getJwq() {
		return jwq;
		
	}

	public Document setJwq(String jwq) {
		this.jwq = jwq;
		return this;
	}
}
