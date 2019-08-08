package com.dawn.common.page;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------
 * 分页请求 (PageRequest)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-07 16:26:00
 * ---------------------------
 */
@Data
public class PageRequest {

    /** 当前页码 */
    private int pageNum = 1;

    /** 每页数量 */
    private int pageSize = 10;

    /** 每页数量 */
    private Map<String, ColumnFilter> columnFilters = new HashMap<String, ColumnFilter>();

    private Map param = new HashMap();

    public ColumnFilter getColumnFilter(String name) {
        return columnFilters.get(name);
    }

}
