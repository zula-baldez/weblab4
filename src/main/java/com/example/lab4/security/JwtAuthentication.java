package com.example.lab4.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return null; }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return username; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return username; }

}