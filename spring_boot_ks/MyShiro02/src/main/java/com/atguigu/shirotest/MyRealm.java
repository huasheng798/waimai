package com.atguigu.shirotest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * @Author:luosheng
 * @Date:2023/3/11 13:04
 * @Description:
 */
public class MyRealm extends AuthenticatingRealm {
    //自定义登录认证方法，shiro的login方法底层会调用该类的认证方法进行认证
    //需要配置自定义的realm生效，在init文件中配置，或者springboot中配置
    //该方法只是获取进行对比的信息 认证逻辑还是按照shiro底层认证逻辑完成
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        //1 获取身份信息
//        String principal = token.getPrincipal().toString();
//        //2 获取凭证信息
//        String password = new String((char[])
//                token.getCredentials());
//        System.out.println("认证用户信息："+principal+"---"+password);
//        //3 获取数据库中存储的用户信息
//        if(principal.equals("zhangsan")){
//            //3.1 数据库存储的加盐迭代 3 次密码
//            String pwdInfo = "7174f64b13022acd3c56e2781e098a5f";
//            //3.2 创建封装了校验逻辑的对象，将要比较的数据给该对象
//            AuthenticationInfo info = new SimpleAuthenticationInfo(
//                    token.getPrincipal(),
//                    pwdInfo,
//                    ByteSource.Util.bytes("salt"),
//                    token.getPrincipal().toString());
//            return info;
//        }
        return null;
    }
}
