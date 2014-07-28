#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import ${package}.dao.domain.DemoDO;
import ${package}.dao.domain.DemoDOCriteria;
import java.util.List;

public interface DemoDOM${parentArtifactId}er {
    int countByExample(DemoDOCriteria example);

    int deleteByPrimaryKey(Integer id);

    int insert(DemoDO record);

    int insertSelective(DemoDO record);

    List<DemoDO> selectByExample(DemoDOCriteria example);

    DemoDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DemoDO record);

    int updateByPrimaryKey(DemoDO record);
}