package com.dawn.common.enums;

/**
 * ---------------------------
 * 季节 (SeasonEnum)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-09 17:20:00
 * ---------------------------
 */
public enum SeasonEnum {

    /** 春 */
    SPRING(1,"春"),

    /** 夏 */
    SUMMER(2,"夏"),

    /** 秋 */
    AUTUMN(3,"秋"),

    /** 冬 */
    WINTER(4,"冬");

    private final int code;
    private final String info;

    SeasonEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

}
