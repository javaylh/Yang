package com.dawn.shiro.web.interceptor;

import cn.hutool.json.JSONUtil;
import com.dawn.common.base.BaseResult;
import com.dawn.common.enums.ErrorCodeEnum;
import com.dawn.shiro.common.annotction.RepeatSubmit;
import com.dawn.common.utils.http.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * ---------------------------
 * 防止重复提交拦截器 (AbstractRepeatSubmitInterceptor)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Slf4j
public abstract class AbstractRepeatSubmitInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("防止重复提交拦截器执行了...");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    BaseResult result = BaseResult.error(ErrorCodeEnum.A0501);
                    ServletUtils.renderString(response, JSONUtil.toJsonStr(result));
                    return false;
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request);
}
