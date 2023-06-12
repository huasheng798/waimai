package com.atguigu.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:luosheng
 * @Date:2023/3/5 10:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LayuiReturn<T> {
    private int code=0;
    private String msg="ok";
    private int count;//总记录数
    private T data;//数据对象
}
