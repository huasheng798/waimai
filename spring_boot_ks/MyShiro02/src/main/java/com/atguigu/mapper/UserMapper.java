package com.atguigu.mapper;

import com.atguigu.bean.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:luosheng
 * @Date:2023/3/11 16:21
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
