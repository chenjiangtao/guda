/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.uribox;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import org.springframework.util.StringUtils;

/**
 * 
 * @author gag
 * @version $Id: DefaultURIBox.java, v 0.1 2012-5-29 下午4:05:46 gag Exp $
 */
public class DefaultURIBox implements URIBox {

    private LinkedHashMap<String, String> queryData = new LinkedHashMap<String, String>();

    private String                        protocol  = "http";

    private String                        host;

    private String                        path;

    private int                           port;

    public DefaultURIBox() {

    }

    public void copy(DefaultURIBox box) {
        this.host = box.getHost();
        this.path = box.getPath();
        this.port = box.getPort();
        this.protocol = box.getProtocol();
        this.queryData.putAll(box.getQueryData());
    }

    /**
     * Setter method for property <tt>uri</tt>.
     * 
     * @param uri value to be assigned to property uri
     * @throws MalformedURLException 
     */
    public void setUri(String uri) throws MalformedURLException {
        URL url = new URL(uri);
        this.protocol = url.getProtocol();
        this.host = url.getHost();
        this.port = url.getPort();
        this.path = url.getPath();
        String queryStr = url.getQuery();
        if (StringUtils.hasLength(queryStr)) {
            String[] querys = queryStr.split("&");
            for (int i = 0, len = querys.length; i < len; ++i) {
                if (querys[i].indexOf("=") > 0) {
                    String[] params = querys[i].split("=");
                    queryData.put(params[0], params[1]);
                }
            }
        }

    }

    public DefaultURIBox(String uri) {
        try {
            setUri(uri);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

    }

    /** 
     * @see net.zoneland.sms.web.home.base.URIBox#escapeURL(java.lang.String)
     */
    public String escapeURL(String uri) {
        return null;
    }

    /** 
     * @see net.zoneland.sms.web.home.base.URIBox#render()
     */
    public String render() {
        StringBuilder buff = new StringBuilder();
        buff.append(protocol).append("://").append(host);
        if (port > -1) {
            buff.append(":").append(port);
        }
        buff.append(path).append("?");
        Set<String> set = queryData.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            buff.append(key).append("=").append(queryData.get(key)).append("&");
        }
        return buff.substring(0, buff.length() - 1);
    }

    /** 
     * @see net.zoneland.sms.web.home.base.URIBox#getURI()
     */
    public URIBox getURI(String path) {
        if (StringUtils.hasLength(path)) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasLength(this.path)) {

                if (!this.path.startsWith("/")) {
                    this.path = "/" + this.path;
                }
                if (this.path.endsWith("/")) {
                    this.path = this.path.substring(0, path.length() - 1);
                }
                path = this.path + path;
            }
        }
        DefaultURIBox box = new DefaultURIBox();
        box.copy(this);
        box.setPath(path);

        return box;
    }

    /** 
     * @see net.zoneland.sms.web.home.base.URIBox#addQueryData(java.lang.String, java.lang.String)
     */
    public URIBox addQueryData(String key, String value) {
        DefaultURIBox box = new DefaultURIBox();
        box.copy(this);
        if (StringUtils.hasLength(key)) {
            box.getQueryData().put(key, value);
        }

        return box;
    }

    public static void main(String[] args) {

        DefaultURIBox url = new DefaultURIBox("http://local:8080/aab");
        System.out.println(url);
        url.addQueryData("vv", "33");
        System.out.println(url);
        System.out.println(url.getURI("/a.htm/").addQueryData("cc", "33").addQueryData("dd", "33"));
        System.out.println(url.getURI("/a.htm/").addQueryData("ee", "33"));
        System.out.println(url);

    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return render();
    }

    /**
     * Getter method for property <tt>queryData</tt>.
     * 
     * @return property value of queryData
     */
    public LinkedHashMap<String, String> getQueryData() {
        return queryData;
    }

    /**
     * Setter method for property <tt>queryData</tt>.
     * 
     * @param queryData value to be assigned to property queryData
     */
    public void setQueryData(LinkedHashMap<String, String> queryData) {
        this.queryData = queryData;
    }

    /**
     * Getter method for property <tt>protocol</tt>.
     * 
     * @return property value of protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Setter method for property <tt>protocol</tt>.
     * 
     * @param protocol value to be assigned to property protocol
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Getter method for property <tt>host</tt>.
     * 
     * @return property value of host
     */
    public String getHost() {
        return host;
    }

    /**
     * Setter method for property <tt>host</tt>.
     * 
     * @param host value to be assigned to property host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Getter method for property <tt>path</tt>.
     * 
     * @return property value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Setter method for property <tt>path</tt>.
     * 
     * @param path value to be assigned to property path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Getter method for property <tt>port</tt>.
     * 
     * @return property value of port
     */
    public int getPort() {
        return port;
    }

    /**
     * Setter method for property <tt>port</tt>.
     * 
     * @param port value to be assigned to property port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.uribox.URIBox#addPath(java.lang.String)
     */
    public String addPath(String path) {
        if (StringUtils.hasLength(path)) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (!StringUtils.hasLength(this.path)) {
                this.path = path;
            } else {
                if (!this.path.startsWith("/")) {
                    this.path = "/" + this.path;
                }
                if (this.path.endsWith("/")) {
                    this.path = this.path.substring(0, path.length() - 1);
                }
                this.path = this.path + path;
            }
        }
        return render();
    }

    /** 
     * @see com.foodoon.mvc.runtime.core.uribox.URIBox#set(java.lang.String)
     */
    public URIBox set(String path) {
        if (StringUtils.hasLength(path)) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasLength(this.path)) {

                if (!this.path.startsWith("/")) {
                    this.path = "/" + this.path;
                }
                if (this.path.endsWith("/")) {
                    this.path = this.path.substring(0, path.length() - 1);
                }
                path = this.path + path;
            }
        }
        DefaultURIBox box = new DefaultURIBox();
        box.copy(this);
        box.setPath(path);
        return box;

    }

}
