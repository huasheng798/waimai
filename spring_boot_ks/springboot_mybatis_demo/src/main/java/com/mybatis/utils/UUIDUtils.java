package com.mybatis.utils;

import java.util.UUID;

/**
 * @Author:luosheng
 * @Date:2023/1/24 20:39
 * @Description:
 */
public class UUIDUtils {


    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
