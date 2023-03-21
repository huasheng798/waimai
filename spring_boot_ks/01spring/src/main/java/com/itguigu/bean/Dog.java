package com.itguigu.bean;

import org.springframework.stereotype.Component;

/**
 * @Author:luosheng
 * @Date:2023/2/28 11:28
 * @Description:
 */

public class Dog {
    private String dname;
    private int dage;

    @Override
    public String toString() {
        return "Dog{" +
                "dname='" + dname + '\'' +
                ", dage=" + dage +
                '}';
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public int getDage() {
        return dage;
    }

    public void setDage(int dage) {
        this.dage = dage;
    }
}
