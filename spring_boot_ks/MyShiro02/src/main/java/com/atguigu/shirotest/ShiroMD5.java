package com.atguigu.shirotest;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @Author:luosheng
 * @Date:2023/3/11 11:17
 * @Description:
 */
public class ShiroMD5 {
    public static void main(String[] args) {
        //密码明文
        String password = "z3";
        String password1 = "123456";
        Md5Hash m = new Md5Hash(password1,"salt");
        System.out.println(m);
        //使用md5加密
        Md5Hash md5Hash = new Md5Hash(password);
        //toHex是十六进制的内容，用字符串表示,感觉用了和没用一样
        System.out.println("md5加密" + md5Hash.toHex());
        //带盐的md5加密，盐就是在密码明文后拼接新字符串，然后进行加密,其实加盐就是为了叫密码更安全
        Md5Hash md5Hash1 = new Md5Hash(password + "salt");
        System.out.println("带盐的md5加密" + md5Hash1.toHex());
        //为了保证安全，避免被破解还可以多次迭代加解密，保证数据安全,这个就是迭代三次加密
        Md5Hash salt = new Md5Hash(password, "salt", 3);
        System.out.println("带盐的的三次md5加密" + salt.toHex());
        System.out.println("7174f64b13022acd3c56e2781e098a5f");
        //使用父类进行加密,这个其实也就是带盐的三次MD5加密
        SimpleHash simpleHash = new SimpleHash("MD5", password,
                        "salt", 3);
        System.out.println("使用父类进行加密"+simpleHash);
    }
}
