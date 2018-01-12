package com.onsoftwares.zensource.enums;

public enum BroadcastIntentsEnum {

    GET_RANDOM_QUOTE("ACTION_GET_RANDOM_QUOTE");

    private String value;

    BroadcastIntentsEnum(String s) {
        this.value = s;
    }

    public String value() {
        return value;
    }

}
