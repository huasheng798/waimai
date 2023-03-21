package com.atguigu.controller;

import com.atguigu.bean.User;
import com.atguigu.comstant.Constant;
import com.atguigu.domain.ReturnObject;
import com.atguigu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

/**
 * @Author:luosheng
 * @Date:2023/2/16 21:12
 * @Description:
 */
@Controller
public class UserController {

    @Autowired
    private UserService serviceUser;

    /**
     * 注册功能
     *
     * @param user
     * @return
     */
    @RequestMapping("/insertuser")
    public @ResponseBody
    Object insertUser(User user) {
        System.out.println("测试" + user.getUserName());

        user.setId(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
        System.out.println(String.valueOf(UUID.randomUUID()).replaceAll("-", ""));
        int i = serviceUser.enrollUser(user);
        ReturnObject returnObject = new ReturnObject();
        try {
            if (i > 0) {
                //成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                System.out.println("测试controller成功");
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("错误(新增失败)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("错误(新增失败)");
        }
        System.out.println("测试");
        return returnObject;
    }

    /**
     * 进入登录页面
     *
     * @param mav
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(ModelAndView mav) {
        mav.setViewName("login");
        return mav;
    }


    /**
     * 登录功能
     * (查询数据库进行数据比对，账户密码是否正确)
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/loginuser")
    public @ResponseBody
    Object loginuser(String userName, String password) {
        System.out.println(userName);
        System.out.println(password);
        ReturnObject returnObject = new ReturnObject();
        User user = serviceUser.SelectByUser(userName, password);
        int i = serviceUser.selectByNameOnPassword(userName, password);
      //  System.out.println("测试" + user.getId());
        System.out.println("测试aaaaaa"+i);
        if (i ==0) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("错误(登录失败)");
        } else {
            System.out.println("测试登录成功");
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }
        return returnObject;
    }

}
