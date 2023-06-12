package com.dzqc.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.dc.pr.PRError;

import java.io.Serializable;

/**
 * @Author:luosheng
 * @Date:2023/3/20 10:15
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String id;
    private String age;
    private String name;

}
