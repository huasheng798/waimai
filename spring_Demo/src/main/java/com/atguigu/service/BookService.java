package com.atguigu.service;

import com.atguigu.bean.Book;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 13:12
 * @Description:
 */

public interface BookService {
    List<Book> queryBook();
}
