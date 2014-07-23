/**
h * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.web.home.user.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import net.zoneland.mvc.runtime.core.security.OperationContextHolder;
import net.zoneland.mvc.runtime.core.security.SecurityContextHolder;
import net.zoneland.ums.biz.config.util.AjaxResult;
import net.zoneland.ums.biz.group.MainTainGroupService;
import net.zoneland.ums.biz.msg.ObjectBuilder;
import net.zoneland.ums.biz.user.UmsContactBiz;
import net.zoneland.ums.biz.user.UmsUserInfoBiz;
import net.zoneland.ums.common.dal.bo.UmsGroupBo;
import net.zoneland.ums.common.dal.dataobject.UmsContact;
import net.zoneland.ums.common.dal.dataobject.UmsGroup;
import net.zoneland.ums.common.dal.dataobject.UmsGroupUserRel;
import net.zoneland.ums.common.dal.dataobject.UmsUserInfo;
import net.zoneland.ums.common.util.StringRegUtils;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;
import net.zoneland.ums.web.home.base.BaseController;
import net.zoneland.ums.web.home.user.form.GroupForm;
import net.zoneland.ums.web.home.user.form.GroupUserRelForm;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.HtmlUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 *有关群组维护的控制类
 *创建群组，删除群组，增加群组成员，删除群组成员等
 * @author wangyong
 * @version $Id: GroupController.java, v 0.1 2012-8-27 下午5:54:51 Administrator Exp $
 */
@Controller
public class GroupController extends BaseController {

    private final Logger         logger        = Logger.getLogger(GroupController.class);

    @Autowired
    private MainTainGroupService mainTainGroupService;
    @Autowired
    private UmsUserInfoBiz       umsUserInfoBiz;

    @Autowired
    private UmsContactBiz        umsContactBiz;

    private final static long    FILE_MAX_SIZE = 1024 * 1024;

    @RequestMapping(value = "/user/mygroup.htm")
    public String doGet(GroupForm groupForm, HttpServletRequest request, ModelMap modelMap) {
        PageResult<UmsGroup> result = getPageResult(groupForm);
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/group.vm";
    }

    private PageResult<UmsGroup> getPageResult(GroupForm groupForm) {
        if (groupForm == null) {
            groupForm = new GroupForm();
        }
        String userId = OperationContextHolder.getPrincipal().getUserId();
        UmsGroupBo bo = new UmsGroupBo();
        bo.setGroupName(StringUtils.hasText(groupForm.getSearchName()) ? groupForm.getSearchName()
            : null);
        bo.setUserId(userId);
        bo.setOrderBy("GMT_CREATED");
        bo.setOrderbyType("DESC");
        PageResult<UmsGroup> result = mainTainGroupService.searchMyGroupByPage(bo,
            groupForm.getPageId());
        return result;
    }

    @RequestMapping(value = "/user/saveGroup.htm", method = RequestMethod.GET)
    public String saveGroupView(GroupForm groupForm, HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/addGroup.vm";
    }

    @RequestMapping(value = "/user/saveGroup.htm", method = RequestMethod.POST)
    public String saveGroup(@Valid GroupForm groupForm, BindingResult result,
                            HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("group", groupForm);
        if (result.hasErrors()) {
            return "user/group/addGroup.vm";
        }
        String userId = OperationContextHolder.getPrincipal().getUserId();
        if (!mainTainGroupService.existGroup(groupForm.getGroupName(), userId, null)) {
            List<UmsGroupUserRel> groupUserRelList = getGroupUserRelListFromJson(groupForm
                .getRelJson());
            try {
                String groupId = mainTainGroupService.saveGroup(userId, groupForm.getGroupName(),
                    groupUserRelList);
                addSuccessMsg(modelMap, "“" + groupForm.getGroupName() + "”");
                groupForm.setGroupName(null);
                groupForm.setRelJson(null);
                modelMap.addAttribute("id", groupId);
                return "user/group/addGroupSuccess.vm";
            } catch (Exception e) {
                logger.error("群组保存异常", e);
                addErrorMsg(modelMap, "群组“" + groupForm.getGroupName() + "”新增失败");
            }
        } else {
            addErrorMsg(modelMap, "群组“" + groupForm.getGroupName() + "”已存在");
        }
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/addGroup.vm";
    }

