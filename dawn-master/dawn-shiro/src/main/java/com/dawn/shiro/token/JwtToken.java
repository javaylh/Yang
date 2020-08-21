package com.dawn.shiro.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * ---------------------------
 * 自定义tocken对象 (JwtToken)
 * ---------------------------
 * @author ylh
 * @date 2020-08-21 10:28:00
 * ---------------------------
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
