package com.onsoftwares.zensource.enums;

public enum Languages {
    
    ENGLISH("en"),
    PORTUGUESE("pt-br");

    private String value;

    Languages(String s) {
        this.value = s;
    }

    public String value() {
        return value;
    }

}
