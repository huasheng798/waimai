package com.atguigu.service;

import com.atguigu.bean.Book;
import com.atguigu.bean.User;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/17 16:28
 * @Description:
 */
public interface BookService {
    /**
     * 登录主页面
     *
     * @return
     */
    List<Book> BookList();

    /**
     * 实现新增功能
     *
     * @param book
     * @return
     */
    int insertByBook(Book book);

    /**
     * 实现修改窗口数据渲染页面
     *
     * @param id
     * @return
     */
    Book selectBookById(String id);

    /**
     * 实现修改功能
     *
     * @param book
     * @return
     */
    int UpdateBookById(Book book);

    /**
     * 实现删除功能
     *
     * @param id
     * @return
     */
    int deleteBookById(String id);

    /**
     * 实现模糊查以及分页功能
     *
     * @param name,    type
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Book> selectByBook(String name, String type, Integer pageNo, Integer pageSize);

    /**
     * 实现查询总数功能
     *
     * @param name
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    Integer selectCount(String name, String type, Integer pageNo, Integer pageSize);
}
