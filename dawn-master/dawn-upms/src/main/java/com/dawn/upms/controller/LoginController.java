package com.dawn.upms.controller;

import com.dawn.common.base.BaseResult;
import com.dawn.common.service.RedisService;
import com.dawn.service.UserService;
import com.dawn.shiro.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * ---------------------------
 * 登录相关 (LoginController)
 * ---------------------------
 * @author： ylh
 * @date： 2019-11-07 14:00:00
 * ---------------------------
 */
//@Api(tags = "【user】登录")
@RestController
@Validated
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    private final UserService userService;

    private final RedisService redisService;

    private final LoginService loginService;

    /**
     * 登录接口
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResult login(@NotEmpty(message = "用户名不能为空") String userName,
                            @NotEmpty(message = "密码不能为空") String password) {
//        if (false) {
//            //TODO 校验验证码
//        }
//        User loginUser = userService.findByUserName(user.getUserName());
//
//        if (BeanUtil.isEmpty(loginUser)) {
//            return I18nUtils.getBaseResultError("check.login.user.empty");
//        }
//        if (!PasswordUtils.checkPassword(user, loginUser)) {
//            return I18nUtils.getBaseResultError("check.login.name.pwd.error");
//        }
//        // 如果是整数类型的不能用equals
//        if (!loginUser.getStatus().equals(ConfigConsts.ENABLE)) {
//            return I18nUtils.getBaseResultError("check.login.user.delflag");
//        }
//        // 更新 redis 中的token
//        UserToken userToken = null;
//        Date now = DateUtil.date().toJdkDate();
//        if (redisService.hashHasKey(CacheConsts.USER_TOKEN_CACHE, loginUser.getUserName())) {
//            userToken = (UserToken)redisService.getValueByHashKey(CacheConsts.USER_TOKEN_CACHE, loginUser.getUserName());
//            userToken.setLastUpdateTime(now);
//            userToken.setExpireTime(DateUtil.offsetHour(now, 2).toJdkDate());
//            userToken.setToken(TokenGenerator.generateToken());
//        } else {
//            userToken = UserToken.builder()
//                    .token(TokenGenerator.generateToken())
//                    .userId(loginUser.getId())
//                    .userName(loginUser.getUserName())
//                    .createTime(now)
//                    .roleId(loginUser.getRoleId())
//                    // 过期时间 2小时
//                    .expireTime(DateUtil.offsetHour(now, 2).toJdkDate())
//                    .build();
//        }
//        redisService.setValueByHashKey(CacheConsts.USER_TOKEN_CACHE, loginUser.getUserName(), userToken);
//        // 生成token
//        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
        return loginService.loginByPassword(userName, password);
    }

}