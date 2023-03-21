package com.itguigu;

import com.itguigu.bean.Student;
import com.itguigu.bean.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private Teacher teacher;

    @Autowired
    private Student student;

    @Test
    void contextLoads() {
        System.out.println(teacher);
        System.out.println(student);
    }

}
