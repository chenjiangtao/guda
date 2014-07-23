package com.tiaotiaogift.biz.common.taobao;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;

public class Test {
	
	public void test() throws ApiException, ParseException{
		String url=" http://container.open.taobao.com/container?appkey=21568734";
		String appkey = "21568734";
		String secret = "f2d1cc25067242ed494759c1dbf96a95 ";
		TaobaoClient client=new DefaultTaobaoClient(url, appkey, secret);
		TradesSoldGetRequest req=new TradesSoldGetRequest();
		req.setFields("total_fee");
		Date dateTime = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 00:00:00");
		req.setStartCreated(dateTime);
		Date end = SimpleDateFormat.getDateTimeInstance().parse("2000-01-01 23:59:59");
		req.setEndCreated(end);
		req.setStatus("ALL_WAIT_PAY");
		req.setBuyerNick("zhangsan");
		req.setType("game_equipment");
		req.setExtType("service");
		req.setRateStatus("RATE_UNBUYER");
		req.setTag("time_card");
		req.setPageNo(1L);
		req.setPageSize(40L);
		req.setUseHasNext(true);
		req.setIsAcookie(false);
		TradesSoldGetResponse response = client.execute(req , "sessionKey");
	}
	
    private static final String CONTAINER_URL = "https://oauth.tbsandbox.com/authorize?appkey=${appkey}";
    private static final String SESSION_PARAM_KEY = "top_session";
    private static final String LOGIN_URL = "http://login.taobao.com/member/login.jhtml";

    public static String getSessionKey(String appkey, String nick, String psw) {
        HttpClient httpClient = new HttpClient();
        String redirectURL = "";
        PostMethod postMethod = new PostMethod(LOGIN_URL);
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
        List<NameValuePair> nameValues = new ArrayList<NameValuePair>();
        nameValues.add(new NameValuePair("TPL_username", nick));
        nameValues.add(new NameValuePair("TPL_password", psw));
        nameValues.add(new NameValuePair("action", "Authenticator"));
        nameValues.add(new NameValuePair("TPL_redirect_url", ""));
        nameValues.add(new NameValuePair("from", "tb"));
        nameValues.add(new NameValuePair("event_submit_do_login", "anything"));
        nameValues.add(new NameValuePair("loginType", "3"));
        postMethod.setRequestBody(nameValues.toArray(new NameValuePair[nameValues.size()]));
        
        try {
            httpClient.executeMethod(postMethod);
            redirectURL = postMethod.getResponseHeader("Location").getValue();
            System.out.println("redirectURL: 1 " + redirectURL);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            postMethod.releaseConnection();
        }
        
        String newUrl = CONTAINER_URL.replace("${appkey}", appkey);
        try {
            URI uri = new URI(newUrl);
            postMethod.setURI(uri);
            httpClient.executeMethod(postMethod);
            
            redirectURL = postMethod.getResponseHeader("Location").getValue();
            System.out.println("redirectURL: 2 " + redirectURL);
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        } finally {
            postMethod.releaseConnection();
        }
        GetMethod get = new GetMethod(redirectURL);
        
        return extractSessionKey(get.getQueryString(), SESSION_PARAM_KEY);
    }
    
    private static String extractSessionKey(String rsp, String key) {
        if (rsp == null)
            return null;
        Map<String, String> nameValuePair = new HashMap<String, String>();
        String[] array = rsp.split("&");
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            if (s.indexOf("=") > 0) {
                String[] nameValues = s.split("=");
                if (nameValues.length == 2) {
                    nameValuePair.put(s.split("=")[0], s.split("=")[1]);
                }
            }
        }
        return nameValuePair.get(key);
    }

    public static void main(String[] args) {
        String sessionKey = Test.getSessionKey("1021568734", "11测试账号21", "sandbox5067242ed494759c1dbf96a95");
        System.out.println(sessionKey);
    }

}
