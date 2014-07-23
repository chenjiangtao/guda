/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.velocity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.uribox.DefaultURIBox;
import net.zoneland.mvc.runtime.core.uribox.URIBox;
import net.zoneland.mvc.runtime.core.uribox.URIBoxManager;

import org.apache.log4j.Logger;
import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolManager;
import org.apache.velocity.tools.config.FileFactoryConfiguration;
import org.apache.velocity.tools.config.XmlFactoryConfiguration;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

/**
 *
 * @author ypz
 * @version $Id: VelocityToolboxView.java, v 0.1 2012-5-17 下午04:08:08 ypz Exp $
 */
public class VelocityToolboxView extends VelocityLayoutView {

    private final static Logger logger            = Logger.getLogger(VelocityToolboxView.class);

    public static final String  DEFAULTURL        = "http://localhost";

    public static final String  DEFAULTSERVERNAME = "homeServer";

    private DefaultURIBox       defaultBox        = null;

    private static final String defaultHost       = "smsapp.zj.sgcc.com.cn";

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
        velocityContext.put(DEFAULTSERVERNAME, getFullContextPath(request));
        while (it.hasNext()) {
            String key = it.next();
            URIBox uri = box.get(key);
            velocityContext.put(key, uri);

        }
        velocityContext.put("umsServer", getFullContextPath(request));

    }

    private DefaultURIBox getFullContextPath(HttpServletRequest request) {
        defaultBox = new DefaultURIBox(getReqHost(request));
        return defaultBox;
    }

    public static String getReqHost(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String path = request.getServletPath();
        if ("/".equals(path) || "".equals(path)) {
            if (url.indexOf(defaultHost) > -1) {
                logger.info("9081替换" + url + defaultHost);
                url = url.replace(":9081", "");
            }
        } else {
            String realPath = url.substring(0, url.indexOf(path));
            if (realPath.indexOf(defaultHost) > -1) {
                logger.info("9081替换" + url + defaultHost);
                realPath = realPath.replace(":9081", "");
            }
            return realPath;
        }
        return url;
    }

}
