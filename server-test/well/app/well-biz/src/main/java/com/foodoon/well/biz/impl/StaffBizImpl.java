package com.foodoon.well.biz.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.foodoon.well.biz.StaffBiz;
import com.foodoon.well.dao.StaffDOMapper;
import com.foodoon.well.dao.domain.StaffDO;
import com.foodoon.well.dao.domain.StaffDOCriteria;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;

public class StaffBizImpl implements StaffBiz{

    private final static Logger logger = LoggerFactory.getLogger(StaffBizImpl.class);

    @Autowired
    private StaffDOMapper staffDOMapper;

    public BizResult detail(int id) {
        BizResult bizResult = new BizResult();
        try{
            StaffDO staffDO = staffDOMapper.selectByPrimaryKey(id);
            bizResult.data.put("staffDO", staffDO);
            bizResult.success = true;
        }catch(Exception e){
            logger.error("query Staff error",e);
        }
        return bizResult;
}

    public BizResult list(BaseQuery baseQuery) {
        BizResult bizResult = new BizResult();
        try {
            StaffDOCriteria staffDOCriteria = new StaffDOCriteria();
            staffDOCriteria.setLimitStart(baseQuery.getStartRow());
            staffDOCriteria.setLimitEnd(baseQuery.getPageSize());
            int totalCount = staffDOMapper.countByExample(staffDOCriteria);
            baseQuery.setTotalCount(totalCount);
            List<StaffDO> staffDOList = staffDOMapper.selectByExample(staffDOCriteria);
            bizResult.data.put("staffDOList", staffDOList);
            bizResult.data.put("query", baseQuery);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("view Staff list error", e);
        }
            return bizResult;
     }

    public BizResult delete(int id) {
        BizResult bizResult = new BizResult();
        try {
            staffDOMapper.deleteByPrimaryKey(id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("delete staff error", e);
        }
        return bizResult;
    }

    public BizResult create(StaffDO staffDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = staffDOMapper.insert(staffDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("create Staff error", e);
        }
        return bizResult;
    }

    public BizResult update(StaffDO staffDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = staffDOMapper.updateByPrimaryKeySelective(staffDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("update Staff error", e);
        }
        return bizResult;
    }

    }
