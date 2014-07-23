package com.tiaotiaogift.web.home.ums.form;

import javax.validation.constraints.Size;

public class ResetPasswordForm {
	
	private String code;
	
	private String u;
	
	@Size(min = 4, max = 20, message = "密码长度4到20个字")
	private String password;
	
	@Size(min = 4, max = 20, message = "密码长度4到20个字")
	private String passwordSec;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSec() {
		return passwordSec;
	}

	public void setPasswordSec(String passwordSec) {
		this.passwordSec = passwordSec;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

}
