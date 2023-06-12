package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.controller.OrderController;
import com.itheima.reggie.entity.*;
import com.itheima.reggie.mapper.OrdersMapper;
import com.itheima.reggie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author:luosheng
 * @Date:2023/3/17 12:50
 * @Description:
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional
    public void submit(Orders orders) {
        //我们前端传来了一个addressbook，还有支付方式，和描述，然后我们还可以获取用户的id，
        //我们要操作的方式就是往orders中添入地址信息，和orderdetail的详细菜品
        Long currentId = BaseContext.getCurrentId();
        //查询用户信息
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, currentId);
        User user = userService.getOne(userLambdaQueryWrapper);
        //根据地址id查询地址
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());

        if (addressBook == null) {
            throw new CustomException("地址错误，无法下单");
        }

        //存储详细菜品  先根据用户查询当前购物车所有的信息
        //算现金这个使用只是为了保持原子性操作
        AtomicInteger atomicInteger = new AtomicInteger();

        //使用个mybatisplus中雪花算法生成个订单号
        long orderId = IdWorker.getId();
        //根据id查询当下的所有菜品
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        List<ShoppingCart> list = shoppingCartService.list();


        //这里就是为了把购物车的东西遍历赋值给菜品详细，然后最后面那一块是算了个价钱，然后赋值给我们orders
        List<OrderDetail> orderDetails = list.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setOrderId(orderId);
            orderDetail.setDishId(shoppingCart.getDishId() == null ? null : shoppingCart.getDishId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId() == null ? null : shoppingCart.getSetmealId());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setAmount(shoppingCart.getAmount());
            //其中multiply是乘的意思 大致就是getAmount获取每个的价钱，然后称与它的数量，然后给他转换称intValue
            atomicInteger.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        //这块订单号不知道从哪里拿
        orders.setNumber(String.valueOf(orderId));
        //先要执行个insert语句
        orders.setUserId(currentId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        //这里还要写一个实收金额 ，需要用详细菜品算，所以我们在写这个前提要先写菜品详细
        //==============================这还要写金额
        orders.setAmount(new BigDecimal(atomicInteger.get()));
        orders.setPhone(user.getPhone());
        //这里我们就拼接一下地区
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        //调用当前方法保存数据
        this.save(orders);

        orderDetailService.saveBatch(orderDetails);


        //清空一下购物车
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
    }
}
