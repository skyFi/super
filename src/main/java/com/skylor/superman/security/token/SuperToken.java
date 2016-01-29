package com.skylor.superman.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import com.skylor.superman.model.User;

/**
 * Created by darcy on 2016/1/29.
 */
public class SuperToken extends AbstractAuthenticationToken {

    //主号的用户ID
    private Long userId;

    //当前用户的用户名
    private String username;

    private String sessionKey;

    private String subUserSessionKey;

    private String loginFrom;

    private String userType;

    public static final String LOGIN_WEB_PC = "webpc";

    public static final String LOGIN_SERVER = "server";

    public static final String LOGIN_LOCAL = "local";

    public SuperToken() {
        super(null);
        setAuthenticated(true);
    }

    public SuperToken(User user, String subUserSessionKey, String subUserName, String loginFrom) {
        super(null);
        this.userId = user.getId();
        this.username = subUserName == null ? user.getUsername() : subUserName;
        this.sessionKey = user.getSessionKey();
        if (subUserName != null) {
            this.subUserSessionKey = subUserSessionKey;
        }
        this.loginFrom = loginFrom;
        this.userType = user.getUserType().name();
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return sessionKey;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getSubUserSessionKey() {
        return subUserSessionKey;
    }

    public String getLoginFrom() {
        return loginFrom;
    }

    public String getUserType() {
        return userType;
    }
}