package com.dzqz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dzqz.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author:luosheng
 * @Date:2023/3/10 17:20
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
