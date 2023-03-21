package com.Mylayui.dao;

import com.Mylayui.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/5 10:28
 * @Description:
 */
@Mapper
@Resource
public interface UserDao {
    /**
     * 实现全查功能
     *
     * @return
     */
    List<User> queryListUser(@Param("page") Integer page,
                             @Param("limit") Integer limit);

    /**
     * 实现返回数据中的总数
     *
     * @return
     */
    int countUser();

    /**
     * 实现修改中的页面渲染
     *
     * @param id
     * @return
     */
    User selectUserById(Integer id);


    /**
     * 通过id实现删除功能
     *
     * @param id
     * @return
     */
    Integer deleteUserById(Integer id);
}
