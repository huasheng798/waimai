package com.atguigu.server;

import com.atguigu.bean.User;
import com.atguigu.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author:luosheng
 * @Date:2023/3/23 21:29
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
