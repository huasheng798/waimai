package com.atguigu.realm;

import com.atguigu.bean.User;
import com.atguigu.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author:luosheng
 * @Date:2023/3/11 16:42
 * @Description:
 */
//@Component
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    //自定义授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection
                                                               principalCollection) {
        return null;
    }
    //自定义登录认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken
                                                                 token) throws AuthenticationException {
        //1 获取用户身份信息
        String name = token.getPrincipal().toString();
        //2 调用业务层获取用户信息（数据库中）
        User user = userService.getUserByName(name);
        //3 判断并将数据完成封装
        if(user!=null){
            return  new SimpleAuthenticationInfo(
                    token.getPrincipal(),
                    user.getPassword(),
                    ByteSource.Util.bytes("salt"),
                    token.getPrincipal().toString()
            );

        }
        return null;
    }
}
