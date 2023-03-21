package com.atguigu.mvc.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @Auther:luosheng
 * @Date:2022/12/12/11:24
 * @Description:
 */
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    /*
     * 指定spring的配置类
     * */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /*
     * 指定SpringMVC的配置类
     * */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    /*
     * 指定DispatcherServlet的映射规则 就是url-pattern
     * */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /*设置过滤器*/
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{characterEncodingFilter,hiddenHttpMethodFilter};
    }
}
