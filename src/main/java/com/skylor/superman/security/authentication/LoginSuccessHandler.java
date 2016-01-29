package com.skylor.superman.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.skylor.superman.security.token.SuperToken;

/**
 * @author skylor
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

    private static final String NORMAL_URL = "/";

    private static final String CLOSE_URL = "/web-close.html";

    private static final String SECURITY_COOKIE_NAME = "hermes_security_key";


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        SuperToken token = (SuperToken)authentication;
        String loginFrom = token.getLoginFrom();
    }


}
