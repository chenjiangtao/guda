/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.service.socket.codec;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 * 
 * @author gag
 * @version $Id: MsgHeaderDecoder.java, v 0.1 2012-8-10 上午10:42:04 gag Exp $
 */
public class MsgDecoder implements ProtocolDecoder {

    public static final String CONTEXT_ATTR = "CONTEXT_ATTR";

    public MsgDecoder(String charset) {
        Charset.forName(charset);
    }

    /** 
     * @see org.apache.mina.filter.codec.CumulativeProtocolDecoder#doDecode(org.apache.mina.core.session.IoSession, org.apache.mina.core.buffer.IoBuffer, org.apache.mina.filter.codec.ProtocolDecoderOutput)
     */

    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)

    throws Exception {
        //        Context ctx = getContext(session);
        //        // 先把当前buffer中的数据追加到Context的buffer当中   
        //        ctx.append(in);
        //        //把position指向0位置，把limit指向原来的position位置  
        //        IoBuffer buf = ctx.getBuffer();
        //        buf.flip();
        //        System.out.println("remain:" + buf.remaining());
        //        while (buf.remaining() > 4) {
        //            buf.mark();
        //            DecodeResult dr = new DecodeResult();
        //            try {
        //                byte[] bLeng = new byte[5];
        //                buf.get(bLeng);
        //                String msg = new String(bLeng);
        //                int len = getLen(msg);
        //                System.out.println("len:" + len);
        //                System.out.println("remain:" + buf.remaining());
        //                if (len <= 4) {
        //                    buf.clear();
        //                    System.out.println("reset remain:" + buf.remaining());
        //                    break;
        //                } else if (buf.remaining() >= len - 4) {
        //
        //                    String msgCode = buf.getString(4, charset.newDecoder());
        //                    System.out.println("code:" + msgCode);
        //                    byte[] body = new byte[len - 4];
        //                    buf.get(body, 0, len - 4);
        //                    System.out.println(new String(body));
        //                    ServiceRequest sr = null;
        //                    sr = RequestParserHelper.parseRequest(msgCode, body);
        //                    if (sr != null) {
        //                        try {
        //                            InetSocketAddress clientsa = (InetSocketAddress) session
        //                                .getRemoteAddress();
        //                            InetSocketAddress serversa = (InetSocketAddress) session
        //                                .getRemoteAddress();
        //                            if (clientsa != null) {
        //                                sr.setClientIp(clientsa.getHostName());
        //                                sr.setClientPort(clientsa.getPort());
        //                            }
        //                            if (serversa != null) {
        //                                sr.setServerPort(serversa.getPort());
        //                            }
        //                        } catch (Exception e) {
        //
        //                        }
        //
        //                        dr.setServiceRequest(sr);
        //                        dr.setSuccess(true);
        //                    }
        //                    out.write(dr);
        //                    //                    byte[] src = new byte[len];
        //                    //                    buf.get(src, 5, len);
        //                    //                    String msgCode = StringUtils.trimAllWhitespace(new String(src, 5, 4));
        //                    //                    ServiceRequest sr = null;
        //                    //                    if (src.length > 4) {
        //                    //                        byte[] msgBody = new byte[len - 9];
        //                    //                        System.arraycopy(src, 9, msgBody, 0, len - 9);
        //                    //                        sr = RequestParserHelper.parseRequest(msgCode, msgBody);
        //                    //                    } else {
        //                    //                        sr = new ServiceRequest();
        //                    //                        sr.setRequestCode(msgCode);
        //                    //                    }
        //                    //                                        InetSocketAddress clientsa = (InetSocketAddress) session.getRemoteAddress();
        //                    //                                        InetSocketAddress serversa = (InetSocketAddress) session.getRemoteAddress();
        //                    //                                        if (clientsa != null) {
        //                    //                                            sr.setClientIp(clientsa.getHostName());
        //                    //                                            sr.setClientPort(clientsa.getPort());
        //                    //                                        }
        //                    //                                        if (serversa != null) {
        //                    //                                            sr.setServerPort(serversa.getPort());
        //                    //                                        }
        //                    //                    dr.setServiceRequest(sr);
        //                    //                    out.write(dr);
        //                } else {
        //                    buf.reset();
        //                    break;
        //                }
        //
        //                if (buf.hasRemaining()) {
        //                    // 将数据移到buffer的最前面   
        //                    IoBuffer temp = IoBuffer.allocate(2048).setAutoExpand(true);
        //                    temp.put(buf);
        //                    temp.flip();
        //                    buf.clear();
        //                    buf.put(temp);
        //
        //                } else {// 如果数据已经处理完毕，进行清空  
        //                    buf.clear();
        //                }
        //            } catch (Exception e) {
        //                logger.error("解码错误！", e);
        //                dr.setSuccess(false);
        //                dr.setMsg(e.getMessage());
        //                out.write(dr);
        //            }

        //        }

    }

    /** 
     * @see org.apache.mina.filter.codec.ProtocolDecoder#finishDecode(org.apache.mina.core.session.IoSession, org.apache.mina.filter.codec.ProtocolDecoderOutput)
     */
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
    }

    /** 
     * @see org.apache.mina.filter.codec.ProtocolDecoder#dispose(org.apache.mina.core.session.IoSession)
     */
    public void dispose(IoSession session) throws Exception {
    }

}
