package com.mybatis.service;

import com.mybatis.bean.Role;
import com.mybatis.bean.User;
import com.mybatis.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/3 14:56
 * @Description:
 */
@Service
public class UserServcieImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryListUser() {
        return userDao.queryListUser();
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int deleteUser(String id) {
        return userDao.deleteUser(id);
    }

    @Override
    public int updateUser(String userName, String id) {
        return userDao.updateUser(userName,id);
    }

    @Override
    public List<Role> selectListRole() {
        return userDao.selectListRole();
    }

    @Override
    public User queryUserById(String id) {
        return userDao.queryUserById(id);
    }

    @Override
    public Integer updateUserById(String id,String userName,String email,String userType) {
        return userDao.updateUser1(id,userName,email,userType);
    }

    @Override
    public List<Role> queryrole() {
        return userDao.queryrole();
    }

    @Override
    public Integer insertUser(User user) {
        return userDao.insertUser(user);
    }

    /**
     * 实现登录功能
     * @param username
     * @param password
     * @return
     */
    @Override
    public User loginUser(String username, String password) {
        return userDao.loginUser(username,password);
    }
}
