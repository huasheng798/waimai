package com.mybatis.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/3/3 17:20
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private String id;
    private String roleName;
    private List<User> users;
}
