package com.tributelambdaapi.model;

public class Martyr {
	private int martyrId;
	private String martyrName;
	private String martyrYear;	
	private String martyrImageName;
	public int getMartyrId() {
		return martyrId;
	}
	public void setMartyrName(String martyrName){
		this.martyrName = martyrName;
	}
	public String getMartyrName() {
		return martyrName;
	}
	public void setMartyrId(int  martyrId){
		this.martyrId = martyrId;
	}
	public String getMartyrYear() {
		return martyrYear;
	}
	public void setMartyrYear(String martyrYear) {
		this.martyrYear = martyrYear;
	}
	public void setImage(String martyrImageName) {
		this.martyrImageName = martyrImageName;
	}
	public String getMartyrImageName() {
		return martyrImageName;
	}
}