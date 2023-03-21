package com.dzqz.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dzqz.bean.User;
import com.dzqz.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:luosheng
 * @Date:2023/3/10 17:21
 * @Description:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
