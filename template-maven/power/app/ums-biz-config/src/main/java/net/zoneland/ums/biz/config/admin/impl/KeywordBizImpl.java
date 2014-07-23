/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package net.zoneland.ums.biz.config.admin.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.zoneland.ums.biz.config.admin.KeywordBiz;
import net.zoneland.ums.biz.config.admin.bo.KeywordBO;
import net.zoneland.ums.common.dal.UmsAppInfoMapper;
import net.zoneland.ums.common.dal.UmsKeywordInfoMapper;
import net.zoneland.ums.common.dal.dataobject.UmsKeywordInfo;
import net.zoneland.ums.common.util.helper.StringHelper;
import net.zoneland.ums.common.util.page.PageResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 关键字业务类
 * @author XuFan
 * @version $Id: UmsKeywordBiz.java, v 0.1 Aug 17, 2012 10:57:48 AM XuFan Exp $
 */

public class KeywordBizImpl implements KeywordBiz {

    private static final Logger  logger = Logger.getLogger(KeywordBizImpl.class);

    @Autowired
    private UmsKeywordInfoMapper umsKeywordInfoMapper;

    @Autowired
    private UmsAppInfoMapper     umsAppInfoMapper;

    /**
     * 条件查询分页显示关键字
     *
     * @param keyword 检索关键字
     * @param appId 检索应用
     * @param curpage 当前页
     * @return
     */
    public PageResult<KeywordBO> searchKeyword(String keyWord, String appId, int curpage) {
        if (logger.isInfoEnabled()) {
            logger.info("开始查询关键字");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(appId)) {// 如果应用不为空
            params.put("appId", appId);
        }
        params.put("keyWord", keyWord);
        int total = umsKeywordInfoMapper.searchAllNum(params);// 返回记录总数
        PageResult<KeywordBO> result = new PageResult<KeywordBO>(total, curpage);
        params.put("first", result.getFirstrecode());
        params.put("end", result.getEndrecode());
        List<UmsKeywordInfo> list = umsKeywordInfoMapper.searchKeywordByPage(params);// 分页显示关键字信息集合
        List<KeywordBO> bos = new ArrayList<KeywordBO>(list.size());
        for (int i = 0; i < list.size(); i++) {
            KeywordBO bo = new KeywordBO();
            bo.setUmsKeywordInfo(list.get(i));
            if (StringUtils.trim(list.get(i).getAppId()).equals("0")) {// 如果所获得的应用ID为0
                bo.setAppName("全部应用");// 把应用名设为“全部应用”
            } else {
                bo.setAppName(StringUtils.trim(umsAppInfoMapper.getAppNameByAppId(list.get(i)
                    .getAppId())));//把appId转换成appName显示
            }
            bos.add(bo);
        }
        result.setResults(bos);
        return result;
    }

