package com.foodoon.demo.biz.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.foodoon.demo.biz.OrgBiz;
import com.foodoon.demo.dao.OrgDOMapper;
import com.foodoon.demo.dao.domain.OrgDO;
import com.foodoon.demo.dao.domain.OrgDOCriteria;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;

public class OrgBizImpl implements OrgBiz{

    private final static Logger logger = LoggerFactory.getLogger(OrgBizImpl.class);

    @Autowired
    private OrgDOMapper orgDOMapper;

    public BizResult detail(int id) {
        BizResult bizResult = new BizResult();
        try{
            OrgDO orgDO = orgDOMapper.selectByPrimaryKey(id);
            bizResult.data.put("orgDO", orgDO);
            bizResult.success = true;
        }catch(Exception e){
            logger.error("query Org error",e);
        }
        return bizResult;
}

    public BizResult list(BaseQuery baseQuery) {
        BizResult bizResult = new BizResult();
        try {
            OrgDOCriteria orgDOCriteria = new OrgDOCriteria();
            orgDOCriteria.setLimitStart(baseQuery.getStartRow());
            orgDOCriteria.setLimitEnd(baseQuery.getPageSize());
            int totalCount = orgDOMapper.countByExample(orgDOCriteria);
            baseQuery.setTotalCount(totalCount);
            List<OrgDO> orgDOList = orgDOMapper.selectByExample(orgDOCriteria);
            bizResult.data.put("orgDOList", orgDOList);
            bizResult.data.put("query", baseQuery);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("view Org list error", e);
        }
            return bizResult;
     }

    public BizResult delete(int id) {
        BizResult bizResult = new BizResult();
        try {
            orgDOMapper.deleteByPrimaryKey(id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("delete org error", e);
        }
        return bizResult;
    }

    public BizResult create(OrgDO orgDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = orgDOMapper.insert(orgDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("create Org error", e);
        }
        return bizResult;
    }

    public BizResult update(OrgDO orgDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = orgDOMapper.updateByPrimaryKeySelective(orgDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("update Org error", e);
        }
        return bizResult;
    }

    }
