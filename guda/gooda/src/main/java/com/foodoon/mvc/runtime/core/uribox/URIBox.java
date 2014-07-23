/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.uribox;

/**
 * 
 * @author gag
 * @version $Id: URIBox.java, v 0.1 2012-5-29 下午3:59:28 gag Exp $
 */
public interface URIBox {

    String escapeURL(String uri);

    public String render();

    public URIBox getURI(String path);

    public String addPath(String path);

    public URIBox addQueryData(String key, String value);

}
