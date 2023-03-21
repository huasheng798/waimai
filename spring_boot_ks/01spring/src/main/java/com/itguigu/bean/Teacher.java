package com.itguigu.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

/**
 * @Author:luosheng
 * @Date:2023/2/28 10:13
 * @Description:
 */
//将此类生成为bean
@Component
//将引入propertues资源 必须搭配@Value使用  @Value("${teacher1.tname}")
@PropertySource(value = "classpath:teacher.properties")

public class Teacher {
    @Value("${teacher1.tname}")
    private String tname;
    @Value("${teacher.tage}")
    private int tage;

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public int getTage() {
        return tage;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "tname='" + tname + '\'' +
                ", tage=" + tage +
                '}';
    }

    public void setTage(int tage) {
        this.tage = tage;
    }
}
