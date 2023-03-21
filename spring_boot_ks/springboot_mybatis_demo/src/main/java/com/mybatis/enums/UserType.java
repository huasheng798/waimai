package com.mybatis.enums;

import lombok.Getter;

/**
 * @Author:luosheng
 * @Date:2023/3/3 16:24
 * @Description:
 */
@Getter
public enum UserType {
    administrator(0, "管理员"),
    ptuser(1, "普通用户");

    private Integer type;
    private String typeName;

    UserType() {
    }

    UserType(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}