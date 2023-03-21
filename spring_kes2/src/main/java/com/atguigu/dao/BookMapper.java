package com.atguigu.dao;

import com.atguigu.bean.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/17 16:18
 * @Description:图书类
 */
@Mapper
public interface BookMapper {

    /**
     * 全查功能，渲染页面
     *
     * @return
     */
    List<Book> selectBook();

    /**
     * 添加书籍功能
     *
     * @param book
     * @return
     */
    int insertByBook(Book book);

    /**
     * 实现修改窗口中单条数据渲染页面
     *
     * @param id
     * @return
     */
    Book selectBookById(String id);

    /**
     * 实现修改功能
     *
     * @param book
     * @return
     */
    int updateByBook(Book book);

    /**
     * 实现删除功能
     *
     * @param id
     * @return
     */
    int deleteBookById(String id);

    /**
     * 实现模糊查询+分页功能
     *
     * @return
     */
    List<Book> selectByBook(@Param("name") String name,@Param("type")
            String type,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);

    /**
     * 实现总数的查询
     * @param name
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    Integer selectCount(@Param("name") String name,@Param("type")
            String type,@Param("pageNo") Integer pageNo,@Param("pageSize") Integer pageSize);
}
