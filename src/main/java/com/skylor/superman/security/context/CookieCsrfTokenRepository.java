package com.skylor.superman.security.context;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

/**
 * Created by jack on 15-3-3.
 */
public class CookieCsrfTokenRepository implements CsrfTokenRepository {

    private static final String headerName = "X-XSRF-TOKEN";

    private static final String parameterName = "X-XSRF-TOKEN";

    private static final String cookieName = "XSRF-TOKEN";

    private static final String REST_SECURITY_TOKEN_HEADER = "rest_security_token";

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return new DefaultCsrfToken(headerName, parameterName, UUID.randomUUID().toString());
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            Cookie cookie = new Cookie(cookieName, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            Cookie cookie = new Cookie(cookieName, token.getToken());
            cookie.setPath("/");
            response.addCookie(cookie);
        }

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        String securityToken = request.getHeader(REST_SECURITY_TOKEN_HEADER);
        if (securityToken != null) {
            return new DefaultCsrfToken(REST_SECURITY_TOKEN_HEADER,
                    REST_SECURITY_TOKEN_HEADER,
                    securityToken);
        }

        String token = "NAN";
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        return new DefaultCsrfToken(headerName, parameterName, token);
    }
}
