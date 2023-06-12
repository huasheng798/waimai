package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:luosheng
 * @Date:2023/3/15 18:37
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
