package net.zoneland.ums.service.invoke.test;

import net.zoneland.ums.gateway.Message;
import net.zoneland.ums.gateway.Result;
import net.zoneland.ums.gateway.http.facade.GatewayFacade;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GatewayServiceClientTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
            "spring-httpinvoke-client-test.xml"); //读取配置文件

        GatewayFacade service = (GatewayFacade) applicationContext.getBean("gatewayFacade"); //通过接口得到服务器信息
        Message m = new Message();
        m.setAck(1);
        m.setContent("测试短信");
        m.setContentType(8);
        // m.setFee(1);
        // m.setFeeType(1);
        //99cc1dff-4518-4e99-b11b-64cd9b13180a
        //5d3262d2-2177-40be-8b3a-bb877f8effcf
        m.setGatewayId("5d3262d2-2177-40be-8b3a-bb877f8effcf");
        m.setPriority(8);
        m.setRecvId(new String[] { "8613588754574", "8613312341234" });
        m.setSendId("95598");
        Result res = service.sendMsg(m); //调用服务器端的方法

        System.out.println("远程调用，测试成功!" + res);
        // boolean rr = service.isOpen("f912740e-929c-4565-af8f-98a2abc03cbd");
        // System.out.println("远程调用，测试成功!" + rr);
        // res = service.openGateway("5d3262d2-2177-40be-8b3a-bb877f8effcf");
        //  System.out.println("远程调用，测试成功!" + res);
        //res = service.closeGateway("5d3262d2-2177-40be-8b3a-bb877f8effcf");
        // System.out.println("远程调用，测试成功!" + res);
    }

}
