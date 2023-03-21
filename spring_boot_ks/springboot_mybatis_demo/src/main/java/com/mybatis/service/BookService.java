package com.mybatis.service;

import com.mybatis.bean.Book;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/2 13:10
 * @Description:
 */
public interface BookService {
    /**
     * 实现全查功能
     * @return
     */
    List<Book> queryBookList();
}
