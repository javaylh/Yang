package com.dawn.shiro.config;

import com.dawn.shiro.web.interceptor.RequestLimitInterceptor;
import com.dawn.shiro.web.interceptor.impl.SameUrlDataInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ---------------------------
 * Mvc 配置类 (MvcConfig)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源
                .allowedOrigins("*")
                // 允许请求方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*")
                //是否发送cookie
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 请求限制拦截器
        registry.addInterceptor(requestLimitInterceptor())
                .excludePathPatterns("/webjars/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/**", "/images/**")
                .addPathPatterns("/**")
                .order(Integer.MAX_VALUE-1);
        // 防止重复提交拦截器
        registry.addInterceptor(new SameUrlDataInterceptor())
                .excludePathPatterns("/login/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/**", "/images/**")
                .addPathPatterns("/**")
                .order(Integer.MAX_VALUE);
    }

    @Bean
    public RequestLimitInterceptor requestLimitInterceptor(){
        return new RequestLimitInterceptor();
    }
}
