package com.foodoon.demo.biz;

import com.foodoon.demo.dao.domain.OrgDO;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;

public interface OrgBiz {

        BizResult detail(int id);

        BizResult list(BaseQuery baseQuery);

        BizResult delete(int id);

        BizResult create(OrgDO orgDO);

        BizResult update(OrgDO orgDO);

}
