package com.atguigu.dao;

import com.atguigu.bean.Book;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 13:07
 * @Description:
 */
public interface BookDao {
    List<Book> selectBook();
}
