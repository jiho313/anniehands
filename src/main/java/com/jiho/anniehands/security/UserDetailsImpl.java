package com.jiho.anniehands.security;

import com.jiho.anniehands.user.LoginInfo;
import com.jiho.anniehands.user.Role;
import com.jiho.anniehands.user.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String id;
    private String password;
    private Role role;
    private LoginInfo loginInfo;
    private Collection<GrantedAuthority> authorities;

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
        return id;
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

    @Builder
    public UserDetailsImpl(String id, String password, Role role, LoginInfo loginInfo, Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.password = password;
        this.role = role;
        this.loginInfo = loginInfo;
        this.authorities = authorities;
    }

    public static UserDetailsImpl create(User user) {
        return UserDetailsImpl.builder()
                .id(user.getId())
                .password(user.getPassword())
                .role(user.getRole())
                .loginInfo(user.getLoginInfo())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().getRole())))
                .build();
    }
}
