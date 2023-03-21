package com.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;
import lombok.Getter;

/**
 * @Author:luosheng
 * @Date:2023/3/2 15:10
 * @Description:
 */
@Getter
public enum UserType {
    administrator(0,"管理员"),
    ptuser(1,"普通用户");
    @EnumValue  //用来标记数据库存的值
    private Integer type;
    private String typeName;

    UserType() {
    }

    UserType(Integer type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }
}
