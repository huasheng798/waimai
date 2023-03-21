package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author:luosheng
 * @Date:2023/2/15 15:37
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Book {
    private String bookid;
    private String bookName;
    private String bookType;
    private String price;
}
