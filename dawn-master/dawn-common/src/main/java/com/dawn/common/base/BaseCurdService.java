package com.dawn.common.base;

import java.util.List;

/**
 * ---------------------------
 * 通用CURD接口 (BaseCurdService<T>)
 * ---------------------------
 * @author： ylh
 * 时间： 2019-08-07 16:20:00
 * ---------------------------
 */
public interface BaseCurdService<T> {

    /**
     * 保存操作
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 删除操作
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除操作
     * @param records
     * @return 删除的数量
     */
    int delete(List<T> records);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    T findById(Integer id);

}