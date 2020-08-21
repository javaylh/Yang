package com.dawn.shiro.service;

import com.dawn.model.User;
import com.dawn.shiro.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ---------------------------
 * 权限服务 (PermissionsService)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Service
public class PermissionsService {

//    @Resource
//    private PermMapper menuMapper;
//
//    @Resource
//    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 根据用户id获取其角色列表
     * @param userId userId
     * @return Set<Role>
     */
    public Set<String> getRoleSet(Long userId) {
        User user = userMapper.findById(userId);
        String[] authArr = user.getRoleId().split(",");
        Set<String> authSet = new HashSet<>(Arrays.asList(authArr));
        return authSet;
    }

    /**
     * 给用户添加角色
     */
    public void addRole(Long userId, Long... roleIds) {
        User user = userMapper.findById(userId);
        StringBuffer stringBuffer = new StringBuffer(user.getRoleId());
        for (Long roleId : roleIds) {
            stringBuffer.append(roleId);
        }
        user.setRoleId(stringBuffer.toString());
        userMapper.update(user);
    }

    /**
     * 根据用户id获取其角色列表
     * @param userId userId
     * @return Set<Role>
     */
    public Set<String> getPermissionsSet(Long userId) {
        User user = userMapper.findById(userId);
        String[] authArr = user.getAuth().split(",");
        Set<String> strings = new HashSet<>(Arrays.asList(authArr));

//        Set<Integer> roleIdSet = roleMapper.getRoleIdSet(userId);
//        Set<String> strings = new HashSet<>();
//        for (Integer roleId : roleIdSet) {
//            Set<String> permissionsSetByRoleId = getPermissionsSetByRoleId(roleId);
//            strings.addAll(permissionsSetByRoleId);
//        }
        // 去除空权限
        strings.remove("");
        return strings;
    }

    /**
     * 根据角色id获取权限列表
     * @param roleId 角色id
     * @return 权限列表
     */
    public Set<String> getPermissionsSetByRoleId(Integer roleId) {
        // TODO return menuMapper.getPermissionsSet(roleId);
        Set<String> strings = new HashSet<>();
        return strings;
    }
}
