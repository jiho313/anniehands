package com.jiho.anniehands.security.oauth2;

import com.jiho.anniehands.security.CustomUserDetails;
import com.jiho.anniehands.security.oauth2.logininfo.SocialLoginInfo;
import com.jiho.anniehands.security.oauth2.logininfo.SocialLoginInfoFactory;
import com.jiho.anniehands.domain.user.Role;
import com.jiho.anniehands.domain.user.User;
import com.jiho.anniehands.domain.user.UserLoginType;
import com.jiho.anniehands.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOatuh2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("getAttributes : {}", oAuth2User.getAttributes());

        // 소셜 로그인 이름 ex) GOOGLE, KAKAO, NAVER
        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        SocialLoginInfo loginInfo = SocialLoginInfoFactory.getSocialLoginInfo(provider, oAuth2User.getAttributes());
        String loginId = provider + "_" + loginInfo.getId();
        UserLoginType userLoginType = UserLoginType.valueOf(provider);

        User user = findOrCreateUser(loginId, loginInfo, userLoginType);
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }
    
    // 저장된 유저를 찾거나 없으면 만들어 저장한다.
    private User findOrCreateUser(String loginId, SocialLoginInfo loginInfo, UserLoginType userLoginType) {
        return userRepository.findById(loginId)
                .orElseGet(() -> createUser(loginId, loginInfo, userLoginType));
    }

    // 유저 생성 메소드
    private User createUser(String loginId, SocialLoginInfo loginInfo, UserLoginType userLoginType) {
        User newUser = User.builder()
                .id(loginId)
                .name(loginInfo.getName())
                .email(loginInfo.getEmail())
                .loginInfo(userLoginType)
                .role(Role.ROLE_USER)
                .build();
        return userRepository.save(newUser);
    }
}
