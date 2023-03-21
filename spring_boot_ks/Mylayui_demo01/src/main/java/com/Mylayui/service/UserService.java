package com.Mylayui.service;

import com.Mylayui.bean.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/5 10:34
 * @Description:
 */
public interface UserService {
    /**
     * 实现全查功能
     * @return
     */
    List<User> queryListUser(@Param("page") Integer page,
                             @Param("limit")Integer limit);

    /**
     * 实现查询总数，返回值需要
     * @return
     */
    int countUser();

    /**
     * 实现修改功能中的单查渲染页面
     * @param id
     * @return
     */
    User selectUserById(Integer id);

    /**
     * 实现删除功能
     * @param id
     * @return
     */
    boolean deleteUserById(Integer id);
}
