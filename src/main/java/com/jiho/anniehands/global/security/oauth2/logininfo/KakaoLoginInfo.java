package com.jiho.anniehands.global.security.oauth2.logininfo;

import java.util.Map;

public class KakaoLoginInfo implements SocialLoginInfo {

    private Map<String, Object> attributes; // getAttributes
    private Map<String, Object> attributesProperties; // getAttributes
    private Map<String, Object> attributesAccount; // getAttributes

    public KakaoLoginInfo(Map<String,Object> attributes){
        this.attributes = attributes;
        this.attributesProperties = (Map<String, Object>) attributes.get("properties");
        this.attributesAccount = (Map<String, Object>) attributes.get("kakao_account");
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return (String) attributesProperties.get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) attributesAccount.get("email");
    }
}
