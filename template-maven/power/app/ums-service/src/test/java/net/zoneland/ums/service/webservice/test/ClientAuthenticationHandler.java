/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice.test;

import org.codehaus.xfire.MessageContext;
import org.codehaus.xfire.handler.AbstractHandler;
import org.jdom.Element;

public class ClientAuthenticationHandler extends AbstractHandler {

    //private static final Log logger = LogFactory.getLog(ClientAuthenticationHandler.class);

    private String appid;

    private String password;

    public ClientAuthenticationHandler() {

    }

    /**
     * @param username
     * @param password
     */
    public ClientAuthenticationHandler(String appid, String password) {
        this.appid = appid;
        this.password = password;
    }

    public void invoke(MessageContext ctx) throws Exception {
        //为SOAP Header构造验证信息
        // Element el = new Element("header");
        Element el = ctx.getOutMessage().getOrCreateHeader();
        Element auth = new Element("AuthenticationToken");
        Element appid_el = new Element("Appid");
        appid_el.addContent(appid);
        Element password_el = new Element("Password");
        password_el.addContent(password);
        auth.addContent(appid_el);
        auth.addContent(password_el);
        el.addContent(auth);
        ctx.getOutMessage().setHeader(el);

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

}
