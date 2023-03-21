package com.mybatis.bean;

import com.baomidou.mybatisplus.annotation.TableName;


import com.mybatis.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:luosheng
 * @Date:2023/3/2 13:23
 * @Description:
 */
//这届配置前缀
//@TableName("news_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private UserType usertype;


}
