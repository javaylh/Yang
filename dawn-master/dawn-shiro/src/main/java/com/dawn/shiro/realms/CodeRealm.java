package com.dawn.shiro.realms;

import com.dawn.common.service.RedisService;
import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.shiro.common.constant.RedisKey;
import com.dawn.shiro.token.CustomizedToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * ---------------------------
 * 验证码登录Realm (CodeRealm)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Slf4j
public class CodeRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }

    /**
     * 获取授权信息
     * @param principals principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 获取身份认证信息
     * @param auth authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        CustomizedToken token = (CustomizedToken) auth;
        log.info(token.getUsername() + " - code auth start...");
        // 根据用户名查询用户
        User user = userService.findByUserName(token.getUsername());
        if (user == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        // 1.principal：认证的实体信息，可以是手机号，也可以是数据表对应的用户的实体类对象
        // 2.从redis中获取登录验证码
        Object credentials = redisService.getValue(RedisKey.getLoginCodeKey(user.getPhone()));
        if (credentials == null) {
            throw new ExpiredCredentialsException();
        }
        // 3.realmName：当前realm对象的name，调用父类的getName()方法即可
        String realmName = super.getName();
        // 4.盐,取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getPhone());
        return new SimpleAuthenticationInfo(user, credentials, credentialsSalt, realmName);
    }
}
