package com.foodoon.info.web.home;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.zoneland.mvc.runtime.core.config.ConfigrationFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/down.htm")
public class DownController {
	
	 private final static Logger logger = LoggerFactory.getLogger(DownController.class);

	@RequestMapping(method = RequestMethod.GET)
	public void doGet(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) {
		String f = request.getParameter("f");
		String basedir = ConfigrationFactory.getConfigration().getAppRoot();
		basedir =basedir+File.separator+"file"+File.separator+"doc"+File.separator;
		try {
            response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f);
logger.info("read:"+basedir+f);
			
			InputStream is  = new BufferedInputStream(new FileInputStream(basedir+f));
			File file = new File(basedir+f);
	
            byte[] buf = new byte[is.available()];
            is.read(buf);
            is.close();
            response.addHeader("Content-Length", ""+file.length());
			
			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buf);
			os.flush();
			os.close();
			
		} catch (Exception e) {
			logger.error("",e);
		}

	}

}
