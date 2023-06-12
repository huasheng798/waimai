package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import lombok.Data;
import sun.dc.pr.PRError;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/10 10:23
 * @Description:一些专门路径的请求 他的数据比较复杂
 * 这个看看菜单管理里面的新增功发送的东西其实就能看得懂
 */
@Data
//首先要继承Dish里面所有的属性
public class DishDto extends Dish {
    //然后定义接收List<DishFavor> 为数据类类型的list集合
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;
    private Integer copies;

}
