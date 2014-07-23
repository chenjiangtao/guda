package net.zoneland.ums.service.webservice.test;

import java.net.URL;

import net.zoneland.ums.service.webservice.form.FetchRequest;

import org.apache.xmlbeans.impl.util.Base64;
import org.codehaus.xfire.client.Client;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

public class FetchMsgTest {

    public static void main(String[] args) throws Exception {
        //http://172.16.86.103:9081/ums
        String wsdl = "http://localhost:8080/ums/services/MsgService?wsdl";
        //wsdl = "http://172.16.86.103:9081/ums/services/MsgService?wsdl";
        FetchRequest sr = new FetchRequest();
        sr.setAppId("1000");
        sr.setSubAppId("");
        sr.setMaxCount(2);
        System.out.println(StringUtils.hasText(" "));
        Gson gson = new Gson();
        String json = gson.toJson(sr);
        System.out.println(gson.fromJson(json, FetchRequest.class).getSubAppId());
        sr = new FetchRequest();
        sr.setAppId("1000");
        sr.setMaxCount(2);
        String json2 = gson.toJson(sr);
        Client client = new Client(new URL(wsdl)); //根据WSDL创建客户实例  
        client.addOutHandler(new ClientAuthenticationHandler("1000", "1111"));
        Client client2 = new Client(new URL(wsdl)); //根据WSDL创建客户实例  
        client2.addOutHandler(new ClientAuthenticationHandler("1000", "1111"));

        // Object[] res = client.invoke("fetchMsgJson", new Object[] { json });
        // System.out.println(res[0]);
        FetchMsgTest t = new FetchMsgTest();
        new Thread(t.new Invoke(client, new String(Base64.encode(json.getBytes("utf-8"))))).start();
        new Thread(t.new Invoke(client2, new String(Base64.encode(json.getBytes("utf-8")))))
            .start();
    }

    public class Invoke implements Runnable {
        Client client;
        String json;

        public Invoke(Client client, String json) {
            this.client = client;
            this.json = json;
        }

        /** 
         * @see java.lang.Runnable#run()
         */
        public void run() {
            Object[] res;
            try {
                res = client.invoke("fetchMsgJson", new Object[] { json });
                System.out.println(new String(Base64.decode(res[0].toString().getBytes("utf-8"))));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
