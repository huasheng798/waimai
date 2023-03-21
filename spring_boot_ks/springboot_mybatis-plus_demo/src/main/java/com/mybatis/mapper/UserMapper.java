package com.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mybatis.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @Author:luosheng
 * @Date:2023/3/2 13:36
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
