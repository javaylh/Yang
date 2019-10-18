package com.dawn.dao;

import com.dawn.model.User;

import java.util.List;
import java.util.Map;

/**
 * ---------------------------
 * 用户信息 (User)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
public interface UserMapper {

	/**
	 * 添加用户信息
	 * @param record
	 * @return
	 */
    int add(User record);

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    int delete(Long id);
    
    /**
     * 修改用户信息
     * @param record
     * @return
     */
    int update(User record);
    
    /**
     * 根据主键查询
     * @param id
     * @return
     */    
    User findById(Long id);

    /**
     * 基础分页查询
     * @return
     */    
    List<User> findPage();

    /**
    * 获取用户信息列表
    * @param map 查询列表的条件
    * @return
    */
    List<User> findList(Map map);
    
}