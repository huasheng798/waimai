package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/9 10:53
 * @Description:
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        //保存普通信息
        this.save(setmealDto);
        //把他们的外键id全部赋值为一个套餐id
        List<SetmealDish> setmealDtos = setmealDto.getSetmealDishes();
        setmealDtos = setmealDtos.stream().map((setmeal) -> {
            setmeal.setSetmealId(setmealDto.getId());
            return setmeal;
        }).collect(Collectors.toList());

        //保存套餐中的集合信息就是菜品信息
        setmealDishService.saveBatch(setmealDtos);

    }

    /**
     * 实现删除功能，并且删除相关关系表中内容
     *
     * @param ids
     */
    @Transactional
    public void deleteSetmealandSetmealDish(List<Long> ids) {
        //查询删除状态是否可以删除
        //1.构造条件
        //select count(*) from 表 where (id in (1,2,3,id...) and 状态=1)
        LambdaQueryWrapper<Setmeal> selqw = new LambdaQueryWrapper<>();
        selqw.in(Setmeal::getId, ids);
        selqw.eq(Setmeal::getStatus, 0);
        int count = this.count(selqw);
        if (count == 0) {
            //如果不能删除抛出异常
            throw new CustomException("当前状态起售中,不能删除");
        }
        //如果可以删除，删除套餐表中的数据
        this.removeByIds(ids);
        //删除关系表中的数据----setmeal_-dish
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

    @Transactional
    public void updateWithDish(SetmealDto setmealDto) {
        //直接就根据id修改前端会把id传过来一块
        this.updateById(setmealDto);
        //刚刚调用上面写的新增save方法了我说怎么为什么修改一致用新增sql
//        LambdaUpdateWrapper<Setmeal> lqw=new LambdaUpdateWrapper<>();
//        lqw.set(Setmeal::getName,setmealDto.getName())
//                .set(Setmeal::getPrice,setmealDto.getPrice())
//                .set(Setmeal::getDescription,setmealDto.getDescription())
//                .set(Setmeal::getImage,setmealDto.getImage()).eq(Setmeal::getId,setmealDto.getId());
//               // .eq(Setmeal::getUpdateTime,setmealDto.getUpdateTime())  这时间和用户我们有自动填充
//                //.eq(Setmeal::getUpdateUser,setmealDto.getUpdateUser());
//        this.update(lqw);



        // this.update
        //然后就是要修改他关联的表setmeal_dish
        //这一块我们没有传来对应的id只有它的外键id，但这个id都是一样的，所以
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish s : setmealDishes) {
                    LambdaUpdateWrapper<SetmealDish> lqw=new LambdaUpdateWrapper<>();
        lqw.set(SetmealDish::getName,s.getName())
                .set(SetmealDish::getPrice,s.getPrice())
                .set(SetmealDish::getCopies,s.getCopies()).eq(SetmealDish::getDishId,s.getDishId());
            setmealDishService.update(lqw);
        }
    }
}
