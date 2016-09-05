package com.infosys.service.rest.asset;

public class CSVTaskBean {
	private String cId;
	private String aID;
	private String aURL;
	private String wURL;
	public String getcId() {
		return cId;
	}
	public void setcId(String cId) {
		this.cId = cId;
	}
	public String getaID() {
		return aID;
	}
	public void setaID(String aID) {
		this.aID = aID;
	}
	public String getaURL() {
		return aURL;
	}
	public void setaURL(String aURL) {
		this.aURL = aURL;
	}
	public String getwURL() {
		return wURL;
	}
	public void setwURL(String wURL) {
		this.wURL = wURL;
	}

	@Override
	public String toString(){
		return "\nContent ID="+getcId()+"Asset ID="+getaID()+" Ad URL="+getaURL()+" Wrapper URL="+getwURL();
	}
}
