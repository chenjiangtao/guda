package com.tiaotiaogift.web.home.ums.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tiaotiaogift.common.dal.MsgOutMapper;
import com.tiaotiaogift.common.mysql.dataobject.MsgOutVo;
import com.tiaotiaogift.web.home.BaseJsonController;
import com.tiaotiaogift.web.home.ums.MySendController;
import com.tiaotiaogift.web.home.ums.admin.form.SearchMsgForm;
import com.tiaotiaogift.web.home.ums.admin.form.StatForm;
import com.tiaotiaogift.web.home.ums.vo.UiVo;

@Controller
public class StatMsgController extends BaseJsonController {

    @Autowired
    private MsgOutMapper msgOutMapper;

    @RequestMapping(value = "/ums/admin/stat.htm", method = RequestMethod.GET)
    public String doGet(SearchMsgForm searchMsgForm, BindingResult result,
                        HttpServletRequest request, ModelMap modelMap) {

        return "ums/admin/stat.vm";
    }

    @RequestMapping(value = "/ums/admin/stat.json")
    public void doLoad(StatForm form, HttpServletRequest request, HttpServletResponse response,
                       ModelMap modelMap) {
        if (form.getPage() == null || form.getPage() < 1) {
            form.setPage(1);
        }

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("start", (form.getPage() - 1) * form.getRows());
        params.put("rows", form.getRows());
        Date startTime = MySendController.getDate(form.getStartTime());
        Date endTime = MySendController.getDate(form.getEndTime());
        if (startTime != null && endTime != null) {
            if (startTime.after(endTime)) {
                Date t = startTime;
                startTime = endTime;
                endTime = t;
            }
            params.put("startTime", startTime);
            params.put("endTime", endTime);
        }
        List<MsgOutVo> list = new ArrayList<MsgOutVo>();
        int count = 0;
        params.put("userName", form.getUserName());
        list = msgOutMapper.selectByUserName(params);
        count = msgOutMapper.selectCountByUserName(params);

        UiVo v = new UiVo();
        v.setRows(list);
        v.setTotal(count);
        super.ajaxReturn(v, response);
    }

}
