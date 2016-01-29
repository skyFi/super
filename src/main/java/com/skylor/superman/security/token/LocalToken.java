package com.skylor.superman.security.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Created by jack on 15-6-25.
 */
public class LocalToken extends AbstractAuthenticationToken {

    private String username;

    private String remoteAddr;

    public LocalToken(String username, String remoteAddr) {
        super(null);
        this.username = username;
        this.remoteAddr = remoteAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public Object getCredentials() {
        return remoteAddr;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
