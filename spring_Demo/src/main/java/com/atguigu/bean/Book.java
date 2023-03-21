package com.atguigu.bean;

/**
 * @Author:luosheng
 * @Date:2023/2/15 12:59
 * @Description:
 */

public class Book {
    private String bookid;
    private String bookName;
    private String bookType;
    private double price;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookid='" + bookid + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookType='" + bookType + '\'' +
                ", price=" + price +
                '}';
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Book(String bookid, String bookName, String bookType, double price) {
        this.bookid = bookid;
        this.bookName = bookName;
        this.bookType = bookType;
        this.price = price;
    }
}
