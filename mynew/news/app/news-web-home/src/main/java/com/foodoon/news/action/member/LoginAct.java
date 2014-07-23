package com.foodoon.news.action.member;

import static com.foodoon.news.Constants.TPLDIR_CSI;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.foodoon.common.web.RequestUtils;
import com.foodoon.news.entity.main.CmsSite;
import com.foodoon.news.web.CmsUtils;
import com.foodoon.news.web.FrontUtils;

@Controller
public class LoginAct {
	public static final String LOGIN_CSI = "tpl.loginCsi";

	/**
	 * 客户端包含
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login_csi.jspx")
	public String csi(HttpServletRequest request, ModelMap model) {
		CmsSite site = CmsUtils.getSite(request);
		// 将request中所有参数
		model.putAll(RequestUtils.getQueryParams(request));
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CSI, LOGIN_CSI);
	}

}
