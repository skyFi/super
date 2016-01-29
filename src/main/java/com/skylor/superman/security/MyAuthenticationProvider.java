package com.skylor.superman.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

/**
 * Created by darcy on 2016/1/29.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider, Serializable {

    @SuppressWarnings("serial")
    private Map<String, String> SIMPLE_USERS = new HashMap<String, String>(2) {{
        put("joe", "joe");
        put("bob", "bob");
    }};

    @SuppressWarnings("serial")
    private static List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>(1) {{
        add(new GrantedAuthorityImpl("ROLE_USER"));
    }};

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        System.out.println("==Authenticate Me==");
        if (SIMPLE_USERS.containsKey(String.valueOf(auth.getPrincipal()))
                && SIMPLE_USERS.get(String.valueOf(auth.getPrincipal())).equals(auth.getCredentials())) {
            return new UsernamePasswordAuthenticationToken(auth.getName(), auth.getCredentials(), AUTHORITIES);
        }
        throw new BadCredentialsException("Username/Password does not match for " + auth.getPrincipal());
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}