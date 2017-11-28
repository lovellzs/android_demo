package com.sz.testokhttp.net;

/**
 * Created by apple on 2017/11/28.
 */

public class NameValuePair {
    private String value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public NameValuePair() {
    }
}