    /**
     * 通过id获取关键字
     *
     * @param id
     * @return
     */
    public UmsKeywordInfo getKeywordById(String id) {
        return umsKeywordInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 创建新关键字：<br/>
     *
     * 先判断数据库中是否存在该关键字：<br/>
     * 不存在则插入<br/>
     * 存在则判断以下四种情况：<br/>
     * 1.情况一：当前关键字没有添加到全部应用(即属于个别应用),并且下拉框选择了【全部应用】<br/>
     *   处理：把该关键字相关的个别应用删除再插入一条新数据(这条新数据是把该关键字添加为属于【全部应用】)，<br/>
     *         返回【有关键字由属于个别应用改为属于全部应用.该关键字之前相关的个别应用信息已删除】的提示<br/>
     * 2.情况二：当前关键字没有添加到全部应用(即属于个别应用)，并且下拉框没有选择【全部应用】(即选择了个别应用)，
     *         再判断数据库中该应用是否已经有相同关键字<br/>
     *   处理：(1)该应用中存在该关键字：返回【关键字由于已经存在，保存失败】的提示<br/>
     *         (2)该应用中不存在该关键字：返回【关键字保存成功】的提示<br/>
     * 3.情况三：当前关键字已经添加到了全部应用，并且在全部应用中找到该关键字<br/>
     *   处理：返回【关键字由于已经存在，保存失败】的提示<br/>
     * 4.情况四：当前关键字已经添加到了全部应用，并且在全部应用中没有找到该关键字    <br/>
     *   处理：(1)如果该关键字选择了下拉框【全部应用】，作为新的属于全部应用的关键字保存，返回【关键字保存成功】的提示<br/>
     *         (2)如果该关键字没有选择下拉框【全部应用】(即选择了个别应用)，
     *         则返回【有关键字已经属于全部应用，该关键字保存失败.(若要分配该关键字到个别的应用请先删除该关键字).】的提示<br/>
     *
     * @param umsKeywordInfo
     */
    private int createKeyword(UmsKeywordInfo umsKeywordInfo) {
        boolean belongAllApp = false;
        List<String> keywords = umsKeywordInfoMapper.selectByAppId("0");
        List<UmsKeywordInfo> listkey = umsKeywordInfoMapper.getKeyword(umsKeywordInfo);//判断数据库中是否已经有相同关键字
        List<UmsKeywordInfo> keywordInfo = umsKeywordInfoMapper.getKeywordInfo(umsKeywordInfo);//根据应用和关键字判断数据库中该应用是否已经有相同关键字
        if (listkey != null && listkey.size() > 0) {//有相同的关键字
            if (keywords.size() == 0) {//数据库中没有属于【全部应用】的关键字
                if (StringUtils.trim(umsKeywordInfo.getAppId()).equals("0")) {//下拉框选中的【全部应用】
                    umsKeywordInfoMapper.deleteByKeyWord(StringUtils.trim(umsKeywordInfo
                        .getKeyWord()));//则把该关键字相关的个别应用删除再插入一条属于全部应用的关键字
                    saveNewKeyword(umsKeywordInfo);//插入新关键字
                    return 2;
                }
            }
            if (keywords != null && keywords.size() > 0) {//获得属于【全部应用】的关键字
                for (int i = 0; i < keywords.size(); ++i) {
                    if (StringUtils.trim(keywords.get(i)).equals(
                        StringUtils.trim(umsKeywordInfo.getKeyWord()))) {
                        belongAllApp = true;
                    }
                }
                if (!belongAllApp) {
                    //情况一：当前关键字没有添加到全部应用(即属于个别应用),并且下拉框选择了【全部应用】
                    //如果当前关键字没有添加到全部应用的记录
                    if (StringUtils.trim(umsKeywordInfo.getAppId()).equals("0")) {//下拉框选中的【全部应用】
                        umsKeywordInfoMapper.deleteByKeyWord(StringUtils.trim(umsKeywordInfo
                            .getKeyWord()));//则把该关键字相关的个别应用删除再插入一条属于全部应用的关键字
                        saveNewKeyword(umsKeywordInfo);//插入新关键字
                        return 2;
                    }
                    //情况二：当前关键字没有添加到全部应用(即属于个别应用)，并且下拉框没有选择【全部应用】(即选择了个别应用)
                    if (keywordInfo != null && keywordInfo.size() > 0) {//判断数据库中该应用是否已经有相同关键字
                        return -1;//数据库中有该关键字，提示已存在该关键字
                    }
                    //该应用中不存在相同关键字则作为新关键字插入
                    int insertNum = saveNewKeyword(umsKeywordInfo);//插入新关键字
                    return insertNum;
                }
                //情况三：当前关键字已经添加到了全部应用，并且在全部应用中找到该关键字
                if (keywordInfo != null && keywordInfo.size() > 0) {//判断数据库中该应用是否已经有相同关键字
                    return -1;//数据库中有该关键字，提示已存在该关键字
                }
                //情况四：当前关键字已经添加到了全部应用，并且在全部应用中没有找到该关键字
                //如果该关键字选择了下拉框【全部应用】
                if (StringUtils.trim(umsKeywordInfo.getAppId()).equals("0")) {
                    int insertNum = saveNewKeyword(umsKeywordInfo);//插入新关键字
                    return insertNum;
                }
                //如果该关键字没有选择下拉框【全部应用】，即选择了个别应用
                return 0;
            }
            //如果该关键字中不是属于【全部应用】的关键字
            if (keywordInfo != null && keywordInfo.size() > 0) {//判断数据库中该应用是否已经有相同关键字
                return -1;//数据库中有该关键字，提示已存在该关键字
            }
            //如果该关键字中不是属于【全部应用】的关键字，且数据库在所选的该应用下没有该关键字
            int insertNum = saveNewKeyword(umsKeywordInfo);//插入新关键字
            return insertNum;
        } else {//数据库中没有相同关键字则插入一条新记录
            int insertNum = saveNewKeyword(umsKeywordInfo);//插入新关键字
            return insertNum;
        }
    }

    /**
     * 往数据库插入新的关键字
     *
     * @param umsKeywordInfo
     * @return
     */
    private int saveNewKeyword(UmsKeywordInfo umsKeywordInfo) {
        umsKeywordInfo.setId(UUID.randomUUID().toString());
        umsKeywordInfo.setGmtCreated(new Date());
        umsKeywordInfo.setGmtModified(new Date());
        int insertNum = umsKeywordInfoMapper.insertSelective(umsKeywordInfo);//插入新关键字
        return insertNum;
    }

    /**
     * 根据id删除关键字
     *
     * @param id
     */
    public void deleteKeyword(String id) {
        int deleteNum = umsKeywordInfoMapper.deleteByPrimaryKey(id);
        if (logger.isDebugEnabled()) {
            logger.debug("删除全部的关键字,返回：" + deleteNum);
        }
    }

    /**
     * 查询消息内容中是否包含关键词
     *
     * @see net.zoneland.ums.biz.config.admin.KeywordBiz#includeKeyword(java.lang.String, java.lang.String)
     */
    public String includeKeyword(String msgContent, String appId) {
        if (msgContent == null) {
            return "";
        }
        if (appId != null) {
            List<String> list = umsKeywordInfoMapper.selectByAppId(appId);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                String key = it.next();
                if (msgContent.indexOf(key) > -1) {
                    return key;
                }
            }

        }
        List<String> list = umsKeywordInfoMapper.selectByAppId("0");
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (msgContent.indexOf(key) > -1) {
                return key;
            }
        }
        return "";
    }

    /**
     * 保存关键字
     *
     * @see net.zoneland.ums.biz.config.admin.KeywordBiz#saveKeyword(java.lang.String, java.lang.String[])
     */
    public String saveKeyword(String appId, String[] keywords) {
        int count = 0;
        int error = 0;
        int existError = 0;
        boolean overMaxLength = false;//判断是否有关键字超过最大长度
        boolean isAllApp = false;//判断该关键字是否已经添加了全部应用
        boolean toAllApp = false;//判断该关键字是否从个别应用变为属于全部应用
        String keywordsList = "";//保存成功的关键字集合
        StringBuilder buf = new StringBuilder();
        for (int i = 0, len = keywords.length; i < len; ++i) {
            UmsKeywordInfo umsKeywordInfo = new UmsKeywordInfo();
            if (StringHelper.getStringLength(keywords[i]) > 36) {//某个关键字长度大于12个汉字，即36个字符
                overMaxLength = true;
                continue;//不保存此关键字
            }
            umsKeywordInfo.setAppId(appId);
            umsKeywordInfo.setKeyWord(StringUtils.trim(keywords[i]));
            int res = createKeyword(umsKeywordInfo);
            if (res == 1) {//成功插入该关键字
                ++count;
                keywordsList += keywords[i] + ",";
                if (count % 2 == 0) {
                    keywordsList += "<br/>";
                }
            } else if (res == -1) {//已经存在该关键字
                ++existError;
            } else if (res == 2) {//由属于个别应用改为属于全部应用
                ++count;
                keywordsList += keywords[i] + ",";
                if (count % 2 == 0) {
                    keywordsList += "<br/>";
                }
                toAllApp = true;
            } else if (res == 0) {//有关键字已经属于全部应用
                isAllApp = true;
            } else {
                ++error;
            }
        }
        if (isAllApp) {
            buf.append("有关键字已经属于全部应用，该关键字保存失败.(若要分配该关键字到个别的应用请先删除该关键字).<br/>");
        }
        if (toAllApp) {
            buf.append("有关键字由属于个别应用改为属于全部应用.该关键字之前相关的个别应用信息已删除.<br/>");
        }
        if (overMaxLength) {
            buf.append("有关键字超过长度，该关键字保存失败.<br/>(添加关键字规则：长度限制36个，其中一个文字占3个，一个数字占1个，一个字母占1个).<br/>");
        }
        if (count > 0) {
            buf.append("关键字：<br/>");
            if (keywordsList.endsWith("<br/>")) {
                keywordsList = keywordsList.substring(0, keywordsList.lastIndexOf(","));
                keywordsList = keywordsList + ".";
            }
            if (keywordsList.endsWith(",")) {
                keywordsList = keywordsList.substring(0, keywordsList.lastIndexOf(","));
                keywordsList = keywordsList + ".";
            }
            buf.append(keywordsList);
        }
        buf.append("<br/>").append(count).append("个关键字保存成功.");
        if (existError > 0) {
            buf.append(existError).append("个关键字由于已经存在，保存失败.");
        }
        if (error > 0) {
            buf.append(error).append("个关键字保存失败.");
        }

        return buf.toString();
    }

    /**
     * 根据AppId删除关键字
     *
     * @param appId
     * @return
     */
    public boolean deleteKeywordByAppId(String appId) {
        if (StringHelper.isEmpty(appId)) {
            return false;
        }
        umsKeywordInfoMapper.deleteKeywordByAppId(appId);
        return true;
    }
}
