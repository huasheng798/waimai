package com.mybatis.config;

import com.mybatis.controller.Hello;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Author:luosheng
 * @Date:2023/3/9 15:48
 * @Description:
 */
@Slf4j
//@WebInitParam(name = "myInitParam",value = "/**")
public class MyInterceptor implements HandlerInterceptor {
    //该方法在目标方法执行前调用目标方法就是我们controller中的方法
    //该方法在控制器处理请求方法之前，执行返回boolean的值,同时其返回值，是否中断后续操作
    //如果返回值为true 就代表可以执行，返回false表示中断后续操作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String usreid = (String) request.getSession().getAttribute("userid");
        log.info("pre方法执行{},{}当前用户:{},", new Date(), request.getServletPath(),usreid);
        if (usreid != null && usreid != "") {
            //
            return true;
        }
        //没有做登录拦截,返回登录页
        request.getRequestDispatcher("/user/c").forward(request, response);

        return false;
    }

    /**
     * 目标方法执行之后，
     * 注意:该方法在控制器处理器方法调用之后，解析视图之前
     * 用处:可以使用此方法，可以对请求域中的模型和视图进一步改进
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("post方法执行{},{}", new Date(), request.getServletPath());
    }

    //在页面渲染之后，
    //一般用来进行日志的记录,以及资源处理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("after方法执行{},{}", new Date(), request.getServletPath());
    }
}
