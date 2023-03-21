package com.mybatis.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.bean.Role;
import com.mybatis.bean.User;
import com.mybatis.service.UserService;
import com.mybatis.utils.UUIDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/2 16:50
 * @Description:使用 jdbcTemplate;
 */
@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    //springboot 封装jdbc

    @Resource
    private JdbcTemplate jdbcTemplate;

//    @GetMapping("/")
//    @ResponseBody
//    public List<Map<String, Object>> select(){
//        String sql="SELECT * from news_user";
//        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
//        return maps;
//    }

    @GetMapping("/")
    @ResponseBody
    public List<User> select() {
        List<User> users = userService.queryListUser();
        return users;
    }

    @GetMapping("/role")
    @ResponseBody
    public List<Role> a() {
        List<Role> roles = userService.selectListRole();
        return roles;
    }


    @PutMapping("/{userName}/{id}")
    @ResponseBody
    public String update(@PathVariable("userName") String userName,
                         @PathVariable("id") String id) {
        int i = userService.updateUser(userName, id);
        if (i > 0) {
            return "修改成功";
        }
        return "修改失败";
    }


    @DeleteMapping("/")
    @ResponseBody
    public String delete(@RequestParam("myId") String id) {
        int i = userService.deleteUser(id);
        if (i > 0) {
            return "删除成功";
        }
        return "删除失败";
    }


    @PostMapping("/")
    @ResponseBody
    public String insert() {
        User user = new User();
        user.setId(UUIDUtils.getUUID());
        user.setUserName("成功了");
        user.setPassword("12132132");
        int i = userService.addUser(user);
        if (i > 0) {
            return "新增成功";
        }
        return "新增失败";
    }
//
//    @PostMapping("/{id}")
//    @ResponseBody
//    public Object insertById(@PathVariable("id") String id) {
//        String sql="insert into  news_user values(?,'这是name','这是密码','邮箱@qq.com',1)";
//        int update = jdbcTemplate.update(sql,id);
//        return update;
//    }
//    @PutMapping("/{id}")
//    @ResponseBody
//    public Object updateById(@PathVariable("id")  String id) {
//        String sql="update   news_user set userName=?,password=?,email=? where id=?";
//        Object[] o={"wangxxx","787989798","wang@qq.com",id};
//        int update = jdbcTemplate.update(sql,o);
//        if (update>0){
//            return "修改成功了";
//        }
//        return "修改失败了";
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseBody
//    public Object delete(@PathVariable("id")  String id) {
//        String sql="delete  from  news_user where id=?";
//        int update = jdbcTemplate.update(sql,id);
//        if (update>0){
//            return "删除成功了";
//        }
//        return "删除失败了";
//    }


    //进入登录页
    @RequestMapping("/c")
    public String log() {
        return "login";
    }

    /**
     * 实现页面登录功能
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public String login(String username, String password, HttpServletRequest request) {

        User user = userService.loginUser(username, password);
        if (user != null) {
            //登录成功
            request.getSession().setAttribute("userid", user.getId());
            System.out.println(request.getSession().getAttribute("userid"));
            return "redirect:/user/a";
        }
        return "login";
    }

    /**
     * 实现退出登录功能
     * @return
     */
    @RequestMapping("/remove")
    public String remove() {
        //清除session并返回登录页
        request.getSession().removeAttribute("userid");
        System.out.println("当前为退出页面"+request.getSession().getAttribute("userid"));
        return "login";
    }

    /**
     * 跳转到用户详情页
     *
     * @param model
     * @return
     */
    @RequestMapping("/a")
    //其中RequestParam那个参数1就是第一次进来没有传值，我们就叫他默认为1
    public String A(Model model, @RequestParam(value = "pageNum",
            defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 3);
        List<User> users = userService.queryListUser();
        //会帮我们实现前面设置的参数，会处理users,
        PageInfo<User> page = new PageInfo<>(users);
        List<Role> queryrole = userService.queryrole();
        model.addAttribute("users", page);
        model.addAttribute("queryrole", queryrole);
        return "a";
    }

    /**
     * 实现单查效果
     *
     * @param id
     * @return
     */
    @RequestMapping("/userview")
    public String view(String id) {
        User user = userService.queryUserById(id);
        request.setAttribute("user", user);
        return "userview";
    }

    /**
     * 实现修改中的查看详情功能
     *
     * @param id
     * @return
     */
    @RequestMapping("/updatequery")
    @ResponseBody
    public User updateQuery(String id) {
        User user = null;
        System.out.println("成功");
        try {
            user = userService.queryUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据出错了x_x");

        }
        return user;
    }

    /**
     * 实现修改功能
     *
     * @param id
     * @param userName
     * @param email
     * @param userType
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(String id, String userName, String email, String userType) {
        String rtu = "500";
        try {
            Integer integer = userService.updateUserById(id, userName, email, userType);
            if (integer > 0) {
                rtu = "200";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败了...");
        }
        return rtu;
    }

    /**
     * 实现删除功能
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String deleteById(String id) {
        int i = userService.deleteUser(id);
        if (i == 0) {
            //删除失败
        }
        return "redirect:/user/a";
    }

    @RequestMapping("/newuser")
    @ResponseBody
    public String news(User user) {
        System.out.println(user + "aaaaaaaaaaaaaaaa");
        String rtur = "500";
        user.setId(UUIDUtils.getUUID());
        try {
            Integer integer = userService.insertUser(user);
            if (integer > 0) {
                rtur = "200";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("新增失败...");
        }
        return rtur;
    }

}
