package com.example.service;

import com.example.bean.Book;
import com.example.dao.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 15:55
 * @Description:
 */
@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> selerctListBook() {
        return bookMapper.selectListBook();
    }

    @Override
    public int deleteBookById(List<String> id) {
        return bookMapper.deleteBookById(id);
    }
}
