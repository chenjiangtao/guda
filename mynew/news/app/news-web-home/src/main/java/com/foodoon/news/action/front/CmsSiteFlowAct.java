package com.foodoon.news.action.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.common.web.RequestUtils;
import com.foodoon.common.web.ResponseUtils;
import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.service.CmsSiteFlowCache;
import com.foodoon.news.web.CmsUtils;

@Controller
public class CmsSiteFlowAct {
	@RequestMapping("/flow_statistic.jspx")
	public void flowStatistic(HttpServletRequest request,
			HttpServletResponse response, String page, String referer) {
		if (!StringUtils.isBlank(page)) {
			String ip = RequestUtils.getIpAddr(request);
			CmsSite site = CmsUtils.getSite(request);
			String sessionId = RequestUtils.getRequestedSessionId(request);
			cmsSiteFlowCache.flow(site, ip, sessionId, page, referer);
			ResponseUtils.renderJson(response, "true");
		} else {
			ResponseUtils.renderJson(response, "false");
		}
	}
	
	@Autowired
	private CmsSiteFlowCache cmsSiteFlowCache;
}
