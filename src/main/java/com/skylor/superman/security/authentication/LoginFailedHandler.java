package com.skylor.superman.security.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * Created by jack on 15-9-7.
 */
public class LoginFailedHandler implements AuthenticationFailureHandler {

    private String loginFailedUrl = "/";

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public LoginFailedHandler(String loginFailedUrl) {
        this.loginFailedUrl = loginFailedUrl;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String location = loginFailedUrl + "?error_message=" + exception.getMessage();
        redirectStrategy.sendRedirect(request, response, location);
    }

}
