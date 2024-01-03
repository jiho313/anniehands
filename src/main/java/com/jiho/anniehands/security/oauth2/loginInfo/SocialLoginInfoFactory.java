package com.jiho.anniehands.security.oauth2.loginInfo;

import java.util.Map;

public class SocialLoginInfoFactory {

    private SocialLoginInfoFactory() {
        throw new IllegalStateException("소셜로그인 정보 생성을 위한 팩토리 클래스입니다.");
    }

    public static SocialLoginInfo getSocialLoginInfo(String provider, Map<String, Object> attributes) {
        switch (provider.toUpperCase()) {
            case "GOOGLE" :
                return new GoogleLoginInfo(attributes);
            case "KAKAO" :
                return new KakaoLoginInfo(attributes);
            default:
                throw new IllegalArgumentException(provider);
        }
    }
}
