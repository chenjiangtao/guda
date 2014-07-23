/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.webservice;

/**
 * 
 * @author gag
 * @version $Id: MsgService.java, v 0.1 2012-10-17 上午9:30:40 gag Exp $
 */
public interface MsgService {

    /**
     * 支持单个对象短信
     * 
     * @param json
     * @return
     */
    public String sendMsgJson(String json);

    /**
     * webservice接口支持list结合发送的短信
     * 
     * @param json
     * @return
     */
    public String sendMsgListJson(String json);

    public String sendCommonMsgJson(String json);

    public String sendCommonMsgListJson(String json);

    /**
     * 取上行短信
     * 
     * @param json
     * @return
     */
    public String fetchMsgJson(String json);

    /**
     * 取短信模版
     * 
     * @param json
     * @return
     */
    public String fetchTemplateJson(String json);

}
