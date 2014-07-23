package com.tiaotiaogift.web.home.taobao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.domain.Trade;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.api.response.TradesSoldGetResponse;
import com.tiaotiaogift.biz.common.taobao.TaobaoService;
import com.tiaotiaogift.common.dal.AccountMapper;
import com.tiaotiaogift.common.dal.ContactMapper;
import com.tiaotiaogift.common.dal.NotifyMapper;
import com.tiaotiaogift.common.dal.UserMapper;
import com.tiaotiaogift.common.mysql.dataobject.Account;
import com.tiaotiaogift.common.mysql.dataobject.Contact;
import com.tiaotiaogift.common.mysql.dataobject.Notify;
import com.tiaotiaogift.common.mysql.dataobject.User;
import com.tiaotiaogift.common.util.DateUtil;
import com.tiaotiaogift.common.util.MD5;
import com.tiaotiaogift.common.util.enums.NotifyEnum;
import com.tiaotiaogift.common.util.enums.UserStatusEnum;
import com.tiaotiaogift.web.home.C;
import com.tiaotiaogift.web.home.taobao.vo.NotifyVo;
import com.tiaotiaogift.web.home.ums.LoginController;

@Controller
public class TaobaoCallController {

    private Logger             logger         = LoggerFactory.getLogger(TaobaoCallController.class);

    @Autowired
    private UserMapper         userMapper;

    @Autowired
    private TaobaoService      taobaoService;

    @Autowired
    private ContactMapper      contactMapper;

    @Autowired
    private AccountMapper      accountMapper;

    @Autowired
    private NotifyMapper       notifyMapper;

    @Autowired
    private TaobaoNotify       taobaoNotify;

    public static final String appKey         = "21568734";

    public static final String appSecret      = "f2d1cc25067242ed494759c1dbf96a95";

    private String             taobaoOauthUrl = "https://oauth.taobao.com/authorize";

    private String             taokenUri      = "https://oauth.taobao.com/token";

    private String             redirectUri    = "http://www.ums365.com/taobao/taobaoCall.htm";

    private String             defaultUrl     = "https://oauth.taobao.com/authorize?response_type=code&client_id=21568734&redirect_uri=http://www.ums365.com/taobao/taobaoCall.htm&state=1212&view=web";

    public static final String NOTIFY_RULE    = "NOTIFY_RULE";

