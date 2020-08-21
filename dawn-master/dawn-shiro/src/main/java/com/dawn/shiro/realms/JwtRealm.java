package com.dawn.shiro.realms;

import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.common.utils.http.ServletUtils;
import com.dawn.shiro.service.TokenService;
import com.dawn.shiro.token.JwtToken;
import com.dawn.shiro.web.domain.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * ---------------------------
 * token 登录Realm (JwtRealm)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 添加角色
        authorizationInfo.addRoles(loginUser.getRoleSet());
        // 添加权限
        authorizationInfo.addStringPermissions(loginUser.getPermissionsSet());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 获得userName
        String userName = tokenService.getPhone(token);
        log.info(userName + " - token auth start...");
        if (StringUtils.isEmpty(userName)) {
            throw new IncorrectCredentialsException();
        }
        User user = userService.findByUserName(userName);
        if (user == null) {
            throw new IncorrectCredentialsException();
        }
        try{
            boolean verify = tokenService.verify(token, user.getPassword());
            if(verify == Boolean.FALSE){
                throw new IncorrectCredentialsException();
            }
        } catch (Exception e){
            throw new IncorrectCredentialsException();
        }
        return new SimpleAuthenticationInfo(token, token, "JwtRealm");
    }
}
