package com.tongji.backend.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Integer userID;
    private final String token;

    public JwtAuthenticationToken(Integer userID, String token) {
        super(null);
        this.userID = userID;
        this.token = token;
        setAuthenticated(true);
    }

    public Integer getUserID() {
        return userID;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userID;
    }
}
