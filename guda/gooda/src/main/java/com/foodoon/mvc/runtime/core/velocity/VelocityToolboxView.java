/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.velocity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.FileFactoryConfiguration;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

import com.foodoon.mvc.runtime.core.uribox.DefaultURIBox;
import com.foodoon.mvc.runtime.core.uribox.URIBox;
import com.foodoon.mvc.runtime.core.uribox.URIBoxManager;

/**
 *
 * @author ypz
 * @version $Id: VelocityToolboxView.java, v 0.1 2012-5-17 下午04:08:08 ypz Exp $
 */
public class VelocityToolboxView extends VelocityLayoutView {

    public static final String defaultUrl        = "http://localhost";

    public static final String defaultServerName = "homeServer";

    private DefaultURIBox      defaultBox        = null;

    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request,
                                            HttpServletResponse response) throws Exception {

        ViewToolContext ctx;

        ctx = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());

        ctx.putAll(model);

        if (this.getToolboxConfigLocation() != null) {
            ToolManager tm = new ToolManager();
            tm.setVelocityEngine(getVelocityEngine());
            ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

            InputStream input = currentClassLoader.getResourceAsStream(getToolboxConfigLocation());
            FileFactoryConfiguration config = new XmlFactoryConfiguration(false);
            config.read(input);
            tm.configure(config);
            if (tm.getToolboxFactory().hasTools(Scope.REQUEST)) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.REQUEST));
            }
            if (tm.getToolboxFactory().hasTools(Scope.APPLICATION)) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.APPLICATION));
            }
            if (tm.getToolboxFactory().hasTools(Scope.SESSION)) {
                ctx.addToolbox(tm.getToolboxFactory().createToolbox(Scope.SESSION));
            }
        }
        return ctx;
    }

    /**
     * @see org.springframework.web.servlet.view.velocity.VelocityView#exposeHelpers(org.apache.velocity.context.Context, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void exposeHelpers(Context velocityContext, HttpServletRequest request)
            throws Exception {
        super.exposeHelpers(velocityContext, request);
        URIBoxManager uriBoxManager = new URIBoxManager();
        Map<String, URIBox> box = uriBoxManager.loadURIBox();
        Iterator<String> it = box.keySet().iterator();
        velocityContext.put(defaultServerName, getFullContextPath(request));
        while (it.hasNext()) {
            String key = it.next();
            URIBox uri = box.get(key);
            if (uri.render().equals(defaultUrl)) {
                velocityContext.put(key, getFullContextPath(request));
            } else {
                velocityContext.put(key, uri);
            }
        }
        VelocityEngine velocityEngine = super.getVelocityEngine();
        velocityContext.put("tile",new Tile(velocityEngine,velocityContext));

    }

    private DefaultURIBox getFullContextPath(HttpServletRequest request) {
        if (defaultBox == null) {
            StringBuilder buf = new StringBuilder();
            buf.append(request.getScheme()).append("://").append(request.getServerName());
            if (request.getServerPort() != 80) {
                buf.append(":").append(request.getServerPort());
            }
            buf.append(request.getContextPath());
            defaultBox = new DefaultURIBox(buf.toString());
        }
        return defaultBox;
    }

    public static String getFullContextURL(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String path = request.getServletPath();
        return url.substring(0, url.indexOf(path));

    }

}
