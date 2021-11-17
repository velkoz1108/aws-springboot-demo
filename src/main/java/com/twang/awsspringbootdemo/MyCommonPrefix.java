package com.twang.awsspringbootdemo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class MyCommonPrefix {
    @JacksonXmlProperty(localName = "Prefix")
    private String prefix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
