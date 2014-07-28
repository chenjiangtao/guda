package com.foodoon.well.biz;

import com.foodoon.well.dao.domain.DemoDO;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;

public interface DemoBiz {

        BizResult detail(int id);

        BizResult list(BaseQuery baseQuery);

        BizResult delete(int id);

        BizResult create(DemoDO demoDO);

        BizResult update(DemoDO demoDO);

}
