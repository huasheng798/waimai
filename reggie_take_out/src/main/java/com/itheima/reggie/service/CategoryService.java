package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Category;

/**
 * @Author:luosheng
 * @Date:2023/3/9 9:46
 * @Description:
 */
public interface CategoryService extends IService<Category> {

    void remove(Long id);
}
