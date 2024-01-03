package com.jiho.anniehands.security.oauth2;

import com.jiho.anniehands.security.CustomUserDetails;
import com.jiho.anniehands.security.oauth2.loginInfo.SocialLoginInfo;
import com.jiho.anniehands.security.oauth2.loginInfo.SocialLoginInfoFactory;
import com.jiho.anniehands.user.Role;
import com.jiho.anniehands.user.User;
import com.jiho.anniehands.user.UserLoginInfo;
import com.jiho.anniehands.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        UserLoginInfo userLoginInfo = UserLoginInfo.valueOf(provider);

        Optional<User> optionalUser = userRepository.findById(loginId);
        User user = optionalUser.orElseGet(() -> {
            User newUser = User.builder()
                    .id(loginId)
                    .name(loginInfo.getName())
                    .email(loginInfo.getEmail())
                    .loginInfo(userLoginInfo)
                    .role(Role.ROLE_USER)
                    .build();
            userRepository.save(newUser);
            return newUser;
        });
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }
}
