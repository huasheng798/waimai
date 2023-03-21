package com.atguigu.service;

import com.atguigu.bean.Book;
import com.atguigu.bean.User;
import com.atguigu.dao.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/17 16:28
 * @Description:
 */
@Service()
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<Book> BookList() {
        return bookMapper.selectBook();
    }

    @Override
    public int insertByBook(Book book) {
        return bookMapper.insertByBook(book);
    }

    @Override
    public Book selectBookById(String id) {
        return bookMapper.selectBookById(id);
    }

    @Override
    public int UpdateBookById(Book book) {
        return bookMapper.updateByBook(book);
    }

    @Override
    public int deleteBookById(String id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public List<Book> selectByBook(String name, String type, Integer pageNo, Integer pageSize) {
        return bookMapper.selectByBook(name,type, pageNo, pageSize);
    }

    @Override
    public Integer selectCount(String name, String type, Integer pageNo, Integer pageSize) {
        return bookMapper.selectCount(name,type, pageNo, pageSize);
    }
}
