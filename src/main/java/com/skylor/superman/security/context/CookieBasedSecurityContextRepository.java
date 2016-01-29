package com.skylor.superman.security.context;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import com.skylor.superman.security.encryptor.ObjectEncryptedSerializer;
import com.skylor.superman.security.token.SuperToken;

/**
 * Created by jack on 14-9-24.
 */
@Component
public class CookieBasedSecurityContextRepository implements SecurityContextRepository {

    private static final Logger logger = LoggerFactory.getLogger(CookieBasedSecurityContextRepository.class);

    @Autowired
    private ObjectEncryptedSerializer objectEncryptedSerializer;

    private final String securityCookieName = "super_security_key";

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        try {
            HttpServletRequest request = requestResponseHolder.getRequest();
            if (request.getCookies() == null) {
                return securityContext;
            }

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(securityCookieName)) {
                    SuperToken superToken = objectEncryptedSerializer.deserialize(cookie.getValue(), SuperToken.class);
                    securityContext.setAuthentication(superToken);

                    return securityContext;
                }
            }
        } catch (Exception e) {
            logger.error("Retrieve security info from cookie failed, ", e);
        }

        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public boolean containsContext(HttpServletRequest request) {

        if (request.getCookies() == null) {
            return false;
        } else {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(securityCookieName)) {
                    return true;
                }
            }

            return false;
        }
    }
}
