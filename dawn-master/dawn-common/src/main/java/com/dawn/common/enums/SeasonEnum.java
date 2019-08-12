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
    SPRING(1),

    /** 夏 */
    SUMMER(2),

    /** 秋 */
    AUTUMN(3),

    /** 冬 */
    WINTER(4);

    public int seq;

    SeasonEnum(int seq) { this.seq = seq; }

}
