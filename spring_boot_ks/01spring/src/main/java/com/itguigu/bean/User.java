package com.itguigu.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/2/28 11:27
 * @Description:
 */

@Component
@ConfigurationProperties(prefix = "useraaa")
public class User {


    private String name;
    private int age;
    private Dog dog;
    private String[] hobby;
    private Map<String, Object> mood;
    private List<Cat> cat;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", dog=" + dog +
                ", hobby=" + Arrays.toString(hobby) +
                ", mood=" + mood +
                ", cat=" + cat +
                '}';
    }

    public List<Cat> getCat() {
        return cat;
    }

    public void setCat(List<Cat> cat) {
        this.cat = cat;
    }

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

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public Map<String, Object> getMood() {
        return mood;
    }

    public void setMood(Map<String, Object> mood) {
        this.mood = mood;
    }
}
