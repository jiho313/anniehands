package com.jiho.anniehands.security.oauth2.loginInfo;

import java.util.Map;

public class GoogleLoginInfo implements SocialLoginInfo{
    private Map<String, Object> attributes;

    public GoogleLoginInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
