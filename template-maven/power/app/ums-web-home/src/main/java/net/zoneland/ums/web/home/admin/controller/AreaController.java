package net.zoneland.ums.web.home.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zoneland.ums.biz.config.admin.UmsAreaBiz;
import net.zoneland.ums.common.dal.dataobject.UmsArea;
import net.zoneland.ums.web.home.base.BaseController;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AreaController extends BaseController {

    private final static Logger logger        = Logger.getLogger(AreaController.class);

    private final static int    AREANAMELEGTH = 12;

    @Autowired
    private UmsAreaBiz          umsAreaBiz;

    /**
     * 显示单位。
     * 
     * @return
     */
    @RequestMapping(value = "/area/list.htm")
    public String doList() {

        return "/admin/area/area.vm";
    }

    /**
     * 增加单位</br>
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/area/add.ajax")
    public void doAdd(HttpServletRequest request, HttpServletResponse response) {
        String parentId = request.getParameter("parentId");
        String areaCode = request.getParameter("areaCode");
        String areaName = request.getParameter("areaName");
        if (!checkAreaCode(areaCode) || !checkAreaName(areaName)) {
            ajaxReturn(false, response);
            return;
        }
        try {
            UmsArea test = umsAreaBiz.isExistOfAreaCode(areaCode);
            if (test != null) {
                boolean areaNameExist = umsAreaBiz.isExistOfAreaName(areaName, parentId);
                if (areaNameExist) {
                    ajaxReturn("allExist", response);
                    return;
                }
                ajaxReturn("exist", response);
                return;
            }
            boolean areaNameExist = umsAreaBiz.isExistOfAreaName(areaName, parentId);
            if (areaNameExist) {
                ajaxReturn("areaNameExist", response);
                return;
            }
            UmsArea umsArea = new UmsArea(areaCode, areaName, parentId);
            boolean save = umsAreaBiz.savaUmsArea(umsArea);
            if (save) {
                ajaxReturn("success", response);
            } else {
                ajaxReturn(false, response);
            }
        } catch (Exception e) {
            logger.error("增加单位出现系统异常", e);
            ajaxReturn("error", response);
        }

    }

    /**
     * 通过areaCode删除单位
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/area/del.ajax")
    public void doDel(HttpServletRequest request, HttpServletResponse response) {
        String areaCode = request.getParameter("areaCode");
        try {
            umsAreaBiz.delUmsAreaByAreaCode(areaCode);
        } catch (Exception e) {
            logger.error("删除单位出现系统异常！", e);
            ajaxReturn("error", response);
        }
        ajaxReturn(true, response);
    }

    /**
     * 更新单位名称
     * 
     * @param request
     * @param response
     */
    @RequestMapping(value = "/area/update.ajax")
    public void doUpdate(HttpServletRequest request, HttpServletResponse response) {
        String areaCode = request.getParameter("areaCode");
        String areaName = request.getParameter("areaName");
        String parentId = request.getParameter("parentId");
        if (!checkAreaName(areaName)) {
            ajaxReturn(false, response);
        }
        boolean result = false;
        try {
            UmsArea umsArea = umsAreaBiz.findUmsAreaByAreaCode(areaCode);
            if (umsArea != null && umsArea.getAreaName() != null
                && !umsArea.getAreaName().equalsIgnoreCase(areaName)) {
                boolean areaNameExist = umsAreaBiz.isExistOfAreaName(areaName, parentId);
                if (areaNameExist) {
                    ajaxReturn("areaNameExist", response);
                    return;
                }
            }
            result = umsAreaBiz.updateNameByCode(areaCode, areaName);
        } catch (Exception e) {
            logger.error("更新单位时出现系统异常！", e);
            ajaxReturn("error", response);
        }
        if (result) {
            ajaxReturn(true, response);
        } else {
            ajaxReturn(false, response);
        }
    }

    private boolean checkAreaCode(String areaCode) {
        if (StringUtils.isEmpty(areaCode)) {
            return false;
        }
        return StringUtils.trim(areaCode).matches("^[0-9]{1,36}$");
    }

    private boolean checkAreaName(String areaName) {
        if (StringUtils.isEmpty(areaName)) {
            return false;
        }
        if (areaName.length() > AREANAMELEGTH) {
            return false;
        }
        return true;
    }
}
