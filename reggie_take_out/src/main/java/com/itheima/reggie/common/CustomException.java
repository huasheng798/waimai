package com.itheima.reggie.common;

/**
 * @Author:luosheng
 * @Date:2023/3/9 12:33
 * @Description:自定义业务异常类
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
