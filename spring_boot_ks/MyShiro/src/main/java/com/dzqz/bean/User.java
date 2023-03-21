package com.dzqz.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:luosheng
 * @Date:2023/3/3 14:52
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("news_user")
public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private Integer userType;
    private String perms;
}
