package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.OrderDto;
import com.itheima.reggie.entity.OrderDetail;
import com.itheima.reggie.entity.Orders;
import com.itheima.reggie.service.OrderDetailService;
import com.itheima.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/17 12:57
 * @Description:
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 实现点至支付订单
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> post(@RequestBody Orders orders) {
        log.info("支付订单数据:{}", orders);
        ordersService.submit(orders);
        return R.success("支付成功");
    }

    /**
     * 实现查询订单功能
     * 这点有点问题
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/userPage")
    public R<Page> userPage(Integer page, Integer pageSize) {
        log.info("查询订单功能参数:{},{}", page, pageSize);
        Page ordersPage = new Page<>(page, pageSize);
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Orders::getUserId, currentId);
//        //我们需要一个总商品数据，我们可以查出所有的订单号，然后根据订单号去查询所有的商品总数据
//        AtomicReference<Integer> sumNum = new AtomicReference<>(0);
//        // Integer sumNum = null;
        ;
        List<Orders> one = ordersService.list(lqw);
        if (one.isEmpty()) {
            return R.error("当前账单为空");
        }
        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetail::getOrderId, one.get(0).getId());

        orderDetailService.page(ordersPage, lambdaQueryWrapper);
        ordersService.page(ordersPage, lqw);

        //    ordersPage.setRecords(orderDetails);
        return R.success(ordersPage);
    }

//    @GetMapping("/userPage")
//    public R<Page<OrderDto>> userPage(Integer page, Integer pageSize) {
//        log.info("查询订单功能参数:{},{}", page, pageSize);
//        Page<OrderDto> ordersPage = new Page<>(page, pageSize);
//        Long currentId = BaseContext.getCurrentId();
//        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Orders::getUserId, currentId);
////        //我们需要一个总商品数据，我们可以查出所有的订单号，然后根据订单号去查询所有的商品总数据
////        AtomicReference<Integer> sumNum = new AtomicReference<>(0);
////        // Integer sumNum = null;
//        List<Orders> list = ordersService.list(lqw);
//        list.stream().map(orders -> {
//            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper=new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(OrderDetail::getOrderId,orders.getId());
//            List<OrderDetail> orderDetails = orderDetailService.list(lambdaQueryWrapper);
//            return null;
//        });
//        ordersService.page(ordersPage, lqw);
//        return R.success(ordersPage);
//    }


    /**
     * 实现后台数据订单展示
     *
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String number,
                        String beginTime, String endTime) {
        Page pages = new Page<>(page, pageSize);

        //构造条件
        LambdaQueryWrapper<Orders> lqw = new LambdaQueryWrapper<>();
        if (number != null) {
            lqw.like(Orders::getNumber, number);
        }
        if (beginTime != null) {
            lqw.ge(Orders::getOrderTime, beginTime);
            lqw.lt(Orders::getOrderTime, endTime);
        }
        ordersService.page(pages, lqw);
        return R.success(pages);
    }
}
