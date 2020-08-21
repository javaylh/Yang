package com.dawn.shiro.service;

import com.dawn.common.base.BaseResult;
import com.dawn.common.enums.ErrorCodeEnum;
import com.dawn.common.service.RedisService;
import com.dawn.model.User;
import com.dawn.service.UserService;
import com.dawn.shiro.common.constant.Constant;
import com.dawn.shiro.common.constant.RedisKey;
import com.dawn.shiro.common.enums.LoginType;
import com.dawn.shiro.common.utils.CommonsUtils;
import com.dawn.shiro.token.CustomizedToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ---------------------------
 * 登录服务 (LoginService)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Service
public class LoginService {

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisCache;

    @Resource
    private TokenService tokenService;


    public boolean sendLoginCode(String phone) throws Exception {
        // 这里使用默认值，随机验证码的方法为CommonsUtils.getCode()
        int code = 6666;
        // todo 此处为发送验证码代码
        //        SmsUtils.sendSms(phone, code);
        // 将验证码加密后存储到redis中
        String encryptCode = CommonsUtils.encryptPassword(String.valueOf(code), phone);
        redisCache.setValue(RedisKey.getLoginCodeKey(phone), encryptCode, Constant.CODE_EXPIRE_TIME);
        return true;
    }


    public boolean sendModifyPasswordCode(String phone) throws Exception {
        int code = 6666;
        // todo 此处为发送验证码代码
//        SmsUtils.sendSms(phone, code);
        redisCache.setValue(RedisKey.getModifyPasswordCodeKey(phone), code, Constant.CODE_EXPIRE_TIME);
        return true;
    }

    public BaseResult loginByPassword(String userName, String password) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 2.封装用户数据
        CustomizedToken token = new CustomizedToken(userName, password, LoginType.PASSWORD_LOGIN_TYPE.toString());
        // 3.执行登录方法
        try{
            subject.login(token);
            return BaseResult.ok(returnLoginInitParam(userName));
        }catch (UnknownAccountException e) {
            return BaseResult.error(ErrorCodeEnum.A0201);
        } catch (IncorrectCredentialsException e){
            return BaseResult.error(ErrorCodeEnum.A0120);
        }
    }

    public BaseResult loginByCode(String phone, String code) {
        // 1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        User sysUser = userService.findByUserPhone(phone);
        // 2.验证码登录，如果该用户不存在则创建该用户
        if (Objects.isNull(sysUser)) {
            return BaseResult.error(ErrorCodeEnum.A0201);
        }
        // 3.封装用户数据
        CustomizedToken token = new CustomizedToken(phone, code, LoginType.CODE_LOGIN_TYPE.toString());
        // 4.执行登录方法
        try{
            subject.login(token);
            return BaseResult.ok(returnLoginInitParam(phone));
        }catch (UnknownAccountException e) {
            return BaseResult.error(ErrorCodeEnum.A0201);
        }catch (ExpiredCredentialsException e) {
            return BaseResult.error(ErrorCodeEnum.A0204);
        } catch (IncorrectCredentialsException e) {
            return BaseResult.error(ErrorCodeEnum.A0203);
        }
    }

    public BaseResult modifyPassword(String userName, String code, String password) {
        Object modifyCode = redisCache.getValue(RedisKey.getModifyPasswordCodeKey(userName));
        // 判断redis中是否存在验证码
        if(Objects.isNull(modifyCode)) {
            return BaseResult.error(ErrorCodeEnum.A0204);
        }
        // 判断redis中code与传递过来的code 是否相等
        if(!Objects.equals(code, modifyCode.toString())){
            return BaseResult.error(ErrorCodeEnum.A0203);
        }
        User user = userService.findByUserName(userName);
        // 如果用户不存在，执行注册
        if (Objects.isNull(user)) {
            return BaseResult.error(ErrorCodeEnum.A0201);
        }
        String salt = CommonsUtils.uuid();
        String encryptPassword = CommonsUtils.encryptPassword(password, salt);
        user.setSalt(salt);
        user.setPassword(encryptPassword);
        // 删除缓存
        redisCache.delKeys(RedisKey.getLoginUserKey(userName));
        int flag = userService.save(user);
        if (flag > 0) {
            return BaseResult.ok(this.returnLoginInitParam(userName));
        } else {
            return BaseResult.error();
        }
    }

    /**
     * 返回登录后初始化参数
     * @param userName
     * @return Map<String, Object>
     */
    private Map<String, Object> returnLoginInitParam(String userName) {
        Map<String, Object> data = new HashMap<>(1);
        User user = userService.findByUserName(userName);
        // 生成jwtToken
        String token = tokenService.createToken(userName, user.getId(), user.getPassword());
        // token
        data.put("token", token);
        return data;
    }

}
