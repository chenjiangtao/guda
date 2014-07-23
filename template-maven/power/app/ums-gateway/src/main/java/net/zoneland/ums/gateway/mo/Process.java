/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.gateway.mo;

/**
 * 
 * @author Administrator
 * @version $Id: Process.java, v 0.1 2012-11-15 下午4:25:14 Administrator Exp $
 */
public interface Process {

    public void saveDeliverMsg(String spNumber, String sendNumber, String recvNumber,
                               String recvMsg, String mediaId, int data_code);

}
