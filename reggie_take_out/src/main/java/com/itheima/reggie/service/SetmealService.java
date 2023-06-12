package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/9 10:53
 * @Description:
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 实现删除功能，并且删除相关关系表中内容
     *
     * @param ids
     */
    void deleteSetmealandSetmealDish(List<Long> ids);

    /**
     * 实现修改功能，并修改相关关系表中内容
     * @param setmealDto
     */
    void updateWithDish(SetmealDto setmealDto);
}
