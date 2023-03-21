package com.atguigu.controller;

import com.atguigu.bean.Book;
import com.atguigu.bean.User;
import com.atguigu.comstant.Constant;
import com.atguigu.domain.ReturnObject;
import com.atguigu.service.BookService;
import org.apache.ibatis.annotations.ResultType;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/2/17 16:16
 * @Description:
 */
@Controller
public class BookController {

    @Autowired

    private BookService bookService;


    @RequestMapping("/a")
    public ModelAndView a(ModelAndView mav) {
        List<Book> books = bookService.BookList();

        mav.addObject("books", books);
        mav.setViewName("bookShow");

        return mav;
    }

    /**
     * 登录成功，进入页面，渲染页面
     *
     * @return
     */
    @RequestMapping("/bookShow")
    public ModelAndView AllBook(ModelAndView mav) {
//        List<Book> books = bookService.BookList();
        int pageNo = (1 - 1) * 10;
        List<Book> books = bookService.selectByBook(null, null, pageNo, 10);
        mav.addObject("books", books);
        mav.setViewName("bookShow");
        Integer count = bookService.selectCount(null, null, pageNo, 10);
        mav.addObject("count", count);
        mav.addObject("pageNo", 1);
        return mav;
    }

    /**
     * 实现书籍新增功能
     *
     * @param book
     * @return
     */
    @RequestMapping("/insertbook")
    @ResponseBody
    public Object insertBook(Book book) {

        int i = bookService.insertByBook(book);
        ReturnObject returnObject = new ReturnObject();
        try {
            if (i > 0) {
                System.out.println("测试数据新增成功");
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙(新增失败)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙(新增失败)");
        }

        return returnObject;
    }

    /**
     * 渲染修改模态窗口
     *
     * @param id
     * @return
     */
    @RequestMapping("/selectBookById")
    public @ResponseBody
    Object selectById(String id) {
        Book book = bookService.selectBookById(id);
        return book;
    }

    /**
     * 实现修改功能
     *
     * @param book
     * @return
     */
    @RequestMapping("/updateBookById")
    public @ResponseBody
    Object update(Book book) {
        System.out.println("修改测试id" + book.getId());
        ReturnObject returnObject = new ReturnObject();
        int i = bookService.UpdateBookById(book);
        if (i > 0) {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("修改失败");
        }
        return returnObject;
    }

    /**
     * 实现删除流程
     *
     * @param id
     * @return
     */
    @RequestMapping("deleteBookById")
    @ResponseBody
    public Object deleteBookById(String id) {
        System.out.println("测试controller删除" + id);
        int i = bookService.deleteBookById(id);
        ReturnObject returnObject = new ReturnObject();
        try {
            if (i > 0) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("业务繁忙(删除失败)");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("业务繁忙(删除失败)");
        }
        return returnObject;
    }

    /**
     * 实现模糊查询以及分页查询
     * <p>
     * 实现分析:我们需要的数据为 模糊查询的name和type还有分页需要的总页数(使用总数除于10)
     * 和总数(需要一个sql查询出来) 每页数量为10
     * 分页计算总页数算法：总页数=（总数-1）/每页数量+1
     * 总页数=（总数+每页数量-1）/每页数量
     * pageNo当前页  pageSize每页显示条数
     *
     * @return
     */
    @RequestMapping("/selectByBook")
    @ResponseBody
    public Object selectByBook(String name, String type, Integer pageNo, Integer pageSize) {
        pageNo = (pageNo - 1) * pageSize;
        List<Book> books = bookService.selectByBook(name, type, pageNo, pageSize);
        Integer count = bookService.selectCount(name, type, pageNo, pageSize);
        System.out.println(count);


        Map<String, Object> retMap = new HashMap<>();
        retMap.put("books", books);
        retMap.put("count", count);

        return retMap;
    }

    @RequestMapping("/totalPage")
    public ModelAndView totalPage(Integer pageNo) {
        System.out.println(pageNo);
        Integer pageSize = 10;
        pageNo++;

        pageNo = (pageNo - 1) * pageSize;
        Integer count  = bookService.selectCount(null, null, pageNo, pageSize);
        List<Book> books = bookService.selectByBook(null, null, pageNo, pageSize);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bookShow");
        mav.addObject("books", books);
        if (count!=null){
            mav.addObject("count", count);
        }

        mav.addObject("pageNo", pageNo);

        return mav;
    }
    @RequestMapping("/nextPage")
    public ModelAndView nextPage(Integer pageNo) {
        System.out.println(pageNo);
        Integer pageSize = 10;
        pageNo--;

        pageNo = (pageNo - 1) * pageSize;
        Integer count  = bookService.selectCount(null, null, pageNo, pageSize);
        List<Book> books = bookService.selectByBook(null, null, pageNo, pageSize);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("bookShow");
        mav.addObject("books", books);
        if (count!=null){
            mav.addObject("count", count);
        }

        mav.addObject("pageNo", pageNo);

        return mav;
    }
}
