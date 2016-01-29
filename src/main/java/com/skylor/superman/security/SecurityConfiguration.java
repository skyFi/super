package com.skylor.superman.security;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.skylor.superman.security.authentication.LocalAuthenticationFilter;
import com.skylor.superman.security.authentication.LoginFailedHandler;
import com.skylor.superman.security.authentication.LoginSuccessHandler;

/**
 * @author Eric
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    private final String authenticateUrl = "/callback.shtml";

    private final String loginFailedUrl = "/login/login-fail.html";

    private final String logoutUrl = "/logout";

    @Override
    @SuppressWarnings("unchecked")
    protected void configure(HttpSecurity http) throws Exception {

        registerFilterClass(http);

        http.apply(new AbstractAuthenticationFilterConfigurer(new LocalAuthenticationFilter(), authenticateUrl) {
            @Override
            protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
                return new AndRequestMatcher(new AntPathRequestMatcher(loginProcessingUrl), new RequestMatcher() {
                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return request.getParameter("username") != null;
                    }
                });
            }
        }).successHandler(successHandler).failureHandler(new LoginFailedHandler(loginFailedUrl));
        System.out.println(http);
    }

    private void registerFilterClass(HttpSecurity http) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object comparator = FieldUtils.readDeclaredField(http, "comparitor", true);

        MethodUtils.invokeMethod(comparator, "registerBefore",
                new Class[]{LocalAuthenticationFilter.class, UsernamePasswordAuthenticationFilter.class});
    }
}