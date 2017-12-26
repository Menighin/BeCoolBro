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

    public static LanguagesEnum fromStr(String s) {
        switch(s) {
            case "en":
                return LanguagesEnum.ENGLISH;
            case "pt":
                return LanguagesEnum.PORTUGUESE;
            default:
                return LanguagesEnum.ENGLISH;
        }
    }

}
