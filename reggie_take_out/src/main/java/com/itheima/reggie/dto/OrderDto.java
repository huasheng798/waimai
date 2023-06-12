package com.itheima.reggie.dto;

import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/18 14:23
 * @Description:
 */
public class OrderDto extends Orders {
    //手动添加数据
    private List<OrderDetail> orderDetails;
}
