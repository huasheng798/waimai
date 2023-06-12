package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.entity.SetmealDish;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/12 22:21
 * @Description:套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;


    /**
     * 实现套餐管理中的添加套餐功能
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info("新增套餐接收参数:{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        //分页构造器对象
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> dtoPage = new Page<>();
        //构造条件 实现搜索框信息
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null, Setmeal::getName, name);
        lqw.orderByAsc(Setmeal::getStatus).orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(setmealPage, lqw);
        //对象拷贝 把setmealPage的所有信息都拷贝到dtoPage中
        //排除records，这里我们泛型不一样Setmeal,,,SetmealDto
        BeanUtils.copyProperties(setmealPage, dtoPage, "records");
        //我们需要自己处理对象因为records数据没处理
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> list = records.stream().map(setmeal -> {//这里我们就相当于一个遍历拿到Setmeal对象
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝 把所有信息都给setmealDto
            BeanUtils.copyProperties(setmeal, setmealDto);
            //我们可以根据每个分类的id
            Long categoryId = setmeal.getCategoryId();
            //去查询每一个分类对应的名称(根据分类的id查询分类对象)就是category表的name字段
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //拿到分类名称
                String categoryName = category.getName();
                //把名字都给setmealDto对象
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 实现删除并且删除多条功能
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    //这个注解其实就是删除setmealCache缓存中的数据，allEntries=true 这个意思就是删除setmealCache下所有的缓存数据
    public R<String> delet(@RequestParam List<Long> ids) {
        setmealService.deleteSetmealandSetmealDish(ids);
        return R.success("删除成功");
    }

    /**
     * 根据id查询信息，(修改功能中的渲染页面)
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> updateById(@PathVariable("id") Long id) {
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = setmealService.getById(id);
        BeanUtils.copyProperties(setmeal, setmealDto);
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getId() != null, SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(lqw);
        setmealDto.setSetmealDishes(list);
        return R.success(setmealDto);
    }

    /**
     * 实现修改功能
     *
     * @param setmealDto
     * @return
     */
    @PutMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> update(@RequestBody SetmealDto setmealDto) {
        log.info("修改数据展现:{}", setmealDto);
        setmealService.updateWithDish(setmealDto);
        return R.success("修改成功");
    }

    /**
     * 实现前端根据id和状态查询setmeal套餐信息
     *
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_'+#setmeal.status")
    //这里就是把我们的这个对象载入到缓存当中
    //其中value值是设置该缓存的名字
    //key值 是和当前传来的数据相关的，我们根据cxxxxid和status查询的，直接拼接一个key
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> list = setmealService.list(lqw);
        return R.success(list);
    }

    /**
     * 修改状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> status(@PathVariable("status") Integer status,
                            @RequestParam("ids") List<Long> ids) {
        log.info("当前修改状态{},{}",status,ids);

        if (status==0){
            //当前状态为1时
            for (Long id:ids) {
                LambdaUpdateWrapper<Setmeal> lqw1=new   LambdaUpdateWrapper<>();
                lqw1.set(Setmeal::getStatus,0);
                lqw1.eq(Setmeal::getId,id);
                setmealService.update(lqw1);
            }
            return R.success("当前状态已修改为禁售");
        }
        if (status==1){
            //当前状态为1时
            for (Long id:ids) {
                LambdaUpdateWrapper<Setmeal> lqw1=new   LambdaUpdateWrapper<>();
                lqw1.set(Setmeal::getStatus,1);
                lqw1.eq(Setmeal::getId,id);
                setmealService.update(lqw1);
            }
            return R.success("当前状态已修改为起售");
        }
        return R.success("发生错误了-_-");
    }
}

