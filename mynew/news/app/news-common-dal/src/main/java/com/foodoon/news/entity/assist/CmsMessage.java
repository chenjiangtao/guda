package com.foodoon.news.entity.assist;

import com.foodoon.common.util.StrUtils;
import com.foodoon.news.entity.assist.base.BaseCmsMessage;



public class CmsMessage extends BaseCmsMessage {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsMessage () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsMessage (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsMessage (
		java.lang.Integer id,
		com.foodoon.news.entity.main.CmsUser msgReceiverUser,
		com.foodoon.news.entity.main.CmsUser msgSendUser,
		com.foodoon.news.entity.main.CmsSite site,
		java.lang.String msgTitle,
		java.lang.Boolean msgStatus,
		java.lang.Integer msgBox) {

		super (
			id,
			msgReceiverUser,
			msgSendUser,
			site,
			msgTitle,
			msgStatus,
			msgBox);
	}
	public String getTitleHtml() {
		return StrUtils.txt2htm(getMsgTitle());
	}
	public String getContentHtml() {
		return StrUtils.txt2htm(getMsgContent());
	}


/*[CONSTRUCTOR MARKER END]*/


}