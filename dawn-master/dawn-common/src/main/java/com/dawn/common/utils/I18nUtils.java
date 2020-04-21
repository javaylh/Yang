package com.dawn.common.utils;

import com.dawn.common.base.BaseResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * ---------------------------
 * 根据key获取国际化之后的信息(I18nUtils)
 * ---------------------------
 * @author： ylh
 * @date： 2020-04-20 15:30:00
 * ---------------------------
 */
public class I18nUtils {

    private static MessageSource messageSource = SpringUtils.getBean("messageSource");

    /**
     *
     * @param key 对应messages配置的key.
     * @return
     */
    public static String getMessage(String key) {
        return getMessage(key, null);
    }

    /**
     * 根据语言及key获取国际化内容
     * @param locale
     * @param key
     * @return
     */
    public static String getMessage(Locale locale,String key) {
        return messageSource.getMessage(key,null,locale);
    }

    /**
     *
     * @param key ：对应messages配置的key.
     * @param args : 单个参数.
     * @return
     */
    public static String getMessageBySoleParam(String key, Object args) {
        Object[] objectArr = new Object[1];
        objectArr[0] = args;
        return getMessage(key, objectArr, "");
    }

    /**
     *
     * @param key ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public static String getMessage(String key, Object[] args) {
        return getMessage(key, args, "");
    }

    /**
     *
     * @param key ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public static String getMessage(String key,Object[] args,String defaultMessage) {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, args, defaultMessage, locale);
    }
    /**
     *
     * @param key ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public static String getMessage(String key,Object[] args,String defaultMessage,Locale locale) {
        //这里使用比较方便的方法，不依赖request.
        return messageSource.getMessage(key, args, defaultMessage, locale);
    }

    /**
     * 返回国际化后的HTTP结果封装，状态ok
     * @param key ：对应国际化中配置的key.
     * @return
     */
    public static BaseResult getBaseResultOk(String key) {
        return BaseResult.ok(getMessage(key));
    }

    /**
     * 返回国际化后的HTTP结果封装，状态ok
     * @param key ：对应国际化中配置的key.
     * @param param ：单个参数(国际化需要的).
     * @return
     */
    public static BaseResult getBaseResultOkBySoleParam(String key, Object param) {
        return BaseResult.ok(getMessageBySoleParam(key, param));
    }

    /**
     * 返回国际化后的HTTP结果封装，状态error
     * @param key ：对应国际化中配置的key.
     * @return
     */
    public static BaseResult getBaseResultError(String key) {
        return BaseResult.error(getMessage(key));
    }

    /**
     * 返回国际化后的HTTP结果封装，状态error
     * @param key ：对应国际化中配置的key.
     * @param param ：单个参数(国际化需要的).
     * @return
     */
    public static BaseResult getBaseResultErrorBySoleParam(String key, Object param) {
        return BaseResult.error(getMessageBySoleParam(key, param));
    }

    /**
     * 获取当前语言：zh_CN,zh_TW,en_US
     * @return
     */
    public static String getLanguage() {
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

}