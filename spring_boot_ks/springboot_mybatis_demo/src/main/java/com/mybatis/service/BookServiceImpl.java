package com.mybatis.service;

import com.mybatis.bean.Book;
import com.mybatis.mapper.BookDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/2 13:10
 * @Description:
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> queryBookList() {
        return bookDao.queryBookList();
    }
}
