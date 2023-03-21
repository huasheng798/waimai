package com.dzqz;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:luosheng
 * @Date:2023/3/18 13:35
 * @Description:
 */
public class Test {
    public static void main(String[] args) {
        AtomicReference<Integer> sumNum= new AtomicReference<>(0);

        for (int i = 0; i < 5; i++) {
            sumNum.updateAndGet(sun -> sun + 2);
        }
        System.out.println(sumNum);
    }

}
