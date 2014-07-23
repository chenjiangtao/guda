/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2013 All Rights Reserved.
 */
package com.foodoon.monitor.web.home.warn;

import com.foodoon.monitor.dal.dataobject.Warn;
import com.foodoon.monitor.web.home.form.GarForm;

/**
 * 
 * @author foodoon
 * @version $Id: WarnService.java, v 0.1 2013-6-4 下午1:53:36 foodoon Exp $
 */
public interface WarnService {

    void warn(GarForm form, Warn warn);

}
