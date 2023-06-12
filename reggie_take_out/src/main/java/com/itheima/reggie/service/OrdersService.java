package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Orders;

/**
 * @Author:luosheng
 * @Date:2023/3/17 12:50
 * @Description:
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
