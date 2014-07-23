/**
 *
 */
package net.zoneland.ums.biz.config.admin.bo;

import net.zoneland.ums.common.dal.dataobject.UmsAppInfo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author louguodong
 *
 */
public class AppInfoBO {

    private UmsAppInfo appInfo;

    private String     dayflow;

    private String     monthflow;

    public UmsAppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(UmsAppInfo appInfo) {
        this.appInfo = appInfo;
    }

    public String getDayflow() {
        return dayflow;
    }

    public void setDayflow(String dayflow) {
        this.dayflow = dayflow;
    }

    public String getMonthflow() {
        return monthflow;
    }

    public void setMonthflow(String monthflow) {
        this.monthflow = monthflow;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
