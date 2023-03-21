package com.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringbootMybatisDemoApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {

        Connection connection = dataSource.getConnection();
        System.out.println(connection + "====================================");

        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("最大连接数" + druidDataSource.getMaxActive());
        System.out.println("最小连接数" + druidDataSource.getMinIdle());
        connection.close();
    }

}
