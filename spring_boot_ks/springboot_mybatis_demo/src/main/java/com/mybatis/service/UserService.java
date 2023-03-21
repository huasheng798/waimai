package com.mybatis.service;

import com.mybatis.bean.Role;
import com.mybatis.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/3 14:56
 * @Description:
 */
public interface UserService {
    List<User> queryListUser();
    /**
     * 实现新增功能
     *
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 实现删除功能
     *
     * @param id
     * @return
     */
    int deleteUser(String id);

    /**
     * 实现修改功能
     *
     * @param userName
     * @param id
     * @return
     */
    int updateUser(
            @Param("userName") String userName,
            @Param("id") String id);


    /**
     * 测试实现一对多
     * @return
     */
    List<Role> selectListRole();

    /**
     * 实现通过id单查，实现详细查看功能
     * @param id
     * @return
     */
    User queryUserById(String id);

    /**
     * 通过id实现修改该
     * @param id
     * @return
     */
    Integer updateUserById(String id,String userName,String email,String userType);

    /**
     * 实现新增中的遍历列表
     * @return
     */
    List<Role> queryrole();

    /**
     * 实现新增操作
     * @param user
     * @return
     */
    Integer insertUser(User user);

    /**
     * 实现登录功能
     * @param username
     * @param password
     * @return
     */
    User loginUser(String username,String password);
}
