package com.dzqz.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.dzqz.bean.User;
import com.dzqz.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/10 15:30
 * @Description:
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //权限验证的，你必须有某些权限才能做一些事情
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection
                    principalCollection) {
        log.info("进行权限验证-----");
        SimpleAuthorizationInfo simpleAuthorizationInfo =
                new SimpleAuthorizationInfo();
        //拿到当前的登录对象
        Subject subject = SecurityUtils.getSubject();
        //获取user对象,也可以叫做凭证
        User principal = (User) subject.getPrincipal();
        //设置用户权限
        simpleAuthorizationInfo.addStringPermission(principal.getPerms());
        return simpleAuthorizationInfo;
    }

    //身份验证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("进行身份验证-----");
        //假设，amdin，密码为123才能登录成功

        //判断用户名不能为空，则直接返回
        if (token.getPrincipal().toString() == null) {
            return null;
        }


        String usernmae = token.getPrincipal().toString();

        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName, usernmae);
        User user = userService.getOne(lqw);
        if (user == null) {
            //用户名不存在
            return null;//shiro底层会抛出 UnknownAccountException
        }
        log.info("当前实体类{}", user);
        if (usernmae.equals(user.getUserName())) {
            //封装信息进入权限验证  SimpleAuthenticationInfo 他需要四个参数
            //第一个：用户对象信息，如果没有，我们就放凭证信息
            //第二个参数:真实密码
            //第三个参数：salt 加盐
            //第四个参数: 类名  getName   com.dzqz.config.MyRealm_0
            //将用户名作为盐值  我们的盐可以是任何字符串(所以我们一般注册
            // 可以使用md5加密 然后再加上自己用户名作为盐进行加盐)
            ByteSource bytes = ByteSource.Util.bytes(usernmae);
            SimpleAuthenticationInfo simpleAuthenticationInfo
                    = new SimpleAuthenticationInfo(
                    user, user.getPassword(),
                    bytes, getName());
            log.info("当前第四个参数为,{}",getName().toString());//com.dzqz.config.MyRealm_0
            //            SimpleAuthenticationInfo simpleAuthenticationInfo
//                    = new SimpleAuthenticationInfo(
//                    user, user.getPassword(),
//                    ByteSource.Util.bytes("salt"), getName());
            return simpleAuthenticationInfo;
        }

        return null;
    }
}
