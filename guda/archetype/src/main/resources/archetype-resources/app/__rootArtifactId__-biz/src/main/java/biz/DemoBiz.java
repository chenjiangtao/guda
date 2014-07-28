#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.biz;

import ${package}.dao.domain.DemoDO;
import ${groupId}.tools.web.page.BaseQuery;
import ${groupId}.tools.web.page.BizResult;

public interface DemoBiz {

        BizResult detail(int id);

        BizResult list(BaseQuery baseQuery);

        BizResult delete(int id);

        BizResult create(DemoDO demoDO);

        BizResult update(DemoDO demoDO);

}