    private List<UmsGroupUserRel> getGroupUserRelListFromJson(String relJson) {
        if (StringUtils.hasText(relJson)) {
            relJson = HtmlUtils.htmlUnescape(relJson);
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<List<UmsGroupUserRel>>() {
                }.getType();
                List<UmsGroupUserRel> relList = gson.fromJson(relJson, type);
                return relList;
            } catch (JsonSyntaxException e) {
                logger.error("Gson转化异常", e);
            }
        }
        return null;
    }

    @RequestMapping(value = "/user/updateGroup.htm", method = RequestMethod.GET)
    public String updateGroupView(GroupForm groupForm, HttpServletRequest request, ModelMap modelMap) {
        UmsGroup group = mainTainGroupService.getById(groupForm.getId());
        if (group != null) {
            groupForm.setGroupName(group.getGroupName());
            List<UmsGroupUserRel> relList = mainTainGroupService.getGroupUserRelByGroupId(group
                .getId());
            List<GroupUserRelForm> groupUserRelForms = new ArrayList<GroupUserRelForm>();
            String relJson = "[]";
            if (relList != null && relList.size() > 0) {
                for (UmsGroupUserRel umsGroupUserRel : relList) {
                    GroupUserRelForm groupUserRelForm = new GroupUserRelForm();
                    ObjectBuilder.copyObject(umsGroupUserRel, groupUserRelForm);
                    if (StringRegUtils.isPhoneNumber(umsGroupUserRel.getUserDesc())) {
                        groupUserRelForm.setPhone(umsGroupUserRel.getUserDesc());
                        groupUserRelForms.add(groupUserRelForm);
                        continue;
                    }
                    UmsUserInfo umsUserInfo = umsUserInfoBiz.getUmsUserInfoById(umsGroupUserRel
                        .getUserDesc());
                    if (umsUserInfo != null) {
                        groupUserRelForm.setPhone(umsUserInfo.getPhone());
                    } else {
                        UmsContact umsContact = umsContactBiz.searchByPrimaryKey(umsGroupUserRel
                            .getUserDesc());
                        if (umsContact == null) {
                            continue;
                        }
                        groupUserRelForm.setPhone(umsContact.getPhone());
                    }
                    groupUserRelForms.add(groupUserRelForm);
                }
                relJson = new Gson().toJson(groupUserRelForms);
            }
            groupForm.setRelJson(relJson);
        }
        modelMap.addAttribute("group", groupForm);
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/editGroup.vm";
    }

    @RequestMapping(value = "/user/updateGroup.htm", method = RequestMethod.POST)
    public String updateGroup(@Valid GroupForm groupForm, BindingResult result,
                              HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("group", groupForm);
        if (result.hasErrors()) {
            return "user/group/editGroup.vm";
        }
        String userId = OperationContextHolder.getPrincipal().getUserId();
        if (mainTainGroupService.existGroup(groupForm.getGroupName(), userId, groupForm.getId())) {
            addErrorMsg(modelMap, "群组“" + groupForm.getGroupName() + "”已存在");
            return "user/group/editGroup.vm";
        }
        UmsGroup group = mainTainGroupService.getById(groupForm.getId());
        if (group != null) {
            List<UmsGroupUserRel> groupUserRelList = getGroupUserRelListFromJson(groupForm
                .getRelJson());
            try {
                group.setGroupName(groupForm.getGroupName());
                mainTainGroupService.updateGroup(group, groupUserRelList);
                addSuccessMsg(modelMap, "“" + groupForm.getGroupName() + "”");
                modelMap.addAttribute("id", group.getId());
                return "user/group/editGroupSuccess.vm";
            } catch (Exception e) {
                logger.error("群组保存异常", e);
                addErrorMsg(modelMap, "群组“" + groupForm.getGroupName() + "”修改失败");
            }
        }
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/editGroup.vm";
    }

    @RequestMapping(value = "/user/deleteGroup.htm")
    public String deleteGroup(GroupForm groupForm, HttpServletRequest request, ModelMap modelMap) {
        UmsGroup group = mainTainGroupService.getById(groupForm.getId());
        if (group != null) {
            try {
                mainTainGroupService.deleteGroup(group.getId());
                addSuccessMsg(modelMap, "群组“" + group.getGroupName() + "”删除成功");
            } catch (Exception e) {
                logger.error("群组删除异常", e);
                addErrorMsg(modelMap, "群组“" + group.getGroupName() + "”删除失败");
            }
        } else {
            addErrorMsg(modelMap, "群组删除失败");
        }
        PageResult<UmsGroup> result = getPageResult(groupForm);
        modelMap.addAttribute("result", result);
        modelMap.addAttribute("groupForm", groupForm);
        return "user/group/group.vm";
    }

    /**
     * 根据群组主键查询出群组信息</br>
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/user/queryGroupsById.ajax")
    public void doQuery(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");// 群组主键ID或用户主键ID
        AjaxResult ajaxResult = new AjaxResult();
        try {
            UmsGroupUserRel umsGroupUserRel = mainTainGroupService.getGroupUserRelById(id);
            if (umsGroupUserRel != null) {
                ajaxResult.setResult(true);
                ajaxResult.setInfo("umsGroupUserRel");
                ajaxResult.setResultObject(umsGroupUserRel);
                ajaxReturn(ajaxResult, response);
            }
            List<UmsGroupUserRel> umsGroupUserRels = mainTainGroupService
                .getGroupUserRelByGroupId(id);
            if (umsGroupUserRels != null && umsGroupUserRels.size() > 0) {
                // Collections.sort(umsGroupUserRels, new MyGroupcomparator());
                ajaxResult.setResult(true);
                ajaxResult.setInfo("umsGroupUserRelList");
                ajaxResult.setResultObject(umsGroupUserRels);
                ajaxReturn(ajaxResult, response);
            }
            ajaxResult.setResultObject(false);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("noGroupMembers");
            ajaxReturn(ajaxResult, response);
        } catch (Exception e) {
            logger.error("群组右侧显示列表时，查询群组时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("error");
            ajaxReturn(ajaxResult, response);
        }
    }

    /**
     * 增加群组成员</br>
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/user/addMembers.ajax")
    public void doAdd(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");// 群组主键ID
        String relJson = request.getParameter("relJson");//成员ID，用逗号分隔成字符串
        List<UmsGroupUserRel> groupUserRelList = getGroupUserRelListFromJson(relJson);
        AjaxResult ajaxResult = new AjaxResult();
        try {
            boolean save = false;
            if (StringHelper.isNotEmpty(groupId)
                && (groupUserRelList != null && groupUserRelList.size() > 0)) {
                save = mainTainGroupService.addGroupMembers(groupId, groupUserRelList);
            }
            if (save) {
                ajaxResult.setResult(true);
                ajaxResult.setInfo("添加成功");
                ajaxReturn(ajaxResult, response);
            } else {
                ajaxResult.setResult(false);
                ajaxResult.setInfo("添加失败，请重新添加或联系管理员");
                ajaxReturn(ajaxResult, response);
            }
        } catch (Exception e) {
            logger.error("发送消息页面添加群组成员时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("添加群组成员出现系统异常");
            ajaxReturn(ajaxResult, response);
        }
    }

    /**
     * 选择收件人页面，点击“添加群组”按钮时，用UUID产生群组ID
     *
     * @param request
     * @param response
     */
    @RequestMapping("/user/createNewGroup.ajax")
    public void createNewGroup(HttpServletRequest request, HttpServletResponse response) {
        String groupName = request.getParameter("groupName");
        String userId = SecurityContextHolder.getContext().getPrincipal().getUserId();
        if (StringHelper.isNotEmpty(groupName)) {
            if (!mainTainGroupService.existGroup(groupName, userId, null)) {
                try {
                    UmsGroup umsGroup = mainTainGroupService.saveGroup(userId, groupName);
                    ajaxReturn(umsGroup, response);
                } catch (Exception e) {
                    logger.error("群组添加异常", e);
                    ajaxReturn("error", response);
                }
            } else {
                ajaxReturn("exist", response);
            }
        }
    }

    /**
     * 修改群组名称</br>
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/user/updateGroupName.ajax")
    public void doUpdateGroupName(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");// 群组主键ID
        String groupName = request.getParameter("groupName");// 群组名称
        AjaxResult ajaxResult = new AjaxResult();
        try {
            groupName = StringHelper.trim(groupName);
            String userId = OperationContextHolder.getPrincipal().getUserId();
            if (mainTainGroupService.existGroup(groupName, userId, groupId)) {
                ajaxResult.setResult(false);
                ajaxResult.setInfo("groupNameExist");
                ajaxReturn(ajaxResult, response);
            } else {
                boolean save = mainTainGroupService.updateGroupName(groupId, userId, groupName);// 更新群组名称
                if (save) {
                    ajaxResult.setResult(true);
                    ajaxResult.setInfo("更新成功");
                    ajaxReturn(ajaxResult, response);
                } else {
                    ajaxResult.setResult(false);
                    ajaxResult.setInfo("更新失败，请重新更新或联系管理员");
                    ajaxReturn(ajaxResult, response);
                }
            }
        } catch (Exception e) {
            logger.error("发送消息页面更新群组名称时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("更新群组名称时出现系统异常");
            ajaxReturn(ajaxResult, response);
        }
    }

    @RequestMapping(value = "/user/delGroup.ajax")
    public void deleteGroupAjax(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");// 群组主键ID
        AjaxResult ajaxResult = new AjaxResult();
        try {
            mainTainGroupService.deleteGroup(groupId);
            ajaxResult.setResult(true);
            ajaxResult.setInfo("删除成功");
            ajaxReturn(ajaxResult, response);
        } catch (Exception e) {
            logger.error("删除群组时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("error");
            ajaxReturn(ajaxResult, response);
        }
    }

    @RequestMapping(value = "/user/delGroupMember.ajax")
    public void delGroupMember(HttpServletRequest request, HttpServletResponse response) {
        String groupId = request.getParameter("groupId");// 群组主键ID
        String memberId = request.getParameter("memberId");// 当前所选群组成员ID
        AjaxResult ajaxResult = new AjaxResult();
        try {
            mainTainGroupService.deleteGroupMember(groupId, memberId);
            ajaxResult.setResult(true);
            ajaxResult.setInfo("删除成功");
            ajaxReturn(ajaxResult, response);
        } catch (Exception e) {
            logger.error("删除群组成员时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("error");
            ajaxReturn(ajaxResult, response);
        }
    }

    @RequestMapping(value = "/user/delBatchGroup.ajax")
    public void delBatchGroup(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("idStr");// 群组主键ID或群组成员ID拼成的字符串，用逗号分隔
        AjaxResult ajaxResult = new AjaxResult();
        try {
            boolean res = mainTainGroupService.deleteGroupMember(idStr);
            if (res) {
                ajaxResult.setResult(true);
                ajaxResult.setInfo("批量删除成功");
                ajaxReturn(ajaxResult, response);
            } else {
                ajaxResult.setResult(false);
                ajaxResult.setInfo("批量删除失败");
                ajaxReturn(ajaxResult, response);
            }
        } catch (Exception e) {
            logger.error("批量删除群组成员时出现系统异常", e);
            ajaxResult.setResult(false);
            ajaxResult.setInfo("error");
            ajaxReturn(ajaxResult, response);
        }
    }

    /**
     *1.导出群组
     *
     * @param request
     * @param response
     * @param modelMap
     */
    @RequestMapping(value = "/user/exportGroup.htm")
    public void exportGroupExcel(HttpServletRequest request, HttpServletResponse response,
                                 ModelMap modelMap) {
        String groupId = request.getParameter("id");
        UmsGroup umsGroup = mainTainGroupService.getById(groupId);
        String path_base = request.getSession().getServletContext().getRealPath("/")
                           + "/GroupOutExcel";
        File file_b = new File(path_base);
        if (!file_b.exists()) {
            file_b.mkdir();
        }
        String path = path_base + "/" + groupId + ".xls";
        File fileOld = new File(path);
        if (fileOld.exists()) {
            fileOld.delete();
        }
        File filenew = new File(path);
        try {
            if (!filenew.exists()) {
                filenew.createNewFile();
            }
            mainTainGroupService.exportExcel(groupId, path);
            // 输出
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-Excel;charset=utf-8");
            String groupName = umsGroup.getGroupName();
            String fileName = new String(groupName.getBytes("GBK"), "iso-8859-1") + ".xls";
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            File file = new File(path);
            logger.debug(file.getAbsolutePath());
            InputStream inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            inputStream.close();
            os.close();
        } catch (FileNotFoundException e) {
            logger.error("群组成员导出表文件没有找到：" + e);
        } catch (IOException e) {
            logger.error("群组成员导出表IO读写异常：" + e);
        } finally {
            filenew.delete();
        }
    }

    @RequestMapping(value = "/user/importGroup.htm", method = RequestMethod.POST)
    public String importGroupExcel(GroupForm groupForm, HttpServletRequest request,
                                   HttpServletResponse response, ModelMap modelMap) {
        String groupId = request.getParameter("groupId");
        if (!StringUtils.hasText(groupId)) {
            addErrorMsg(modelMap, "请选择一个群组！");
            PageResult<UmsGroup> result = getPageResult(groupForm);
            modelMap.addAttribute("result", result);
            modelMap.addAttribute("groupForm", groupForm);
            return "user/group/group.vm";
        }
        String type = request.getParameter("type");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("fileAdd");
        try {
            if (file.getSize() > FILE_MAX_SIZE) {
                logger.info("文件太大，文件大小:" + file.getSize() / 1024 + "K");
                //                response
                //                    .sendRedirect(VelocityToolboxView.getReqHost(request) + "/user/mygroup.htm");
                addErrorMsg(modelMap, "文件太大，请分批导入！");
                PageResult<UmsGroup> result = getPageResult(groupForm);
                modelMap.addAttribute("result", result);
                modelMap.addAttribute("groupForm", groupForm);
                return "user/group/group.vm";
            }
            mainTainGroupService.importGroupExcel(groupId, file, type);
            addErrorMsg(modelMap, "导入成功！");
            PageResult<UmsGroup> result = getPageResult(groupForm);
            modelMap.addAttribute("result", result);
            modelMap.addAttribute("groupForm", groupForm);
            return "user/group/group.vm";
        } catch (IOException e) {
            logger.error("导入群组，IO错误！", e);
            addErrorMsg(modelMap, "导入群组出错！");
            PageResult<UmsGroup> result = getPageResult(groupForm);
            modelMap.addAttribute("result", result);
            modelMap.addAttribute("groupForm", groupForm);
            return "user/group/group.vm";
        }
    }
}
