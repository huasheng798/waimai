package com.atguigu.mvc.controller;

/**
 * @Auther:luosheng
 * @Date:2022/12/05/16:02
 * @Description:
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    //  "/"-->/WEB-INF/templates/index.html
    /*
    上面是我们原本的路径 但是我们配置i文件中配置了这个 只需要写前缀和后缀之间的内容即可
  <!-- 视图前缀 -->
  <property name="prefix" value="/WEB-INF/templates/"/>
  <!-- 视图后缀 -->
  <property name="suffix" value=".html"/>*/
    @RequestMapping(value = "/")
    public String index() {
        //返回视图名称
        return "index";
    }

    //设置注解让他匹配 必须和请求地址一致
    @RequestMapping("/target")
    public String toTarget() {
        return "target";
    }

}
