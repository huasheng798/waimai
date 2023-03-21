package com.example.dao;

import com.example.bean.Book;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 15:49
 * @Description:
 */

public interface BookMapper {
    List<Book> selectListBook();

    int deleteBookById(List<String> id);
}
