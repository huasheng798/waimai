package com.mybatis.mapper;

import com.mybatis.bean.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/2 13:06
 * @Description:
 */
@Mapper
@Repository//给spring托管
public interface BookDao {
    /**
     * 查询所有功能
     * @return
     */
    List<Book> queryBookList();

    /**
     * 实现单查功能
     * @param id
     * @return
     */
    Book queryBookById(Integer id);
}
