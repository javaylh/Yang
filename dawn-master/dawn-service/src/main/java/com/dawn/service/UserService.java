package com.dawn.service;

import com.dawn.common.service.ServiceCurdService;
import com.dawn.model.User;

import java.util.List;
import java.util.Map;

/**
 * ---------------------------
 * 用户信息 (UserService)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 09:30:00
 * ---------------------------
 */
public interface UserService extends ServiceCurdService<User> {

    /**
    * 获取用户信息列表
    * @param map 查询列表的条件
    * @return
    */
    List<User> findList(Map map);

}
