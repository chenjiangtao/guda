package com.foodoon.news.entity.assist;

import com.foodoon.news.entity.assist.base.BaseCmsJobApply;



public class CmsJobApply extends BaseCmsJobApply {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public CmsJobApply () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public CmsJobApply (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public CmsJobApply (
		java.lang.Integer id,
		com.foodoon.news.entity.main.Content content,
		com.foodoon.news.entity.main.CmsUser user,
		java.util.Date applyTime) {

		super (
			id,
			content,
			user,
			applyTime);
	}

/*[CONSTRUCTOR MARKER END]*/


}