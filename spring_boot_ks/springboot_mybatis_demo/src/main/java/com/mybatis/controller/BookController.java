package com.mybatis.controller;

import com.mybatis.bean.Book;
import com.mybatis.mapper.BookDao;
import com.mybatis.service.BookService;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:luosheng
 * @Date:2023/3/2 13:11
 * @Description:
 */

//@RestController   //相当于controller注解加上responbody 注解 全部方法都是返回json对象

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    @ResponseBody
    public List<Book> selectList(){
        List<Book> books = bookService.queryBookList();

        return books;
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/")
    @ResponseBody
    public Object insertById() {
        String sql="insert into tbl_book values(default,'学java','aa','30')";
        int update = jdbcTemplate.update(sql);
        return update;
    }
    @PutMapping("/{id}")
    @ResponseBody
    public Object insertById(@PathVariable("id")  String id) {
        String sql="update  tbl_book set type=?,name=?,description=? where id=?";
        Object[] o={"心理罪",48,18,id};
        int update = jdbcTemplate.update(sql,o);
        if (update>0){
            return "修改成功了";
        }
        return "修改失败了";
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Object delete(@PathVariable("id")  String id) {
        String sql="delete  from tbl_book where id=?";
        int update = jdbcTemplate.update(sql,id);
        if (update>0){
            return "删除成功了";
        }
        return "删除失败了";
    }





}
