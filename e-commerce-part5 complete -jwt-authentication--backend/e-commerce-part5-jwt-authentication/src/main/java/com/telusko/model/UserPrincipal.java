package com.telusko.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String userName = null;
    String password = null;
    List<GrantedAuthority> authorities;

    public UserPrincipal(UserInfo userInfo) {
        userName = userInfo.getUserName();
        password = userInfo.getPassword();
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
