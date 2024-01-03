package com.jiho.anniehands.security.config;

import com.jiho.anniehands.security.oauth2.CustomOatuh2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class AnniehandsSecurityConfig {
    /*
     * Spring Security 6부터는 '.and()'메서드를 제거하고 Lambda DSL을 강화하는 방식으로 업데이트되어, Deprecated된 부분이 있다.
     * Lambda DSL의 장점: 기존에 사용하던 '.and()' 메서드는 서로 무관한 요소까지 체이닝 됐기 때문에 코드의 가독성을 저하시켰다.
     * 때문에 람다 메서드 호출 후에는 HttpSecurity 인스턴스가 자동으로 반환되어 '.and()'메서드 없이도 구성이 가능해졌다.
     * 이는 시큐리티의 설정 구성을 관련 메서드들끼리 모듈화(그룹화)하여 보다 직관적이고 간결하게 설정 정보를 알 수 있다.
     * */

    private final CustomOatuh2UserService customOatuh2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf 비활성화
        http.csrf(AbstractHttpConfigurer::disable)
                // 폼 로그인 관련 설정
                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/login")                        // 사용자 정의 로그인 페이지
                        .usernameParameter("id")             // 로그인 폼의 사용자 id필드 이름
                        .passwordParameter("password")       // 로그인 폼의 사용자 비밀번호 필드 이름
                        .loginProcessingUrl("/login")         // 로그인 처리 url
                        .defaultSuccessUrl("/")               // 로그인 성공 시 리다이렉트 url
                        .failureUrl("/user/login?error=fail")) // 로그인 실패 시 리다이렉트 url
                // 로그아웃 관련 설정
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")                       // 로그아웃 처리 url
                        .logoutSuccessUrl("/")                // 로그아웃 성공시 리다이렉트 url
                        .invalidateHttpSession(true))      // 로그아웃 시 세션 파괴
                // OAuth 로그인 관련 설정
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/user/login?error=fail")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOatuh2UserService)))
                // 인증인가 관련 예외 처리 설정
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/login?error=noauth"))
                        .accessDeniedHandler((request, response, authException) -> response.sendRedirect("/login?error=denied")));
        return http.build();    // HttpSecurity 설정을 기반으로 SecurityFilterChain을 빌드
    }

    // 비밀번호 암호화(인코딩)을 위한 Bcrypt 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

