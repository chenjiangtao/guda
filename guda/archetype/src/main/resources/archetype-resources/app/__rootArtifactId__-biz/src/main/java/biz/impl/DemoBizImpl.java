#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ${package}.biz.DemoBiz;
import ${package}.dao.DemoDOM${parentArtifactId}er;
import ${package}.dao.domain.DemoDO;
import ${package}.dao.domain.DemoDOCriteria;
import ${groupId}.tools.web.page.BaseQuery;
import ${groupId}.tools.web.page.BizResult;

public class DemoBizImpl implements DemoBiz{

    private final static Logger logger = LoggerFactory.getLogger(DemoBizImpl.class);

    @Autowired
    private DemoDOM${parentArtifactId}er demoDOM${parentArtifactId}er;

    public BizResult detail(int id) {
        BizResult bizResult = new BizResult();
        try{
            DemoDO demoDO = demoDOM${parentArtifactId}er.selectByPrimaryKey(id);
            bizResult.data.put("demoDO", demoDO);
            bizResult.success = true;
        }catch(Exception e){
            logger.error("query Demo error",e);
        }
        return bizResult;
}

    public BizResult list(BaseQuery baseQuery) {
        BizResult bizResult = new BizResult();
        try {
            DemoDOCriteria demoDOCriteria = new DemoDOCriteria();
            demoDOCriteria.setStartRow(baseQuery.getStartRow());
            demoDOCriteria.setPageSize(baseQuery.getPageSize());
            int totalCount = demoDOM${parentArtifactId}er.countByExample(demoDOCriteria);
            baseQuery.setTotalCount(totalCount);
            List<DemoDO> demoDOList = demoDOM${parentArtifactId}er.selectByExample(demoDOCriteria);
            bizResult.data.put("demoDOList", demoDOList);
            bizResult.data.put("query", baseQuery);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("view Demo list error", e);
        }
            return bizResult;
     }

    public BizResult delete(int id) {
        BizResult bizResult = new BizResult();
        try {
            demoDOM${parentArtifactId}er.deleteByPrimaryKey(id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("delete demo error", e);
        }
        return bizResult;
    }

    public BizResult create(DemoDO demoDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = demoDOM${parentArtifactId}er.insert(demoDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("create Demo error", e);
        }
        return bizResult;
    }

    public BizResult update(DemoDO demoDO) {
        BizResult bizResult = new BizResult();
        try {
            int id = demoDOM${parentArtifactId}er.updateByPrimaryKeySelective(demoDO);
            bizResult.data.put("id", id);
            bizResult.success = true;
        } catch (Exception e) {
            logger.error("update Demo error", e);
        }
        return bizResult;
    }

    }
