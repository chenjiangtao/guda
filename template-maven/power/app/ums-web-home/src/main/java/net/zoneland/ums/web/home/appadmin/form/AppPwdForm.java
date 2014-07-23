/**
 * 
 */
package net.zoneland.ums.web.home.appadmin.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author louguodong
 *
 */
public class AppPwdForm {
	
	@NotEmpty(message = "应用id不能为空")
	private String id;//appId

	@NotEmpty(message = "新密码不能为空")
    private String newPassword;
    
    private String rePassword;

    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
    
    
    
}
