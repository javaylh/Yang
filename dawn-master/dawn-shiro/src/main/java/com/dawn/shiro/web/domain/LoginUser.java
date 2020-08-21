package com.dawn.shiro.web.domain;

import com.dawn.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * ---------------------------
 * 登录用户 (LoginUser)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Data
@NoArgsConstructor
public class LoginUser {

    /**
     * 角色列表
     */
    private Set<String> roleSet;

    /**
     * 权限列表
     */
    private Set<String> permissionsSet;

    /**
     * User
     */
    private User user;

}
