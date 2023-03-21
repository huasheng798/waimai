package com.itguigu.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/2/28 10:10
 * @Description:
 */
/*
 * 如果使用ConfigurationProperties 上面会爆红 他是因为没有找到这个注解 ，需要导入配置文件处理器
 *
 *
 *
 *
 * ConfigurationProperties:注解解析:
 *   将配置文件中配置的每一个属性值，映射到该组件
 *     参数 :
 *           prefix="students"：将配置文件中 "student"下的所有属性都一一对应
 * */


@Component
@ConfigurationProperties(prefix = "students")
public class Student {
    private String name;
    private int age;
    private String sex;
    private List<Object> books;
    private Map<String, Object> map;
    private Teacher teacher;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<Object> getBooks() {
        return books;
    }

    public void setBooks(List<Object> books) {
        this.books = books;
    }



    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", books=" + books +
                ", map=" + map +
                ", teacher=" + teacher +
                '}';
    }
}
