package com.onsoftwares.zensource.enums;
public enum SharedPreferencesEnum {

    LIKED_QUOTES("onsoftwares.com.ZenSourceLikedQuotes"),
    DISLIKED_QUOTES("onsoftwares.com.ZenSourceDislikedQuotes"),
    LANGUAGE("onsoftwares.com.Language");

    private String value;

    SharedPreferencesEnum(String s) {
        this.value = s;
    }

    public String value() {
        return this.value;
    }
}
