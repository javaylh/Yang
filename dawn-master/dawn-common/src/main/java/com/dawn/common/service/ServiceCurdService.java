package com.dawn.common.service;

import com.dawn.common.page.PageRequest;
import com.dawn.common.page.PageResult;

import java.util.List;

/**
 * 通用CURD接口
 *
 * @author chenwei
 */
public interface ServiceCurdService<T> {

    /**
     * 保存操作
     *
     * @param record
     * @return
     */
    int save(T record);

    /**
     * 删除操作
     *
     * @param record
     * @return
     */
    int delete(T record);

    /**
     * 批量删除操作
     *
     * @param records
     * @return 删除的数量
     */
    int delete(List<T> records);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    T findById(Integer id);

    /**
     * 分页查询
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     *
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);

}