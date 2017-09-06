package com.kingmon.project.psam.datasync.view;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class SyncParam2 implements Serializable{
	
	private String craeteUserId;
	
	private List<Object> sourceValues;
	private List<Object> targetValues;
	
	
	public SyncParam2() {
		super();
	}
	public static SyncParam2 newSP() {
		return new SyncParam2();
	}
	
	public int getParamSize() {
		return this.sourceValues.size();
	}
	
	public  SyncParam2(List<Object> sourceValues, List<Object> targetValues) {
		if(targetValues==null||targetValues.isEmpty()){
			return;
		}
		if(sourceValues==null||sourceValues.isEmpty()){
			return;
		}
		this.sourceValues = sourceValues;
		this.targetValues = targetValues;
	}
//------------------------------------------------------------------------------------------------------------------------------	
	public SyncParam2 setParamValues(List<String> sourceValues, List<String> targetValues) {
		if(targetValues==null||targetValues.isEmpty()){
			return null;
		}
		if(sourceValues==null||sourceValues.isEmpty()){
			return null;
		}
		init();
		for(int i=0;i<sourceValues.size();i++){
			this.sourceValues.add(sourceValues.get(i));
		}
		for(int i=0;i<targetValues.size();i++){
			this.targetValues.add(targetValues.get(i));
		}
		return this;
	}
	public SyncParam2 setParamValues(Object[] sourceValues, Object[] targetValues) {
		if(targetValues==null||targetValues.length==0){
			return null;
		}
		if(sourceValues==null||sourceValues.length==0){
			return null;
		}
		init();
		for(int i=0;i<sourceValues.length;i++){
			this.sourceValues.add(sourceValues[i]);
		}
		for(int i=0;i<targetValues.length;i++){
			this.targetValues.add(targetValues[i]);
		}
		return this;
	}
	public SyncParam2 setParamValues(String[] sourceValues, String[] targetValues) {
		if(targetValues==null||targetValues.length==0){
			return null;
		}
		if(sourceValues==null||sourceValues.length==0){
			return null;
		}
		init();
		for(int i=0;i<sourceValues.length;i++){
			this.sourceValues.add(sourceValues[i]);
		}
		for(int i=0;i<targetValues.length;i++){
			this.targetValues.add(targetValues[i]);
		}
		return this;
	}
//------------------------------------------------------------------------------------------------------------------------------
	public SyncParam2 setParamValues(Object oneRepeatSourceValue,List<Object> targetValues) {
		if(targetValues==null||targetValues.isEmpty()){
			return null;
		}
		init();
		for(int i=0;i<targetValues.size();i++){
			sourceValues.add(oneRepeatSourceValue);
		}
		this.targetValues=targetValues;
		return this;
	}
	public SyncParam2 setParamValues(String oneRepeatSourceValue,List<String> targetValues) {
		if(targetValues==null||targetValues.isEmpty()){
			return null;
		}
		init();
		for(int i=0;i<targetValues.size();i++){
			this.sourceValues.add(oneRepeatSourceValue);
			this.targetValues.add(targetValues.get(i));
		}
		return this;
	}
	public SyncParam2 setParamValues(String oneRepeatSourceValue,String[] targetValues) {
		if(targetValues==null||targetValues.length==0){
			return null;
		}
		init();
		for(int i=0;i<targetValues.length;i++){
			this.sourceValues.add(oneRepeatSourceValue);
			this.targetValues.add(targetValues[i]);
		}
		return this;
	}
//-------------------------------------------------------------------------------------------------------------------
	public SyncParam2 setParamValues(List<String> sourceValues,String oneRepeatTargetValue) {
		if(sourceValues==null||sourceValues.isEmpty()){
			return null;
		}
		init();
		for(int i=0;i<sourceValues.size();i++){
			this.sourceValues.add(sourceValues.get(i));
			targetValues.add(oneRepeatTargetValue);
		}
		return this;
	}
	public SyncParam2 setParamValues(String[] sourceValues,String oneRepeatTargetValue) {
		if(sourceValues==null||sourceValues.length==0){
			return null;
		}
		init();
		for(int i=0;i<sourceValues.length;i++){
			this.sourceValues.add(sourceValues[i]);
			targetValues.add(oneRepeatTargetValue);
		}
		return this;
	}
//-------------------------------------------------------------------------------------------------------------------	
	private void init(){
		if(this.sourceValues==null){
			this.sourceValues=Lists.newArrayList();
		}
		if(this.targetValues==null){
			this.targetValues=Lists.newArrayList();
		}
		for(int i=0;i<sourceValues.size();i++){
			this.sourceValues.add(sourceValues.get(i));
		}
	}
//-------------------------------------------------------------------------------------------------------------------		
	public List<Object> getSourceValues() {
		return sourceValues;
	}
	public void setSourceValues(List<Object> sourceValues) {
		this.sourceValues = sourceValues;
	}
	public List<Object> getTargetValues() {
		return targetValues;
	}
	public void setTargetValues(List<Object> targetValues) {
		this.targetValues = targetValues;
	}
	public String getCraeteUserId() {
		return craeteUserId;
	}
	public SyncParam2 setCraeteUserId(String craeteUserId) {
		this.craeteUserId = craeteUserId;
		return this;
	}
	
	
}
