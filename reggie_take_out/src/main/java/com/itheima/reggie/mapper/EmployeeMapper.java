package com.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author:luosheng
 * @Date:2023/3/7 16:33
 * @Description:
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
