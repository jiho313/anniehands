package com.jiho.anniehands.global.security.oauth2.logininfo;

import java.util.Map;

public class NaverLoginInfo implements SocialLoginInfo {

    private final Map<String, Object> attributes;

    public NaverLoginInfo(Map<String, Object> attributes
    ) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
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
