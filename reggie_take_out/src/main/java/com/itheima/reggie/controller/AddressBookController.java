package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.AddressBook;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.AddressBookService;
import com.itheima.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/16 8:54
 * @Description:
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<String> add(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("新增参数:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success("新增成功");
    }

    /**
     * 渲染地址浦页面
     *
     * @param addressBook 其实这个前端没有传来任何参数，我们只是要根据当前登录用户的id查询
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook) {
        //取出当前用户的id
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("当前渲染地址谱数据,{}", addressBook);

        //构造个查询条件，1.是当前登录用户下的地址，2.按照更新时间降序
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();
        lqw.eq(AddressBook::getUserId, addressBook.getUserId());
        lqw.orderByDesc(AddressBook::getUpdateTime);

        List<AddressBook> list = addressBookService.list(lqw);

        return R.success(list);
    }

    /**
     * 设置默认地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    @Transactional
    public R<String> adefault(@RequestBody AddressBook addressBook) {
        //首先要明确就是我们默认地址只能有一个，就是我们字段中的is_default只能有1个为1的
        //所以我们要在接收到id前把当前登录用户的所有isdefault字段都先设置为0，然后再根据传来的id把默认地址修改称1
        log.info("当前修改状态参数:{}", addressBook.getId());
        Long userId = BaseContext.getCurrentId();
        LambdaUpdateWrapper<AddressBook> uplqw = new LambdaUpdateWrapper<>();
        uplqw.set(AddressBook::getIsDefault, 0);//先全部设置为0
        uplqw.eq(AddressBook::getUserId, userId);
        addressBookService.update(uplqw);

        //进行修改
        LambdaUpdateWrapper<AddressBook> lqw = new LambdaUpdateWrapper<>();
        lqw.set(AddressBook::getIsDefault, 1);
        lqw.eq(AddressBook::getId, addressBook.getId());
        addressBookService.update(lqw);
        return R.success("修改成功");
    }

    /**
     * 进入修改页面渲染修改页面
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> updateByList(@PathVariable("id") Long id) {
        log.info("当前渲染修改页面参数：{}",id);
        AddressBook addressBook = addressBookService.getById(id);
        return R.success(addressBook);
    }

    /**
     * 实现修改数据功能
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody AddressBook addressBook){
        log.info("修改数据：{}",addressBook);
        addressBookService.updateById(addressBook);
        return R.success("修改成功");
    }

    /**
     * 实现支付页面中的信息
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> Mydefault(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AddressBook::getUserId,currentId);
        lambdaQueryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook one = addressBookService.getOne(lambdaQueryWrapper);
        return R.success(one);
    }
}
