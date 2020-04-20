//package com.dawn.upms.config;
//
//import com.dawn.model.User;
//import com.dawn.service.UserService;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * ---------------------------
// * 配置Shiro验证，以及过滤条件 (ShiroRealmConfig)
// * ---------------------------
// * @author ylh
// * @date 2019-10-18 15:30:00
// * ---------------------------
// */
//public class ShiroRealmConfig extends AuthorizingRealm {
//
//    @Autowired
//    private UserService userService;
//
//    /**
//     * 角色权限和对应权限添加
//     * @param principalCollection
//     */
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        // 获取登录用户名
//        String name = (String) principalCollection.getPrimaryPrincipal();
//        // 查询用户名称
//        User user = loginService.findByName(name);
//        //添加角色和权限
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        for (Role role:user.getRoles()) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(role.getRoleName());
//            for (Permission permission:role.getPermissions()) {
//                //添加权限
//                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
//            }
//        }
//        return authorizationInfo;
//    }
//
//    //用户认证
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
//        if (authenticationToken.getPrincipal() == null) {
//            return null;
//        }
//        //获取用户信息
//        String name = authenticationToken.getPrincipal().toString();
//        User user = loginService.findByName(name);
//        if (user == null) {
//            //这里返回后会报出对应异常
//            return null;
//        } else {
//            //这里验证authenticationToken和simpleAuthenticationInfo的信息
//            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
//            return simpleAuthenticationInfo;
//        }
//    }
//
//}
