package com.dawn.shiro.common.utils;

import com.dawn.shiro.common.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Sha256Hash;

import java.util.UUID;

/**
 * ---------------------------
 * 通用帮助类 (CommonsUtils)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Slf4j
public class CommonsUtils {

    /**
     * 获取六位数验证码
     * @return 验证码
     */
    public static int getCode(){
        return (int)((Math.random()*9+1)*1000);
    }


    /**
     * 使用SHA256加密
     * @param password 需要加密的密码
     * @param salt 盐
     * @return 返回加密后的密码
     */
    public static String encryptPassword(String password, String salt){
        return String.valueOf(new Sha256Hash(password, salt, Constant.HASH_ITERATIONS));
    }

    /**
     * 获取uuid
     * @return string
     */
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

}
