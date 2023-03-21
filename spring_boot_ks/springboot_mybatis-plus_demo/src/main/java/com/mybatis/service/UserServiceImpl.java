package com.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.bean.User;
import com.mybatis.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:luosheng
 * @Date:2023/3/2 14:59
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{
}
