package com.dawn.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * ---------------------------
 * 国际化配置文件(I18nConfig)
 * ---------------------------
 * @author： ylh
 * @date： 2020-04-20 15:30:00
 * ---------------------------
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * cookie区域解析器 请求中cookie携带LOCALE=en_US即可
     * @return
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setCookieName("LOCALE");
        //设置默认区域
        slr.setDefaultLocale(Locale.CHINA);
        //设置cookie有效期.单位秒，-1客户端浏览器关闭失效
        slr.setCookieMaxAge(-1);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}

