package com.atguigu.service;

import com.atguigu.bean.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author:luosheng
 * @Date:2023/3/11 16:22
 * @Description:
 */
public interface UserService extends IService<User> {

    User getUserByName(String name);
}
