/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.codec;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

/**
 * 
 * @author gag
 * @version $Id: MsgHeaderEncoder.java, v 0.1 2012-8-10 上午10:58:18 gag Exp $
 */
public class MsgEncoder extends ProtocolEncoderAdapter {

    private final static Logger logger = Logger.getLogger(MsgEncoder.class);

    private Charset             charset;

    public MsgEncoder(String charset) {
        this.charset = Charset.forName(charset);
    }

    /** 
     * @see org.apache.mina.filter.codec.ProtocolEncoder#encode(org.apache.mina.core.session.IoSession, java.lang.Object, org.apache.mina.filter.codec.ProtocolEncoderOutput)
     */
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
                                                                                    throws Exception {

        String msg = String.valueOf(message);
        //        byte[] value = msg.getBytes(charset.name());
        //        byte[] m = new byte[value.length + 5];
        //        byte[] len = String.valueOf(value.length).getBytes("GBK");
        //        System.arraycopy(len, 0, m, 0, len.length);
        //        System.arraycopy(value, 0, m, 5, value.length);
        byte[] m = buildMsg(msg);

        IoBuffer buffer = IoBuffer.allocate(m.length).setAutoExpand(true);
        buffer.put(m);
        buffer.flip();
        out.write(buffer);
    }

    private byte[] buildMsg(String msg) {
        try {

            return String.format("%-5d%s", msg.getBytes("GBK").length, msg).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            return String.format("%-5d%s", msg.getBytes().length, msg).getBytes();
        }
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.println(String.format("%-5d%s", "0000".getBytes("GBK").length, "0000"));
    }

}
