package com.dawn.web.config;

import com.dawn.common.annotation.ApiVersionHandlerMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * ---------------------------
 * 自定义一个条件筛选器，让SpringMVC在原有逻辑的基本上添加一个版本号匹配的规则<br>
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-29 17:30:00
 * ---------------------------
 */
@Configuration
public class ApiVersionConfig implements WebMvcRegistrations {

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionHandlerMapping();
    }

}
