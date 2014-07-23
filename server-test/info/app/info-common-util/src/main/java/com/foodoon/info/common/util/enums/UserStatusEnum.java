package com.foodoon.info.common.util.enums;

public enum UserStatusEnum {

    /**  */
    OK(1, "正常"),
    /**  */
    PHONE_NOT_CHECK(2, "手机未校验"),
    /**  */
    EMAIL_NOT_CHECK(3, "邮箱未验证");

    private int    value;
    private String description;

    private UserStatusEnum(int value, String description){
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
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
