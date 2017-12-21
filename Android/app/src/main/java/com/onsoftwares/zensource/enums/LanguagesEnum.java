package com.onsoftwares.zensource.enums;

public enum LanguagesEnum {
    
    ENGLISH("en"),
    PORTUGUESE("pt");

    private String value;

    LanguagesEnum(String s) {
        this.value = s;
    }

    public String value() {
        return value;
    }

}
