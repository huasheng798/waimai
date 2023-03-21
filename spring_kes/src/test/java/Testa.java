import com.example.bean.Book;
import com.example.service.BookServiceImpl;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:luosheng
 * @Date:2023/2/15 16:04
 * @Description:
 */

public class Testa {


    @Test
    public void Test1() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        BookServiceImpl bean = context.getBean(BookServiceImpl.class);

        List<Book> books = bean.selerctListBook();
        for (Book b : books
        ) {
            System.out.println(b);
        }
    }


    @Test
    public void Test2() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        BookServiceImpl bean = context.getBean(BookServiceImpl.class);
        List<String> bookIdList=new ArrayList<>();
        for (int i = 10; i <=15 ; i++) {
            bookIdList.add(String.valueOf(i));
        }
        System.out.println(bookIdList);
        int i = bean.deleteBookById(bookIdList);
        System.out.println("删除成功"+i+"条");
    }
}
