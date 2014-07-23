package com.tiaotiaogift.web.home.doc;

public class Content {
	
	@Override
	public String toString() {
		return "Content [h=" + h + ", detail=" + detail + "]";
	}
	private String h;
	private String detail;
	public String getH() {
		return h;
	}
	public void setH(String h) {
		this.h = h;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

}
