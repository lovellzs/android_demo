package com.sz.testokhttp.bean;

/**
 * Created by apple on 2017/11/28.
 */

public class User {

    private String name;
    private String desc;
    private boolean sex;
    private int age;

    public User(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
