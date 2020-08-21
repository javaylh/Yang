package com.dawn.shiro.config;

import com.dawn.shiro.common.constant.Constant;
import com.dawn.shiro.realms.CodeRealm;
import com.dawn.shiro.realms.JwtRealm;
import com.dawn.shiro.realms.PasswordRealm;
import com.dawn.shiro.realms.UserModularRealmAuthenticator;
import com.dawn.shiro.web.filter.JwtFilter;
import com.dawn.shiro.web.filter.RepeatableFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * ---------------------------
 * Shiro配置 (ShiroConfig)
 * ---------------------------
 * @author ylh
 * @date 2020-06-03 16:30:00
 * ---------------------------
 */
@Configuration
public class ShiroConfig {

    /**
     * 开启shiro权限注解
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator creator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
     * 开启shiro aop注解支持
     * 在controller中的方法前加上注解@RequiresPermissions("userInfo:test")
     * @param securityManager 安全管理器
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 密码登录时使用该匹配器进行匹配
     * @return HashedCredentialsMatcher
     */
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置哈希算法名称
        matcher.setHashAlgorithmName(Constant.ALGORITHM_NAME);
        // 设置哈希迭代次数
        matcher.setHashIterations(Constant.HASH_ITERATIONS);
        // 设置存储凭证十六进制编码
        matcher.setStoredCredentialsHexEncoded(true);
        return matcher;
    }

    /**
     * 密码登录Realm
     * @param matcher 密码匹配器
     * @return PasswordRealm
     */
    @Bean
    public PasswordRealm passwordRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        PasswordRealm userRealm = new PasswordRealm();
        userRealm.setCredentialsMatcher(matcher);
        return userRealm;
    }

    /**
     * 验证码登录Realm
     * @param matcher 密码匹配器
     * @return CodeRealm
     */
    @Bean
    public CodeRealm codeRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        CodeRealm codeRealm = new CodeRealm();
        codeRealm.setCredentialsMatcher(matcher);
        return codeRealm;
    }

    /**
     * jwtRealm
     * @return JwtRealm
     */
    @Bean
    public JwtRealm jwtRealm(){
        return new JwtRealm();
    }

    /**
     * Shiro内置过滤器，可以实现拦截器相关的拦截器
     *    常用的过滤器：
     *      anon：无需认证（登录）可以访问
     *      authc：必须认证才可以访问
     *      user：如果使用rememberMe的功能可以直接访问
     *      perms：该资源必须得到资源权限才可以访问
     *      role：该资源必须得到角色权限才可以访问
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        // authc：所有已登陆用户可访问
        // roles：有指定角色的用户可访问，通过[ ]指定具体角色，这里的角色名称与数据库中配置一致
        // perms：有指定权限的用户可访问，通过[ ]指定具体权限，这里的权限名称与数据库中配置一致
        // anon：所有用户可访问，通常作为指定页面的静态资源时使用
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filters = new HashMap<>(2);

        // 访问路径的拦截，anon 标识无需验证，不登录也可以访问
        Map<String,String> filterMap = new LinkedHashMap<>();
        // 示例
        filterMap.put("/authc/index", "authc");
        filterMap.put("/authc/admin", "roles[admin]");
        filterMap.put("/authc/renewable", "perms[Create,Update]");
        filterMap.put("/authc/removable", "perms[Delete]");
        //查看druid的sql监控
        filterMap.put("/druid/**","anon");
        //登录
        filterMap.put("/login/**","anon");
        filterMap.put("/static/**","anon");
        //验证码
        filterMap.put("/captcha.jpg**","anon");
        //服务监控
        filterMap.put("/actuator/**","anon");

        //其他路径都交给 OAuth2Filter处理
        filterMap.put("/**","oauth2");

        //从这里开始，是我为解决问题增加的，为swagger页面放行
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/images/**", "anon");

        Map<String, Filter> filter = new LinkedHashMap<>(1);
        filter.put("jwt", new JwtFilter());
        filter.put("repeat", new RepeatableFilter());
        //使用自定义的过滤器
        shiroFilterFactoryBean.setFilters(filter);
        filterMap.put("/**", "jwt,repeat");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public UserModularRealmAuthenticator userModularRealmAuthenticator(){
        // 自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     *  SecurityManager 是 Shiro 架构的核心，通过它来链接Realm和用户(文档中称之为Subject.)
     */
    @Bean
    public SecurityManager securityManager(@Qualifier("passwordRealm") PasswordRealm passwordRealm,
                                           @Qualifier("codeRealm") CodeRealm codeRealm,
                                           @Qualifier("jwtRealm") JwtRealm jwtRealm,
                                           @Qualifier("userModularRealmAuthenticator") UserModularRealmAuthenticator userModularRealmAuthenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setAuthenticator(userModularRealmAuthenticator);
        List<Realm> realms = new ArrayList<>();
        // 添加多个realm
        realms.add(passwordRealm);
        realms.add(codeRealm);
        realms.add(jwtRealm);
        securityManager.setRealms(realms);

        /*
         * 关闭shiro自带的session，详情见文档
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

}