    @RequestMapping(value = "/taobao/taobaoCall.htm", method = RequestMethod.GET)
    public String doGet(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Object _sessionKey = request.getSession().getAttribute(C.TAOBAO_ACCESS_TOKEN);
        String code = request.getParameter("code");
        if (code == null && _sessionKey == null) {
            try {
                response.sendRedirect(defaultUrl);
                return null;
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        if (code != null) {

            Map<String, String> param = new HashMap<String, String>();
            param.put("grant_type", "authorization_code");
            param.put("code", code);
            param.put("client_id", appKey);
            param.put("client_secret", appSecret);
            param.put("redirect_uri", redirectUri);
            param.put("view", "web");
            //param.put("state", state);
            try {
                String responseJson = WebUtils.doPost(taokenUri, param, 3000, 3000);
                Gson gson = new Gson();
                TaobaoTokenResponse tbResponse = gson.fromJson(responseJson,
                    TaobaoTokenResponse.class);
                if (logger.isInfoEnabled()) {
                    logger.info("response:" + tbResponse);
                }
                if (tbResponse.getAccess_token() != null) {
                    request.getSession().setAttribute(C.TAOBAO_ACCESS_TOKEN,
                        tbResponse.getAccess_token());

                    request.getSession().setAttribute(C.TAOBAO_SESSION_KEY,
                        tbResponse.getAccess_token());
                    User user = userMapper.selectByUserId(tbResponse.getTaobao_user_nick());
                    String id = null;
                    Date startDate = null;
                    if (user == null) {
                        startDate = DateUtil.getLastDays(90);
                        user = new User();
                        id = UUID.randomUUID().toString();
                        user.setId(id);
                        user.setUserId(tbResponse.getTaobao_user_nick());
                        user.setPassword(MD5.md5("1234"));
                        user.setPhone("13500000000");
                        user.setEmail("");
                        user.setStatus(UserStatusEnum.OK.getValue());
                        user.setCode(UUID.randomUUID().toString());
                        user.setGmtCreated(new Date());
                        user.setGrade(0);
                        Integer linkId = userMapper.selectMaxLinkId();
                        if (linkId == null) {
                            user.setLinkId(1);
                        } else {
                            user.setLinkId(linkId + 1);
                        }
                        if (logger.isInfoEnabled()) {
                            logger.info("创建用户:" + user);
                        }
                        userMapper.insert(user);
                        Account a = new Account();
                        a.setBalance(100);
                        a.setBalanceLock(0);
                        a.setUserId(id);
                        a.setId(UUID.randomUUID().toString());
                        a.setGmtModify(new Date());
                        accountMapper.insert(a);
                    } else {
                        startDate = user.getGmtCreated();
                        User temp = new User();
                        temp.setId(user.getId());
                        temp.setGmtCreated(new Date());
                        userMapper.updateByPrimaryKeySelective(temp);
                    }
                    Notify notify = notifyMapper.selectByUserId(user.getId());
                    String notifyRule = null;
                    if (notify != null) {
                        notifyRule = notify.getRule();
                        Notify temp = new Notify();
                        temp.setId(UUID.randomUUID().toString());
                        temp.setSessionKey(tbResponse.getAccess_token());
                        notifyMapper.updateByPrimaryKeySelective(temp);
                    } else {
                        notify = new Notify();
                        notify.setId(UUID.randomUUID().toString());
                        notify.setUserId(user.getId());
                        notify.setSessionKey(tbResponse.getAccess_token());
                        notifyMapper.insert(notify);
                    }
                    LoginController.login(user, request, response, notifyRule);
                    AfterTask at = new AfterTask(id, startDate, tbResponse.getAccess_token());
                    Thread thread = new Thread(at);
                    thread.start();

                    NotifyTask notifyTask = new NotifyTask(tbResponse.getAccess_token());
                    Thread notifythread = new Thread(notifyTask);
                    notifythread.start();

                } else {
                    if (logger.isInfoEnabled()) {
                        logger.info("返回access token为空:" + tbResponse);
                    }
                    modelMap.addAttribute("tips", "请求淘宝返回错误,请联系管理员,QQ:52313882,电话15558135733");
                    return "tb/taobaoCallError.vm";
                }
            } catch (IOException e) {
                logger.error("", e);
            }

        }

        request.getSession().setAttribute(C.TAOBAO_CODE, code);
        //        try {
        //            StringBuilder buf = new StringBuilder();
        //            buf.append(request.getScheme()).append("://").append(request.getServerName());
        //            if (request.getServerPort() != 80) {
        //                buf.append(":").append(request.getServerPort());
        //            }
        //            buf.append("/ums/index.htm");
        //            response.sendRedirect(buf.toString());
        //            return null;
        //        } catch (IOException e) {
        //            logger.error("", e);
        //        }

        Notify notify = notifyMapper.selectByUserId(OperationContextHolder.getPrincipal()
            .getUserId());
        String rule = null;
        if (notify != null) {
            rule = notify.getRule();
        }
        List<NotifyVo> tradeVo = new ArrayList<NotifyVo>();
        NotifyEnum[] es = NotifyEnum.values();
        for (int i = 0, len = es.length; i < len; ++i) {
            NotifyVo vo = new NotifyVo();
            vo.setDescribe(es[i].getDescribe());
            vo.setCode(es[i].getCode());
            vo.setName(es[i].getName());
            vo.setChecked(isCheck(es[i].getCode(), rule));
            tradeVo.add(vo);
        }
        modelMap.addAttribute("tradeVo", tradeVo);

        return "tb/taobaoCall.vm";

    }

    class AfterTask implements Runnable {
        private String accessToken;
        private String userId;
        private Date   startDate;

        public AfterTask(String userId, Date startDate, String accessToken) {
            this.userId = userId;
            this.startDate = startDate;
            this.accessToken = accessToken;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                TradesSoldGetResponse tsgr = taobaoService.queryTrade(appKey, appSecret,
                    accessToken, startDate, new Date());
                if (tsgr != null && tsgr.getTrades() != null) {
                    List<Trade> trade = tsgr.getTrades();
                    logger.info("trade size:" + trade.size());
                    Iterator<Trade> it = trade.iterator();
                    List<Contact> cs = new ArrayList<Contact>(trade.size());
                    while (it.hasNext()) {
                        Trade tra = it.next();
                        if (tra.getReceiverMobile() != null) {
                            Map<String, Object> selparams = new HashMap<String, Object>();
                            selparams.put("userId", OperationContextHolder.getPrincipal()
                                .getUserId());
                            selparams.put("phone", tra.getReceiverMobile());
                            Contact contact = contactMapper.selectByPhone(selparams);
                            if (contact == null) {
                                contact = new Contact();
                                contact.setId(UUID.randomUUID().toString());
                                contact.setAddress(tra.getReceiverAddress());
                                contact.setEmail(tra.getBuyerEmail());
                                contact.setName(tra.getBuyerNick());
                                contact.setPhone(tra.getReceiverMobile());
                                contact.setUserId(userId);
                                contact.setTaobaoName(tra.getBuyerNick());
                                if (logger.isInfoEnabled()) {
                                    logger.info("创建联系人:" + cs);
                                }
                                int res = contactMapper.insert(contact);
                                if (res == 1) {
                                    cs.add(contact);
                                }
                            }
                        }
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("创建以下联系人成功:" + cs);
                    }

                }
            } catch (ApiException e) {
                logger.error("获取联系人错误", e);
            }
        }

    }

    class NotifyTask implements Runnable {

        private String accessToken;

        public NotifyTask(String accessToken) {
            this.accessToken = accessToken;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            try {
                taobaoNotify.startLisener();
                taobaoService.sendPermit(appKey, appSecret, accessToken);
            } catch (ApiException e) {
                logger.error("sendPermit error", e);
            }
        }

    }

    private boolean isCheck(String code, String rule) {
        if (code == null || rule == null) {
            return false;
        }
        code = code + ",";
        if (rule.indexOf(code) > -1) {
            return true;
        }
        return false;
    }

}
