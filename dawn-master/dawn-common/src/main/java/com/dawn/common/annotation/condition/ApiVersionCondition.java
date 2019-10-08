package com.dawn.common.annotation.condition;

import com.dawn.common.constant.ConfigConsts;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ---------------------------
 * 自定义一个条件筛选器，让SpringMVC在原有逻辑的基本上添加一个版本号匹配的规则
 * （通过URL加版本号）
 * ---------------------------
 * @author： ylh
 * 时间： 2019-09-29 17:30:00
 * ---------------------------
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    /**
     * 请求路径中版本的前缀， 这里用 /v[1-9]/的形式
     * 请求中的Header版本号规则 这里用 v[1-9]/的形式（细微差异）
     */
    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)");

    /**
     * 当前api版本控制方式：URL 通过请求路径 HEADER通过头请求方式加版本号
     */
    private static final String VERSION_MODE = ConfigConsts.API_VERSION_MODE_URL;

    private int apiVersion;

    public ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        String input = null;
        if (VERSION_MODE.equals(ConfigConsts.API_VERSION_MODE_URL)) {
            input = request.getRequestURI();
        } else {
            input = request.getHeader("ApiVersion");
            if (input == null) {
                input = "v1";
            }
        }

        Matcher m = VERSION_PREFIX_PATTERN.matcher(input);
        if (m.find()) {
            Integer version = Integer.valueOf(m.group(1));
            // 如果请求的版本号大于配置版本号， 则满足
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return other.getApiVersion() - this.apiVersion;
    }

    public int getApiVersion() {
        return apiVersion;
    }

}

