package com.mybatis.bean;

import com.mybatis.enums.UserType;
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
public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private Integer userType;
    private Role role;
}
