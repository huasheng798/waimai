package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:luosheng
 * @Date:2023/3/9 9:46
 * @Description:
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements
        CategoryService {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;


    /**
     * 根据id删除分类，删除之前需要进行判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //这个就是我们dish里面那个categoryid是否和id关联,eq就等于..
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);//准备好条件
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        //----------------------------------------------------------
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Setmeal::getCategoryId, id);//准备好条件
        int setmealCount = setmealService.count(lqw);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (dishCount > 0) {
            //异常位置
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常

        if (setmealCount > 0) {
            //已经关联套餐，抛出业务异常\
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类,这个是iservice写的一个方法直接拿来用，大致就是他里面调用了一个删除方法
        super.removeById(id);
    }
}
