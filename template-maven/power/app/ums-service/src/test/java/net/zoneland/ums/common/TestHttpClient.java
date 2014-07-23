package net.zoneland.ums.common;

import java.io.IOException;


import net.zoneland.ums.service.webservice.MsgService;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHttpClient {
	
	public static void main(String[] args) throws HttpException, IOException{
		  ApplicationContext appContext = new ClassPathXmlApplicationContext("httpclient.xml");
	        HttpClient s = (HttpClient) appContext.getBean("httpClient");
	        HttpMethod method = new GetMethod();
	        method.setURI(new URI("http://www.ums365.com"));
	      int res =   s.executeMethod(method);
	        System.out.println(res);
	}

}
