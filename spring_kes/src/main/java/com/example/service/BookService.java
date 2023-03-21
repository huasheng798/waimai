package com.example.service;


import com.example.bean.Book;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 15:49
 * @Description:
 */
public interface BookService{
    List<Book> selerctListBook();

    int deleteBookById(List<String> id);
}
