package net.zoneland.ums.web.home.iphone.form;

public class SetMsgStatusRequest {
	
	  private String token;     // 服务器生成的唯一序列号
	    private String msgId;      // 接收方号码，多个以英文逗号”,”分隔
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getMsgId() {
			return msgId;
		}
		public void setMsgId(String msgId) {
			this.msgId = msgId;
		}

}
