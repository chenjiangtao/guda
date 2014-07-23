/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package net.zoneland.ums.gateway.http.facade;

import java.util.List;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;

/**
 * 
 * @author Administrator
 * @version $Id: GatewayWebService.java, v 0.1 2013-1-9 ����5:26:24 Administrator Exp $
 */
public interface GatewayWebService {

    /**
     * ������Ϣ
     * @param msg
     * @return
     */
    Result sendMsg(Message msg);
    
    
    List<Result> sendMsgBatch(List<Message> msgs);

    /**
     * �ر�����
     * @param id ����ID
     * @return
     */
    Result closeGateway(String id);

    /**
     * ��������
     * @param id ����ID
     * @return
     */
    Result openGateway(String id);

    /**
     * ��ѯ�����Ƿ�����
     * @param id
     * @return
     */
    boolean isOpen(String id);

    List<Boolean> isOpen(List<String> list);

}
