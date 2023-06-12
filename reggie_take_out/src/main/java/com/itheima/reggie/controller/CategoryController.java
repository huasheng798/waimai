package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/9 9:50
 * @Description:
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category新增接收参数:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize) {
        //分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();

        lqw.orderByAsc(Category::getSort);

        //进行分页查询
        categoryService.page(pageInfo, lqw);
        return R.success(pageInfo);
    }


    @DeleteMapping
    public R<String> delete(Long ids) {
        log.info("删除层接收信息{}", ids);
        categoryService.remove(ids);
        return R.success("删除成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改信息:{}", category);
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    @GetMapping("/list")
    //其中要接收个类型 这个类型1 菜品分类 2 套餐分类
    public R<List<Category>> list(Integer type) {
        //写个条件
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //首先要按照类型进行查询  其中第一个就是判断传来的参数是否为空(我觉得不可能为空..),第二个参数就是第三个参数传的值，然后再通过::形式拿来用
        lqw.eq(type != null, Category::getType, type);
        //进行排序查询 ,他这个意思就是 按照sort升序，按照 update修改事件进行升序(升序降序是不需要提前准备值的)
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);

        //上面是准备条件,现在进行查询.将查询到的内容返回给页面
        List<Category> list = categoryService.list(lqw);
        return R.success(list);
    }
}
