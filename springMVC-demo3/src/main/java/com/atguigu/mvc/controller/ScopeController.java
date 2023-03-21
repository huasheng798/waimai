package com.atguigu.mvc.controller;

import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Auther:luosheng
 * @Date:2022/12/07/22:06
 * @Description:
 */
@Controller
public class ScopeController {
    @RequestMapping("/testRequestByServletAPI")
    public String API(HttpServletRequest request) {
        request.setAttribute("testRequestScope", "hello ServletAPI");
        return "success";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView mav = new ModelAndView();
        //处理模型数据,即向请求域request共享数据
        mav.addObject("testRequestScope", "hello,ModelAndView");
        //设置视图名称
        mav.setViewName("success");
        return mav;

    }

    @RequestMapping("/testModel")
    public String testModel(Model model) {
        model.addAttribute("testRequestScope", "hello,Model");
        return "success";
    }

    @RequestMapping("/testMap")
    public String testMap(Map<String, Object> map) {
        map.put("testRequestScope", "helloMap");
        return "success";
    }

    @RequestMapping("/testSession")
    public String testSession(HttpSession session) {
        session.setAttribute("testSessionScope", "hello,session");
        return "success";
    }

    @RequestMapping("/test_view")
    public String testview(){
    return "test_view";
    }
}
