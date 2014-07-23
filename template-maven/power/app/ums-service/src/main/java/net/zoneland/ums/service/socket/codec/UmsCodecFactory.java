/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.codec;

import org.apache.mina.filter.codec.demux.DemuxingProtocolCodecFactory;

/**
 * 
 * @author gag
 * @version $Id: UmsCodecFactory.java, v 0.1 2012-10-15 下午2:35:06 gag Exp $
 */
public class UmsCodecFactory extends DemuxingProtocolCodecFactory {

    private String charset = "GBK";

    public UmsCodecFactory() {

        super.addMessageDecoder(new UmsMessageDecoder(charset));
        //super.addMessageEncoder(ResultMessage.class, ResultMessageEncoder.class);

    }

}
