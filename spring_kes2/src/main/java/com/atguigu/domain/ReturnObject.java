package com.atguigu.domain;

/**
 * @Author:luosheng
 * @Date:2023/2/17 10:14
 * @Description:
 */


public class ReturnObject {
    private String code = "0";//处理成功获取失败的标记，默认为失败 :  1-----成功, 0----失败
    private String message;//提示信息
    private Object retData;//其他数据 用不用都可以先创建这放着

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    @Override
    public String toString() {
        return "ReturnObject{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", retData=" + retData +
                '}';
    }

    public ReturnObject() {
    }

    public ReturnObject(String code, String message, Object retData) {
        this.code = code;
        this.message = message;
        this.retData = retData;
    }
}

