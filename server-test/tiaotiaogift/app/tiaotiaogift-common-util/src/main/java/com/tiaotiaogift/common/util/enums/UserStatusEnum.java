package com.tiaotiaogift.common.util.enums;

public enum UserStatusEnum {
	
	 /**  */
    OK("1", "正常"),
    /**  */
    PHONE_NOT_CHECK("1", "正常"),
    /**  */
    EMAIL_NOT_CHECK("2", "邮箱未验证");

    private String value;
    private String description;

    private UserStatusEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getDescriptionByValue(String value) {
        if (value == null || value.equals("")) {
            return "";
        }
        UserStatusEnum[] values = UserStatusEnum.values();
        for (int i = 0; i < values.length; i++) {
            if (value.equals(values[i].getValue())) {
                return values[i].getDescription();
            }
        }
        return "";
    }

}
