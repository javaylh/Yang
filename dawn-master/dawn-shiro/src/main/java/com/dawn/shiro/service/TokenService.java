package com.dawn.shiro.service;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dawn.common.service.RedisService;
import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.shiro.common.constant.Constant;
import com.dawn.shiro.common.constant.RedisKey;
import com.dawn.shiro.web.domain.LoginUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ---------------------------
 * Token服务 (TokenService)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Service
public class TokenService {

    @Resource
    private UserService userService;

//    @Resource
//    private PermissionsService permissionsService;

    @Resource
    private RedisService redisService;

    /**
     * 获取当前登录的User对象
     * @return User
     */
    public LoginUser getLoginUser(HttpServletRequest request){
        // 获取token
        String token = getToken(request);
        // 获取手机号
        String phone = getPhone(token);
        // 获取缓存loginUserKey
        String loginUserKey = RedisKey.getLoginUserKey(phone);
        // 获取缓存loginUser
        Object cacheObject = redisService.getValue(loginUserKey);
        if (cacheObject == null) {
            LoginUser loginUser = new LoginUser();
            // 获取当前登录用户
            User user = userService.findByUserPhone(phone);
            loginUser.setUser(user);
            // 获取当前登录用户所有权限
            String[] authArr = user.getAuth().split(",");
            Set<String> permissionsSet = new HashSet<>(Arrays.asList(authArr));
            loginUser.setPermissionsSet(permissionsSet);
            // 获取当前登录用户所有角色
            String[] roleArr = user.getRoleId().split(",");
            Set<String> roleSet = new HashSet<>(Arrays.asList(roleArr));
            loginUser.setRoleSet(roleSet);
            // 缓存当前登录用户 15分钟
            redisService.setValue(loginUserKey, loginUser, 15 * 60);
            return loginUser;
        }
        return (LoginUser) cacheObject;
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token token
     * @return token中包含的用户手机号
     */
    public String getPhone(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param request HttpServletRequest
     * @return token中包含的用户手机号
     */
    public String getPhone(HttpServletRequest request) {
        try {
            DecodedJWT jwt = JWT.decode(this.getToken(request));
            return jwt.getClaim("userName").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token token
     * @return token中包含的用户id
     */
    public String getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param request HttpServletRequest
     * @return token中包含的用户id
     */
    public String getUserId(HttpServletRequest request) {
        try {
            DecodedJWT jwt = JWT.decode(this.getToken(request));
            return jwt.getClaim("userId").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获取当前登录用户的token
     * @param request HttpServletRequest
     * @return token
     */
    public String getToken(HttpServletRequest request){
        return request.getHeader(Constant.TOKEN_HEADER_NAME);
    }


    /**
     *
     * @param userName 用户名/手机号
     * @param userId   用户id
     * @param secret   用户的密码
     * @return 加密的token
     */
    public String createToken(String userName, Long userId, String secret) {
        Date date = DateUtil.offsetHour(DateUtil.date(), Constant.TOKEN_EXPIRE_TIME_HOUR);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim("userName", userName)
                .withClaim("userId", String.valueOf(userId))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public boolean verify(String token, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", getPhone(token))
                    .withClaim("userId", getUserId(token))
                    .build();
            // 效验TOKEN
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }

}
