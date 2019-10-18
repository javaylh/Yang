package com.dawn.upms.utils;

import com.dawn.model.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * ---------------------------
 * 密码帮助类 (PasswordUtils)
 * ---------------------------
 * @author ylh
 * @date 2019-10-18 15:20:00
 * ---------------------------
 */
public class PasswordUtils {

    /**
     * 随机生成器
     */
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    /**
     * 基础散列算法
     */
    public static final String ALGORITHM_NAME = "md5";

    /**
     * 自定义散列次数
     */
    public static final int HASH_ITERATIONS = 2;

    /**
     * 生成经过散列后的密码
     * @param user user对象
     */
    public void encryptPassword(User user) {
        // 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), HASH_ITERATIONS).toHex();
        user.setPassword(newPassword);
    }

}