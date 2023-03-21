package com.Mylayui.controller;

import com.Mylayui.bean.User;
import com.Mylayui.service.UserService;
import com.Mylayui.utils.LayuiReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/5 10:34
 * @Description:
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 来到主页面
     *
     * @return
     */
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    /**
     * 首次来到展示页
     *
     * @return
     */
    @RequestMapping("/user_account")
    public String user_account() {
        return "user_account";
    }

    /**
     * 测试渲染页面
     *
     * @return
     */
    @RequestMapping("/bookList")
    @ResponseBody
    public LayuiReturn<List<User>> selecList(@PathParam("page") Integer page, @PathParam("limit") Integer limit) {
        LayuiReturn<List<User>> listLayuiReturn = new LayuiReturn<>();
        //page 当前页   limit 每页页数
        //查询用户数量
        int count = userService.countUser();
        //当前页的下标
        int formIndex = (page - 1) * limit;
        List<User> users = userService.queryListUser(formIndex, limit);
        listLayuiReturn.setCount(count);
        listLayuiReturn.setData(users);
        return listLayuiReturn;
    }

    /**
     * 实现删除功能
     * @param id
     * @return
     */
    @RequestMapping("/user/delete")
    public boolean deleteUser(@PathParam("id") Integer id) {
        boolean b = userService.deleteUserById(id);
        return b;
    }


    /**
     * 实现修改页面渲染数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/userUpdate")
    public ModelAndView updateSelect(@PathParam("id") Integer id) throws ParseException {
        ModelAndView mav = new ModelAndView();
        System.out.println(id);
        User user = userService.selectUserById(id);
        mav.setViewName("userUpdate");
        mav.addObject("user", user);
        return mav;
    }


    //实现修改待写
    @RequestMapping("/user/update")
    @ResponseBody
    public Integer update() {

        return 1;
    }

}
