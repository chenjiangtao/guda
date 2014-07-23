/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.tiaotiaogift.web.home.ums.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author gang
 * @version $Id: SuggestionForm.java, v 0.1 2013-5-10 下午3:09:04 gang Exp $
 */
public class SuggestionForm {

    @NotEmpty(message = "建议不能为空")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
