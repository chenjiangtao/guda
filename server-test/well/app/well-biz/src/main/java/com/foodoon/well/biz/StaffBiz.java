package com.foodoon.well.biz;

import com.foodoon.well.dao.domain.StaffDO;
import com.foodoon.tools.web.page.BaseQuery;
import com.foodoon.tools.web.page.BizResult;

public interface StaffBiz {

        BizResult detail(int id);

        BizResult list(BaseQuery baseQuery);

        BizResult delete(int id);

        BizResult create(StaffDO staffDO);

        BizResult update(StaffDO staffDO);

}
