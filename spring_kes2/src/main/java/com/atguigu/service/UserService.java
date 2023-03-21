package com.atguigu.service;

import com.atguigu.bean.User;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/16 21:02
 * @Description:
 */
public interface UserService {
    /**
     * 用户注册新增功能
     *
     * @param user
     * @return
     */
    int enrollUser(User user);

    /**
     * 实现登录功能
     *
     * @param userName
     * @param password
     * @return
     */
    User SelectByUser(String userName, String password);

    /**
     * 实现登录功能2
     * @param userName
     * @param password
     * @return
     */
    int selectByNameOnPassword(String userName, String password);

}
