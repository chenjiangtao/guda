package net.zoneland.ums.common.dal.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class UmsBlackList {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UMS_BLACK_LIST.ID
     *
     * @mbggenerated
     */
    private String id;
    private String appId;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UMS_BLACK_LIST.USER_ID
     *
     * @mbggenerated
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UMS_BLACK_LIST.GMT_CREATED
     *
     * @mbggenerated
     */
    private Date   gmtCreated;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column UMS_BLACK_LIST.GMT_MODIFIED
     *
     * @mbggenerated
     */
    private Date   gmtModified;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UMS_BLACK_LIST.ID
     *
     * @return the value of UMS_BLACK_LIST.ID
     *
     * @mbggenerated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UMS_BLACK_LIST.ID
     *
     * @param id the value for UMS_BLACK_LIST.ID
     *
     * @mbggenerated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UMS_BLACK_LIST.USER_ID
     *
     * @return the value of UMS_BLACK_LIST.USER_ID
     *
     * @mbggenerated
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UMS_BLACK_LIST.USER_ID
     *
     * @param userId the value for UMS_BLACK_LIST.USER_ID
     *
     * @mbggenerated
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UMS_BLACK_LIST.GMT_CREATED
     *
     * @return the value of UMS_BLACK_LIST.GMT_CREATED
     *
     * @mbggenerated
     */
    public Date getGmtCreated() {
        return gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UMS_BLACK_LIST.GMT_CREATED
     *
     * @param gmtCreated the value for UMS_BLACK_LIST.GMT_CREATED
     *
     * @mbggenerated
     */
    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column UMS_BLACK_LIST.GMT_MODIFIED
     *
     * @return the value of UMS_BLACK_LIST.GMT_MODIFIED
     *
     * @mbggenerated
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column UMS_BLACK_LIST.GMT_MODIFIED
     *
     * @param gmtModified the value for UMS_BLACK_LIST.GMT_MODIFIED
     *
     * @mbggenerated
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}