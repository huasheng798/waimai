package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;

import javax.annotation.processing.Filer;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:luosheng
 * @Date:2023/3/7 21:53
 * @Description:实现检查用户是否已经完成登录
 */
//filterName就是过滤器名字，urlPatterns:要拦截的路径
//使用webFilter WebListener WebServlet 都需要在程序的入口配置@ServletComponentScan注解才可以使用
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
//@Order  //这个是指定过滤器执行的顺序，值越大越靠后执行
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        //这里需要向下转型一下
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1.获取本次请求的URI
        String requestURI = request.getRequestURI();
        //我们在这还需要一个写一个不需要拦截的路径
        String[] urls = new String[]{
                //一个是登录页的路径，
                "/employee/login",
                //一个是退出登录
                "/employee/logout",
                //还有静态的资源我们也不需要拦截,我们只需要拦截他们页面中ajax发送的请求数据
                "/backend/**",
                //还有一些移动端的静态页面
                "/front/**",
                //暂时过滤图片上传下载功能，用于测试
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };

        //2.判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //3.如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求{}不需要处理", requestURI);
            //使用该方法可以调用过滤器链中的下一个 Filter 的 doFilter() 方法
            // 若该 Filter 是链中最后一个过滤器，则调用目标资源的 service() 方法。
            // filterChain.doFilter其实就是放行的资源
            filterChain.doFilter(request, response);
            //放行之后以下代码就不需要执行了直接return
            return;
        }
        Object employee = request.getSession().getAttribute("employee");
        log.info("过滤器中打印信息{}", employee);
        //4-1.判断登录状态，如果已登录，则直接放行
        if (employee!= null) {
            long id = Thread.currentThread().getId();
            log.info("过滤器当前线程{}",id);
            Long empId = (Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("用户已登录，用户id为{}", employee);
            // filterChain.doFilter其实就是放行的资源
            filterChain.doFilter(request, response);
            //放行之后以下代码就不需要执行了直接return
            return;
        }
//4-2.判断(这个是移动端的用户)登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("user")!= null) {
            long id = Thread.currentThread().getId();

            log.info("过滤器当前线程{}",id);
            Long userId = (Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            // filterChain.doFilter其实就是放行的资源
            filterChain.doFilter(request, response);
            //放行之后以下代码就不需要执行了直接return
            return;
        }
        //5.如果为登录则返回未登录结果,我们可以看前端request.js文件中写了一个响应式拦截器，所以要使用输出流方式想客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
        //这里{}是一个占位符，他会将后面的内容输出到当前{}位置
        //   log.info("拦截到了请求 {}", httpServletRequest.getRequestURI());
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * 其实意思就是，遍历一下我们那个数组，我们当前访问的路径是不是其中之一
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            //这其实就是一个比较器，来比较路径， 返回true则相同，
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
