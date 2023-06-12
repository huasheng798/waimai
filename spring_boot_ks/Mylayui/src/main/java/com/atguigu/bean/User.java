package com.atguigu.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.naming.ldap.PagedResultsControl;

/**
 * @Author:luosheng
 * @Date:2023/3/23 21:28
 * @Description:
 */
@Data
@TableName("news_user")
public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private Integer userType;
    private String perms;
}
