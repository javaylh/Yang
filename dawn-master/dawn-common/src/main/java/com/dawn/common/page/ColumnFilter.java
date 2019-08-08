package com.dawn.common.page;

import lombok.Data;

/**
 * ---------------------------
 * 分页查询列过滤器 (ColumnFilter)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-07 16:20:00
 * ---------------------------
 */
@Data
public class ColumnFilter {

    /** 过滤列名 */
    private String name;

    /** 查询的值 */
    private String value;

}
