package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.PushBuilder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @Author:luosheng
 * @Date:2023/3/7 16:39
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    //我们可以打开浏览器查看我们请求的信息为json数据一个username,一个password所以要使用@RequestBody注解,且如果用实体类接收
    //他的json字段username...要和实体类中一致
    //request就是为了将用户信息存储到session中
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //  * 1.将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //  * 2.根据页面提交的用户名username查询数据库
        //这两句话不就是mybatisplus中的一个简单操作
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //这里用getOne是因为我们username是唯一主键，查到了就有
        Employee emp = employeeService.getOne(queryWrapper);
        // * 3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败了x_x");
        }
        //  * 4.密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误x_x");
        }
        //    * 5.查看员工状态,如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("员工已禁用");
        }
        log.info("登录成功{}", emp.getId());
        //  * 6.登录成功，将员工id存入Session并返回登录成功结果

        request.getSession().setAttribute("employee", emp.getId());

        log.info("登录成功{}", emp);
        return R.success(emp);
    }

    /**
     * 员工退出登录操作
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> login(HttpServletRequest request) {
        //清除session中的登录id
        HttpSession session = request.getSession();
        session.removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 员工信息新增功能
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工,员工信息:{}", employee.toString());
        //进行初始化密码设置，并且进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //下面这一块我们直接使用公共字段填充来写
        //java8新加的时间，反正就性能高，线程安全  //修改时间暂时和新增时间一样
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateTime(LocalDateTime.now());
//        //获得当前创建者的id也就是当前登录的id
//        Long empId = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询功能
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        log.info("分页接收数据page:{},pageSize:{},name:{}", page, pageSize, name);

        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //这其实就是isnotEmpxxx 判断name是否存在，存在就使用 Employee::getName实体类中也就是数据库中的值 name真实值
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        //添加排序条件
        //这个形式其实就是 取出修改的时间
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询,把准备好的两个条件拿过来
        employeeService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 实现修改员工状态功能,这个还能实现修改员工信息功能
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        //log.info(employee.toString());
       // Long sessionId = (Long) request.getSession().getAttribute("employee");
      //  employee.setUpdateUser(sessionId);
      //  employee.setUpdateTime(LocalDateTime.now());
        long id = Thread.currentThread().getId();
        log.info("controller线程id{}",id);
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据员工查询员工信息....{}",id);
        Employee employee = employeeService.getById(id);

        return R.success(employee);
    }
}
