package com.tiaotiaogift.web.home.ums.form;

public class BaseForm {

	private String _form_token;

	private String pageId;

	private String startTime;

	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String get_form_token() {
		return _form_token;
	}

	public void set_form_token(String _form_token) {
		this._form_token = _form_token;
	}



}
