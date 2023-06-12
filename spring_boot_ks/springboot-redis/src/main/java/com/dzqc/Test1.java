package com.dzqc;

import java.util.concurrent.TimeUnit;

/**
 * @Author:luosheng
 * @Date:2023/3/23 14:11
 * @Description:
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        //非守护线程
        Thread t = new Thread(() -> {
            //模拟非守护线程不退出的情况
            while (true) {
                try {
                    //睡眠一秒
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("线程开始");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //启动线程
        t.start();

        //主线程即将退出
        System.out.println("线程退出");
    }
}
