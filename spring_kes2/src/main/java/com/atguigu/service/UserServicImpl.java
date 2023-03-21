package com.atguigu.service;

import com.atguigu.bean.User;
import com.atguigu.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:luosheng
 * @Date:2023/2/16 21:09
 * @Description:用户service
 */
@Service
public class UserServicImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public int enrollUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User SelectByUser(String userName, String password) {
        return userMapper.select(userName,password);
    }

    @Override
    public int selectByNameOnPassword(String userName, String password) {
        return userMapper.selectByNameOnPassword(userName,password);
    }
}
