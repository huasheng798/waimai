package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.ShoppingCart;
import com.itheima.reggie.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/16 20:54
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("购物车数据:{}", shoppingCart);
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, currentId);
        //判断是否为套餐
        if (shoppingCart.getDishId() != null) {
            //非套餐
            lqw.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
        } else {
            //套餐
            lqw.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart one = shoppingCartService.getOne(lqw);

        //判断数据库中是否为空
        if (one != null) {
            one.setUserId(currentId);
            //数据库中有叫number加1
            Integer number = one.getNumber();
            one.setNumber(number + 1);
            LocalDateTime now = LocalDateTime.now();
            one.setCreateTime(now);
            shoppingCartService.updateById(one);
        } else {
            //没有新建一个，然number值默认为1
            Integer integer = 1;
            shoppingCart.setNumber(integer);
            LocalDateTime now = LocalDateTime.now();
            shoppingCart.setCreateTime(now);
            shoppingCartService.save(shoppingCart);
            one = shoppingCart;
        }
        return R.success(one);
    }

    /**
     * 显示购物车信息
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查看购物车");
        LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lqw.orderByDesc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lqw);
        return R.success(list);
    }

    /**
     * 实现购物车减去功能
     *
     * @param shoppingCart
     * @return
     */
    @PostMapping("/sub")
    public R<Object> sub(@RequestBody ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        if (shoppingCart.getSetmealId() == null) {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, currentId);
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId());
            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one.getNumber() == 1) {
                LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
                lqw.eq(ShoppingCart::getUserId, currentId);
                lqw.eq(ShoppingCart::getDishId, one.getDishId());
                shoppingCartService.remove(lqw);
            //    shoppingCart = one;
                return R.success("已清空");
            } else {
                LambdaUpdateWrapper<ShoppingCart> lqw = new LambdaUpdateWrapper<>();
                lqw.eq(ShoppingCart::getUserId, currentId);
                lqw.eq(ShoppingCart::getDishId, one.getDishId());
                Integer number = one.getNumber();
                one.setNumber(number - 1);
                lqw.set(ShoppingCart::getNumber, one.getNumber());
                shoppingCartService.update(lqw);
                //这一块一定要把最新更新的数据返回给前端，要不他前端还是会显示1
                shoppingCart = one;
                return R.success(shoppingCart);
            }
        } else {
            LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ShoppingCart::getUserId, currentId);
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
            ShoppingCart one = shoppingCartService.getOne(queryWrapper);
            if (one.getNumber() == 1) {
                LambdaQueryWrapper<ShoppingCart> lqw = new LambdaQueryWrapper<>();
                lqw.eq(ShoppingCart::getUserId, currentId);
                lqw.eq(ShoppingCart::getSetmealId, one.getSetmealId());
                shoppingCartService.remove(lqw);
               // shoppingCart = one;
                return R.success("已清空");
            } else {
                LambdaUpdateWrapper<ShoppingCart> lqw = new LambdaUpdateWrapper<>();
                lqw.eq(ShoppingCart::getUserId, currentId);
                lqw.eq(ShoppingCart::getSetmealId, one.getSetmealId());
                Integer number = one.getNumber();
                one.setNumber(number - 1);
                lqw.set(ShoppingCart::getNumber, one.getNumber());
                shoppingCartService.update(lqw);
                shoppingCart = one;
                return R.success(shoppingCart);
            }
        }
    }

    /**
     * 清空购物车功能
     *
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean() {
        Long currentId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> lqw=new LambdaQueryWrapper<>();
        lqw.eq(ShoppingCart::getUserId,currentId);
        //直接清空即可
        shoppingCartService.remove(lqw);
        return R.success("清空成功");
    }
}