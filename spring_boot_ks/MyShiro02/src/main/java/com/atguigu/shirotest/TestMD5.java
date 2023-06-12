package com.atguigu.shirotest;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Author:luosheng
 * @Date:2023/3/13 11:26
 * @Description:用于获取各种密码加密或加盐
 */
public class TestMD5 {
    public static void main(String[] args) {
        String passwordOne = "123456";
        //加密三次加密加盐
        //  Md5Hash salt = new Md5Hash(passwordOne, "salt", 3);
        // Md5Hash salt = new Md5Hash(passwordOne+"salt");
           Md5Hash salt = new Md5Hash("123456","admin",3);
        System.out.println(salt);

    }

}
//盐可以是任何字符串
//d1b129656359e35e95ebd56a63d7b9e0   加盐且3次加密
//207acd61a3c1bd506d7e9a4535359f8a  MD5+加盐
//e10adc3949ba59abbe56e057f20f883e   只进行MD5加密
//a20aca20d3d9f00efe2c4d7f3347182b  盐:aaa且三次加密

