/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * 
 * @author gag
 * @version $Id: MsgHeaderCodecFactory.java, v 0.1 2012-8-10 上午10:31:55 gag Exp $
 */
public class MsgCodecFactory implements ProtocolCodecFactory {

    private String          charset = "GBK";

    private ProtocolEncoder encoder = new MsgEncoder(charset);

    private ProtocolDecoder decoder = new MsgDecoder(charset); ;

    /** 
     * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getEncoder(org.apache.mina.core.session.IoSession)
     */
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    /** 
     * @see org.apache.mina.filter.codec.ProtocolCodecFactory#getDecoder(org.apache.mina.core.session.IoSession)
     */
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

    /**
     * Setter method for property <tt>charset</tt>.
     * 
     * @param charset value to be assigned to property charset
     */
    public void setCharset(String charset) {
        this.charset = charset;
        decoder = new MsgDecoder(charset);
    }

}
