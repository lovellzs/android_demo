package com.app.recyclerview;

/**
 * Created by apple on 2017/6/21.
 */

public class Person {

    public String name;
    public int age;

    public Person(String name  , int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
