package com.itheima.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

/**
 * @Author:luosheng
 * @Date:2023/3/8 21:56
 * @Description:配置公共字段，其中要在实体类配置@TableFile
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 当执行插入操作的时候会自动填充(也就是新增操作)
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
       log.info("公共字段自动填充[update]");
       log.info(metaObject.toString());
        metaObject.setValue("updateTime",LocalDateTime.now());
        long id = Thread.currentThread().getId();
        log.info("当前公共字段id为{}",id);
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
