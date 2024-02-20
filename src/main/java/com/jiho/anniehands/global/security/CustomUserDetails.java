package com.jiho.anniehands.global.security;

import com.jiho.anniehands.domain.user.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@ToString
public class CustomUserDetails implements UserDetails, OAuth2User {

    private User user;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    // 일반 로그인 생성자
    public CustomUserDetails(User user) {
        this(user, null);
    }
    
    // OAuth 소셜 로그인 생성자
    public CustomUserDetails(User user, Map<String, Object> attributes) {
        this.user = user;
        this.authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        this.attributes = attributes; // OAuth 사용자 속성 ex) 소셜에서 제공하는 소셜 정보, 이름, 이메일 정보 등을 담는다.
    }

    // OAuth
    @Override
    public Map<String, Object> getAttributes() {
        return attributes; // 사용자 속성
    }

    // OAuth
    @Override
    public String getName() {
        return user.getId(); // OAuth 사용자의 이름 반환 (여기서는 아이디)
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // 사용자 권한
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getId(); // 사용자의 아이디 (고유식별자 역할 이메일이 될 수도 있음)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;    // 계정 만료되지 않음 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;    // 계정 잠금되지 않음 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;    // 자격 증명되지 않음 여부
    }

    @Override
    public boolean isEnabled() {
        return true;    // 계정 활성화 여부
    }

    public String getRealName() {
        return user.getName();  // 네비바에 사용자의 실제 이름을 반환하기 위해 만든 메소드 Thymeleaf html에서 사용함.
    }

    // 일반 로그인 팩토리 메서드
    public static CustomUserDetails create(User user) {
        return new CustomUserDetails(user);
    }

    // 소셜 로그인 펙토리 메서드
    public static CustomUserDetails create(User user, Map<String, Object> attributes) {
        return new CustomUserDetails(user, attributes);
    }

}
