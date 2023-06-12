package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.beans.Transient;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/9 10:48
 * @Description:
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     *
     * @param dishDto
     */
    //@Override   //这个重写只是一个标记用不用都行，只是检查重写方法格式对不对
    @Transactional//保证事务的一致性
    public void saveWithFlavor(DishDto dishDto) {

        //保存菜品的基本信息到菜品表dish,这个应该就当前对象中的方法
        this.save(dishDto);

        //拿个中专变量菜品的id
        Long id = dishDto.getId();
        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        //这里就是遍历这个对象中，把它的dishid 都给赋值一遍,
        flavors = flavors.stream().map(a -> {
            a.setDishId(id);//这里这个操作就是给每个dishid赋值，值为id呗
            return a;
        }).collect(Collectors.toList());//生成一个list 集合 也可是数组呀什么的
        // flavors= flavxxxx...这里我们可以看到 这个lam表达式作用就是把当前list集合加工一下，然后再生成一个list集合
        //把值再赋给flavors

        //保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    //修改渲染页面，根据id查询菜品信息何对应的口味信息
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息,从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        //这个拷贝就是把dish查询的信息放到dishDto中
        BeanUtils.copyProperties(dish, dishDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        //这就拿着dish中id查对应的dishflavor数据
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }


    //更新菜品信息，同时更新对应的口味信息
    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

        //更新dish表的基本信息
        this.updateById(dishDto);//直接就根据前端传来的数据进行dishdto修改
        //清理档期那菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(lqw);
        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((dishFlavor) -> {
            dishFlavor.setDishId(dishDto.getId());
            return dishFlavor;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
