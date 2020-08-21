package com.dawn.shiro.web.interceptor.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.dawn.common.service.RedisService;
import com.dawn.common.utils.http.HttpHelper;
import com.dawn.shiro.common.constant.Constant;
import com.dawn.shiro.common.constant.RedisKey;
import com.dawn.shiro.web.filter.RepeatedlyRequestWrapper;
import com.dawn.shiro.web.interceptor.AbstractRepeatSubmitInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * ---------------------------
 * 判断请求url和数据是否和上一次相同，
 * 如果和上次相同，则是重复提交表单。 有效时间为10秒内。
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Component
public class SameUrlDataInterceptor extends AbstractRepeatSubmitInterceptor implements ApplicationContextAware {

    private final String REPEAT_PARAMS = "repeatParams";

    private final String REPEAT_TIME = "repeatTime";

    /**
     * 间隔时间，单位:秒
     * <p>
     * 两次相同参数的请求，如果间隔时间大于该参数，系统不会认定为重复提交的数据
     */
    private int intervalTime = Constant.FORM_REPEAT_TIME;


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request) {
        RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
        String nowParams = HttpHelper.getBodyString(repeatedlyRequest);

        // body参数为空，获取Parameter的数据
        if (StrUtil.isBlank(nowParams)) {
            nowParams = JSONUtil.toJsonStr(request.getParameterMap());
        }
        Map<String, Object> nowDataMap = new HashMap<>(2);
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());
        // 请求地址（作为存放cache的key值）
        String url = request.getRequestURI();
        RedisService redisService = applicationContext.getBean("redisService", RedisService.class);
        String formRepeatKey = RedisKey.getFormRepeatKey();
        Object sessionObj = redisService.getValue(formRepeatKey);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap)) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<>(2);
        cacheMap.put(url, nowDataMap);
        redisService.setValue(formRepeatKey, cacheMap, intervalTime);
        return false;
    }

    /**
     * 判断参数是否相同
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * 判断两次间隔时间
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        return (time1 - time2) < (this.intervalTime * 1000);
    }

}
