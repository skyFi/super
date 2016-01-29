package com.skylor.superman.security;

import java.lang.reflect.InvocationTargetException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.skylor.superman.security.authentication.LocalAuthenticationFilter;
import com.skylor.superman.security.authentication.LoginFailedHandler;
import com.skylor.superman.security.authentication.LoginSuccessHandler;

/**
 * @author skylor
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Resource(name = "superAccessDecisionManager")
    private AccessDecisionManager decisionManager;

    private final String authenticateUrl = "/login.html";

    private final String loginFailedUrl = "/login/login-fail.html";

    private final String logoutUrl = "/logout";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }

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
//                        return request.getParameter("username") != null;
                        return false;
                    }
                });
            }
        }).successHandler(successHandler).failureHandler(new LoginFailedHandler(loginFailedUrl));

        http.requestMatcher(new OrRequestMatcher(
                new AntPathRequestMatcher("/files/**"),
                new AntPathRequestMatcher("/service/**"),
                new AntPathRequestMatcher(authenticateUrl),
                new AntPathRequestMatcher(logoutUrl)))
                .authorizeRequests().antMatchers("/files/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/service/**").authenticated()
                .accessDecisionManager(decisionManager)
                .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

    private void registerFilterClass(HttpSecurity http) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object comparator = FieldUtils.readDeclaredField(http, "comparitor", true);

        MethodUtils.invokeMethod(comparator, "registerBefore",
                new Class[]{LocalAuthenticationFilter.class, UsernamePasswordAuthenticationFilter.class});
    }
}