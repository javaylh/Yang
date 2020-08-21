package com.dawn.shiro.mapper;

import com.dawn.model.User;

import java.util.List;
import java.util.Map;

/**
 * ---------------------------
 * 系统用户接口 (UserDao)
 * ---------------------------
 * @author： ylh
 * @date： 2019-11-07 14:00:00
 * ---------------------------
 */
public interface UserMapper {

	/**
	 * 添加系统用户
	 * @param record
	 * @return
	 */
    int add(User record);

    /**
     * 删除系统用户
     * @param id
     * @return
     */
    int delete(Long id);
    
    /**
     * 修改系统用户
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
    * 获取系统用户列表
    * @param map 查询列表的条件
    * @return
    */
    List<User> findList(Map map);

    /**
     * 根据用户名查询
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据授权码查询
     * @param authCode
     * @return
     */
    User findByAuthCode(String authCode);

    /**
     * 根据手机号查询
     * @param phone
     * @return
     */
    User findByUserPhone(String phone);
    
}