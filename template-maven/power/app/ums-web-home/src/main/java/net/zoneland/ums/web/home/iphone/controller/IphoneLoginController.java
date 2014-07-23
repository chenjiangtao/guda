/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.iphone.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContext;
import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.OperationPrincipal;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.msg.in.impl.MsgInServiceImpl;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.biz.user.role.RoleInfo;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.GsonHelper;
import net.zoneland.ums.common.util.constants.LoginInfoConstants;
import net.zoneland.ums.common.util.enums.user.RoleNameEnum;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.filter.AccessFilter;
import net.zoneland.ums.web.home.iphone.form.IphoneCodeConstants;
import net.zoneland.ums.web.home.iphone.form.LoginOutRequest;
import net.zoneland.ums.web.home.iphone.form.LoginRequest;
import net.zoneland.ums.web.home.iphone.form.LoginResponse;
import net.zoneland.ums.web.home.iphone.form.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yangjuanying
 * @version $Id: LoginServiceImpl.java, v 0.1 2012-9-25 下午10:29:30 yangjuanying Exp $
 */
@Controller
public class IphoneLoginController extends BaseController {
    private static final Logger logger = Logger.getLogger(MsgInServiceImpl.class);

    @Autowired
    private UmsUserInfoBiz      umsUserInfoBiz;

    @RequestMapping(value = "/login.json")
    public void loginJson(String json, HttpServletRequest request, HttpServletResponse httpResponse) {
        if (logger.isInfoEnabled()) {
            logger.info("request json:" + json);
        }
        LoginRequest lr = GsonHelper.gson().fromJson(json, LoginRequest.class);
        String res = validLoginRequest(lr);// 验证LoginRquest请求参数是否为空
        if ("1".equals(res)) {// 返回"1"表示请求参数都不为空

            LoginResponse response = new LoginResponse();
            UmsUserInfo userInfo = umsUserInfoBiz.loginHandleNoMD5(
                StringUtils.trimWhitespace(lr.getUserName()),
                StringUtils.trimWhitespace(lr.getPassword()));
            if (userInfo == null) {
                //校验失败
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("用户名或者密码错误");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;

            } else {
                List<RoleInfo> roles = umsUserInfoBiz.getRoleByUserId(userInfo.getId());
                OperationPrincipal p = new OperationPrincipal();
                p.setLogonId(lr.getUserName());
                p.setUserId(userInfo.getId());
                p.setUserName(userInfo.getUserName());
                p.setUserPhone(userInfo.getPhone());
                p.setEmail(userInfo.getEmail());
                p.setIp(request.getRemoteAddr());
                p.setAttrMap(LoginInfoConstants.IS_ORG_ADMIN_ATTR, userInfo.getOrgAdmin());
                p.setAttrMap(LoginInfoConstants.ORG_ID_ATTR, userInfo.getOrganizationId());
                RoleInfo[] ro = null;
                if (roles != null && roles.size() == 0) {
                    RoleInfo role = new RoleInfo();
                    role.setRoleName(RoleNameEnum.NORMAL.getValue());
                    ro = new RoleInfo[1];
                    ro[0] = role;
                } else {
                    ro = new RoleInfo[roles.size()];
                    roles.toArray(ro);
                }

                OperationContext context = new OperationContext(p, ro);
                OperationContextHolder.setOperationContext(context);

                request.getSession().setAttribute(AccessFilter.OPERATIONATTR, context);

                String sessionId = request.getSession().getId();
                response.setCode(IphoneCodeConstants.SUCCESS);
                response.setMsg(sessionId);
                response.setPhone(userInfo.getPhone());
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
        }

        Response response = new Response();
        response.setCode(IphoneCodeConstants.OTHER_ERROR);
        response.setMsg(res);
        jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
    }

    @RequestMapping(value = "/loginOut.json")
    public void loginOutJson(String json, HttpServletRequest request,
                             HttpServletResponse httpResponse) {

        LoginOutRequest lor = GsonHelper.gson().fromJson(json, LoginOutRequest.class);
        String res = validLoginOutRequest(lor);// 验证LoginOutRquest请求参数是否为空
        Response response = new Response();
        if ("1".equals(res)) {// 返回"1"表示请求参数都不为空
            if (request.getSession().getAttribute(AccessFilter.OPERATIONATTR) == null) {
                response.setCode(IphoneCodeConstants.FAILURE);
                response.setMsg("未登录，请先登录。");
                jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
                return;
            }
            request.getSession().removeAttribute(AccessFilter.OPERATIONATTR);
            request.getSession().invalidate();// 释放session，退出后使cookie值JSESSIONID有变化
            SecurityContextHolder.clear();
            jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
            return;
        }
        response.setCode(IphoneCodeConstants.OTHER_ERROR);
        response.setMsg(res);
        jsonReturn(GsonHelper.gson().toJson(response), httpResponse);
    }

    /**
     * 验证LoginRquest请求参数是否为空
     * 
     * @param lr
     * @return
     */
    private String validLoginRequest(LoginRequest lr) {
        if (lr == null) {
            return "请求参数不能为空";
        }
        if (!StringUtils.hasText(lr.getUserName())) {
            return "userName不能为空";
        }
        if (!StringUtils.hasText(lr.getPassword())) {
            return "password不能为空";
        }
        return "1";
    }

    /**
     * 验证LoginOutRquest请求参数是否为空
     * 
     * @param lor
     * @return
     */
    private String validLoginOutRequest(LoginOutRequest lor) {
        if (lor == null) {
            return "请求参数不能为空";
        }
        if (!StringUtils.hasText(lor.getToken())) {
            return "token不能为空";
        }
        return "1";
    }

    public void setUmsUserInfoBiz(UmsUserInfoBiz umsUserInfoBiz) {
        this.umsUserInfoBiz = umsUserInfoBiz;
    }

}
