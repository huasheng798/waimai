package com.atguigu.dao;

import com.atguigu.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/16 21:02
 * @Description:
 */
public interface UserMapper {
    /**
     * 实现注册，根据实体类信息
     *
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 登录功能进行数据查询
     *
     * @param userName
     * @param password
     * @return
     */
    User select(@Param("userName") String userName, @Param("password") String password);

    /**
     * 账户登录2 (第一个实体类没有查询到的时候什么也不返回 null也没有)
     *
     * @param userName
     * @param password
     * @return
     */
    int selectByNameOnPassword(@Param("userName") String userName, @Param("password") String password);

}
