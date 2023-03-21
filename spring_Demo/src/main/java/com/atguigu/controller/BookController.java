package com.atguigu.controller;

import com.atguigu.bean.Book;
import com.atguigu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 15:11
 * @Description:
 */
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @RequestMapping("/hello")
    public @ResponseBody
    String selectListBook() {
        List<Book> books = bookService.queryBook();
        for (Book b : books
        ) {
            System.out.println(b);
        }
        return books.toString();
    }
}
