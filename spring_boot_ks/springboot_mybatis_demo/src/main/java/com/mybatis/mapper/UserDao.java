package com.mybatis.mapper;

import com.mybatis.bean.Role;
import com.mybatis.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/3 14:53
 * @Description:
 */
@Mapper
//@Resource//交给spring管理
@Repository //生成bean对象
public interface UserDao {
    /**
     * 实现新增功能
     *
     * @return
     */
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
     * 实现通过id单查，查看详情功能
     * @param id
     * @return
     */
    User queryUserById(String id);

    /**
     * 实现修改功能
     * @param id
     * @param userName
     * @param email
     * @param userType
     * @return
     */
    Integer updateUser1(@Param("id") String id,@Param("userName") String userName,
                       @Param("email") String email,@Param("userType") String userType);

    /**
     * 实现新增中的数据遍历
     * @return
     */
    List<Role> queryrole();

    /**
     * 实现新增功能
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
    User loginUser(@Param("userName") String username,@Param("password") String password);
}
