package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.entity.DishFlavor;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishFlavorService;
import com.itheima.reggie.service.DishService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/10 9:10
 * @Description:菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品功能
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info("新增菜品信息:{}", dishDto.toString());
        //这个savewixxx我们自己写的在service
        dishService.saveWithFlavor(dishDto);
        //这里我们就可以直接删除掉我们redis中存的缓存数据，以免发生，首页和数据库当中的新修改的数据不同
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        //创建分页构造器对象
        Page<Dish> dishPage = new Page<>(page, pageSize);
        //我们需要用到DishDto中的xxxname的属性所以需要再查一下
        Page<DishDto> dishDtoPage = new Page<>();
        //创建name判断，使用lwq
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Dish::getName, name);

        //然后升序降序排序
        lqw.orderByDesc(Dish::getStatus).orderByDesc(Dish::getUpdateTime);
        // 调用查询方法ser.page
        dishService.page(dishPage, lqw);
        //我们只需要把查到的dishPage中所有的属性，都再赋值(拷贝给)dishDtoPage,然后排除records属性不需要拷贝进点开   Page就可以看到
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");

        //这里我们需要把page中的records进行一个单独处理，处理为List<DishDto> 类型的
        List<Dish> records = dishPage.getRecords();

        //这个dish就相当于我们Dish的一个一个类

        List<DishDto> list = records.stream().map(dish -> {
            DishDto dishDto = new DishDto();
            Long categoryId = dish.getCategoryId();//拿到分类id先
            //然后我们通过categoryId拿到每一个分类的对象
            BeanUtils.copyProperties(dish, dishDto);
            Category category = categoryService.getById(categoryId);
            //然后拿到每一个分类的名称
            if (category != null) {
                String categoryName = category.getName();
                //有分类名称了只需要把名称赋给DishDto中的categoryName就ok了

                dishDto.setCategoryName(categoryName);
                //上面只是把单个的categoryname的属性给赋值了，但是其他的属性还没有赋值
                //我们这里只需要把dishDto属性拷贝给dish

            }
            return dishDto;
        }).collect(Collectors.toList());//收集起来赋值给list
        dishDtoPage.setRecords(list);
        dishService.page(dishPage);
        return R.success(dishDtoPage);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }

    /**
     * 修改菜品信息
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        //这里我们就可以直接删除掉我们redis中存的缓存数据，以免发生，首页和数据库当中的新修改的数据不同
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("修改成功");
    }

    /**
     * 商品启用/批量启用
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> qishou(@RequestParam("ids") List<Long> ids) {
        log.info("商品启售参数:{}", ids);
        //这里只需要将前台传来的参数修改为0
        for (Long id : ids) {
            LambdaUpdateWrapper<Dish> lqw = new LambdaUpdateWrapper<>();
            lqw.set(Dish::getStatus, 1).eq(Dish::getId, id);
            dishService.update(lqw);

        }

        return R.success("启售成功");
    }

    /**
     * 商品禁用/批量禁用
     *
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> jinyong(@RequestParam("ids") List<Long> ids) {
        log.info("商品禁用参数:{}", ids);
        //这里只需要将前台传来的参数修改为0
        for (Long id : ids) {
            LambdaUpdateWrapper<Dish> lqw = new LambdaUpdateWrapper<>();
            lqw.set(Dish::getStatus, 0).eq(Dish::getId, id);
            dishService.update(lqw);
        }
        return R.success("商品已禁用");
    }

    /**
     * 商品删除/批量删除功能
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") List<Long> ids) {
        log.info("删除商品参数:{}", ids);
        for (Long id : ids) {
            LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Dish::getId, id);
            dishService.remove(lqw);
        }

        return R.success("删除成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     *
     * @param dish
     * @return
     */
/*    @GetMapping("/list")
    public R<List<Dish>> list(Long categoryId) {
        //构造查询条件
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(categoryId != null, Dish::getCategoryId, categoryId);
        //必须为起售状态
        lqw.eq(Dish::getStatus,1);
        //排序规则
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        //执行sql
        List<Dish> list = dishService.list(lqw);
        return R.success(list);
    }*/
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        List<DishDto> dishDtoList;
        //因为我们每次点击页面都要进行一次访问数据库，这样太浪费了所以这里我们使用redis把他暂时存到缓存中
        //我们这里先在redis中取一下如果有则直接使用没有，就查询数据库
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoList != null) {
            //不未空说明查出来来，我们直接用redis中的数据
            return R.success(dishDtoList);
        }
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus, 1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        //这里这一块我们就把查到的数据放到redis缓存当中，好下一次查询直接就在redis中拿//设置60分钟自动删除改该数据
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }
}
