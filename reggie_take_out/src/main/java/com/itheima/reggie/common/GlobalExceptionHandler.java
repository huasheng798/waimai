package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author:luosheng
 * @Date:2023/3/8 14:28
 * @Description:
 */
//这个就是要拦截的那些注解，现在就是拦截标记了RestController注解的类 后面同理
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 这个其实就是给那个类加异常处理，假如我们上面那些注解类中有地方抛出SQLIntegrityConstraintViolationException 异常的就用这里处理
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e){
        //这里就把异常的信息拿出来
        log.error(e.getMessage());
        //这里做个判断，如果异常信息包含什么什么，就抛出相等的异常处理
        //这里不就是是否包含这些信息，如果异常信息中包含，这个不就是name主键，重复，不能添加，就给他返回账号不能重复
        //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        //上面就是我们报错语句，getmessage 现在就是这个东西
        if (e.getMessage().contains("Duplicate entry")) {
            //首先我们以字符串空格分割
            String[] split = e.getMessage().split(" ");
            //拿到报错的名字
            String msg=split[2];
            return R.error(msg+"已存在");

        }
        return R.error("未知的错误");
    }


    @ExceptionHandler(CustomException.class)
    public R<String> exceptioni(CustomException exception){
        log.info(exception.getMessage());
        return R.error(exception.getMessage());
    }
}
